import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.BasicPriceProcessor;
import util.DiscountPriceProcessor;
import util.EmailProvider;
import util.EmailSender;
import util.FUser;
import util.InternalUserService;
import util.MakeMoreFriendsEmailProvider;
import util.Order;
import util.Order.OrderStatus;
import util.OrderLine;
import util.OrderProcessStep;
import util.Price;
import util.PriceProcessor;
import util.TaxPriceProcessor2;
import util.User;
import util.UserService;
import util.UserServiceInFunctinalWay;
import util.VerifyYourEmailAddressEmailProvider;

public class FunctionDesignPattern {

  @Test
  public void builderPatternTest() throws Exception {
    //given
/*
    FUser user = FUser.builder(1,"testUser")
        .withEmailAddress("test@naver.com")
        .withVerified(true)
        .build();

    FUser userDefault = FUser.builder(1,"testUser")
        .withEmailAddress("test@naver.com")
        .build();
*/

    FUser user = FUser.builder(1, "alice")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
          builder.isVerified = true;
        })
        .build();

    FUser userDefault = FUser.builder(1, "alice")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
        })
        .build();

    //when
    //then
    assertThat(user).isInstanceOf(FUser.class);
    assertThat(userDefault.isVerified()).isFalse();
  }

  @Test
  public void decoratorPatternTest() throws Exception {
    //given
    Price unprocessedPrice = new Price("Original Price");

    PriceProcessor basicPriceProcessor = new BasicPriceProcessor();
    PriceProcessor discountPriceProcessor = new DiscountPriceProcessor();
    PriceProcessor taxPriceProcessor = new TaxPriceProcessor2();

    Price decoratedPriceProcessor = basicPriceProcessor.andThen(discountPriceProcessor)
        .andThen(taxPriceProcessor)
        .process(unprocessedPrice);

    PriceProcessor decoratedPriceProcessor2 = basicPriceProcessor
        .andThen(taxPriceProcessor)
        .andThen(price -> new Price(price.getPrice() + ", then apply another procedure"));
    Price processedPrice2 = decoratedPriceProcessor2.process(unprocessedPrice);
    //when
    System.out.println(decoratedPriceProcessor);
    System.out.println(processedPrice2);
    //then
  }

  @Test
  public void strategyPatternTest() throws Exception {
    //given
    FUser user1 = FUser.builder(1, "alice")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
          builder.isVerified = false;
          builder.friendUserIds = Arrays.asList(201,202,203,204,211,212,213,214);
        })
        .build();
    FUser user2 = FUser.builder(2, "bob")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
          builder.isVerified = true;
          builder.friendUserIds = Arrays.asList(212,213,214);
        })
        .build();
    FUser user3 = FUser.builder(3, "david")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
          builder.isVerified = true;
          builder.friendUserIds = Arrays.asList(201,202,203,204,211,212);
        })
        .build();

    List<FUser> users = Arrays.asList(user1,user2,user3);

    EmailSender emailSender = new EmailSender();
    EmailProvider verfiyYourEmailAddressEmailProvider = new VerifyYourEmailAddressEmailProvider();
    EmailProvider makeMoreFriendsEmailProvider = new MakeMoreFriendsEmailProvider();

    emailSender.setEmailProvider(verfiyYourEmailAddressEmailProvider);
    users.stream()
        .filter(user -> !user.isVerified())
        .forEach(emailSender::sendEmail);

    emailSender.setEmailProvider(makeMoreFriendsEmailProvider);
    users.stream()
        .filter(FUser::isVerified)
        .filter(user -> user.getFrienduserIds().size() <= 5)
        .forEach(emailSender::sendEmail);

    emailSender.setEmailProvider(user -> "'Play With Friends' email for " + user.getName());
    users.stream()
        .filter(FUser::isVerified)
        .filter(user -> user.getFrienduserIds().size() > 5)
        .forEach(emailSender::sendEmail);
    //when
    //then
  }

  @Test
  public void templateMethodPatternTest() throws Exception {
    //given
    FUser user1 = FUser.builder(1, "alice")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
          builder.isVerified = false;
          builder.friendUserIds = Arrays.asList(201,202,203,204,211,212,213,214);
        })
        .build();
    UserService userService = new UserService();
    InternalUserService internalUserService = new InternalUserService();

    userService.createUser(user1);
    internalUserService.createUser(user1);

    UserServiceInFunctinalWay userServiceInFunctinalWay = new UserServiceInFunctinalWay(
        user -> {
          System.out.println("Validating user " + user.getName());
          return user.getName() != null && user.getEmailAddress().isPresent();
        },
        user -> {
          System.out.println("Writing user " + user.getName() + " to DB");
        }
    );
    userServiceInFunctinalWay.createUser(user1);
    //when
    //then
  }

  @Test
  public void chainOfResponsibilityPatternTest() throws Exception {
    //given
    OrderProcessStep initializeStep = new OrderProcessStep(order -> {
      if (order.getStatus() == OrderStatus.CRAETED) {
        System.out.println("Start processing order " + order.getId());
        order.setStatus(OrderStatus.IN_PROGRESS);
      }
    });

    OrderProcessStep setOrderAmountStep = new OrderProcessStep(order -> {
      if (order.getStatus() == OrderStatus.IN_PROGRESS) {
        System.out.println("Setting amount of order " + order.getId());
        order.setAmount(order.getOrderLines().stream().map(OrderLine::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
      }
    });

    OrderProcessStep verifyOrderStep = new OrderProcessStep(order -> {
      if (order.getStatus() == OrderStatus.IN_PROGRESS) {
        System.out.println("Verifying order " + order.getId());
        if (order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
          order.setStatus(OrderStatus.ERROR);
        }
      }
    });

    OrderProcessStep processPaymentStep = new OrderProcessStep(order -> {
      if (order.getStatus() == OrderStatus.IN_PROGRESS) {
        System.out.println("Processing payment of order " + order.getId());
        order.setStatus(OrderStatus.PROCESSED);
      }
    });

    OrderProcessStep handleErrorStep = new OrderProcessStep(order -> {
      if (order.getStatus() == OrderStatus.ERROR) {
        System.out.println("Sending out 'Failed to process order' alert for order " + order.getId());
      }
    });

    OrderProcessStep completeProcessingOrderStep = new OrderProcessStep(order -> {
      if (order.getStatus() == OrderStatus.PROCESSED) {
        System.out.println("Finishied processing order " + order.getId());
      }
    });

    OrderProcessStep chainedOrderProcessSteps = initializeStep
        .setNext(setOrderAmountStep)
        .setNext(verifyOrderStep)
        .setNext(processPaymentStep)
        .setNext(handleErrorStep)
        .setNext(completeProcessingOrderStep);

    Order order = new Order()
        .setId(1001L)
        .setStatus(OrderStatus.CRAETED)
        .setOrderLines(Arrays.asList(new OrderLine().setAmount(BigDecimal.valueOf(1000)),
            new OrderLine().setAmount(BigDecimal.valueOf(2000))
            ));

    Order failOrder = new Order()
        .setId(1001L)
        .setStatus(OrderStatus.CRAETED)
        .setOrderLines(Arrays.asList(new OrderLine().setAmount(BigDecimal.valueOf(1000)),
            new OrderLine().setAmount(BigDecimal.valueOf(-2000))
        ));


    chainedOrderProcessSteps.process(order);
    chainedOrderProcessSteps.process(failOrder);

    //when
    //then
  }



}
