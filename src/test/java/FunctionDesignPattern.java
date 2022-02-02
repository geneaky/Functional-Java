import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.BasicPriceProcessor;
import util.DiscountPriceProcessor;
import util.FUser;
import util.Price;
import util.PriceProcessor;
import util.TaxPriceProcessor2;

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



}
