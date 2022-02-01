import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import util.Order;
import util.Order.OrderStatus;
import util.OrderLine;
import util.User;

public class TerminalStreamTest {


  @Test
  public void max() throws Exception {
    //given
    Optional<Integer> max = Stream.of(5, 3, 6, 2, 1)
        .max((x, y) -> x - y);
    //when
    //then
    assertThat(max.get()).isEqualTo(6);
  }

  @Test
  public void userMax() throws Exception {
    //given
    User user1 = new User()
        .setId(101)
        .setName("alice")
        .setVerified(true)
        .setEmailAdddress("alice@naver.com");
    User user2 = new User()
        .setId(102)
        .setName("bob")
        .setVerified(false)
        .setEmailAdddress("bob@naver.com");
    User user3 = new User()
        .setId(103)
        .setName("charlie")
        .setVerified(false)
        .setEmailAdddress("charlie@naver.com");

    List<User> users = Arrays.asList(user1, user2, user3);

    User findUser = users.stream()
        .min((u1, u2) -> u1.getName().compareTo(u2.getName()))
        .get();
    //when
    //then
    assertThat(findUser.getName()).isEqualTo("alice");
  }

  @Test
  public void count() throws Exception {
    //given
    long positiveIntegerCount = Stream.of(1, -4, 5, -3, 6)
        .filter(x -> x > 0)
        .count();
    //when
    //then
    assertThat(positiveIntegerCount).isEqualTo(3);
  }

  @Test
  public void userCount() throws Exception {
    //given
    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    User user1 = new User()
        .setId(101)
        .setName("alice")
        .setVerified(true)
        .setCreatedAt(now.minusDays(2))
        .setEmailAdddress("alice@naver.com");
    User user2 = new User()
        .setId(102)
        .setName("bob")
        .setVerified(false)
        .setCreatedAt(now.minusHours(10))
        .setEmailAdddress("bob@naver.com");
    User user3 = new User()
        .setId(103)
        .setName("charlie")
        .setVerified(false)
        .setCreatedAt(now.minusHours(1))
        .setEmailAdddress("charlie@naver.com");
    User user4 = new User()
        .setId(104)
        .setName("david")
        .setVerified(false)
        .setCreatedAt(now.minusHours(27))
        .setEmailAdddress("david@naver.com");

    List<User> users = Arrays.asList(user1, user2, user3, user4);

    long unverifiedUsersIn24Hrs = users.stream()
        .filter(user -> user.getCreatedAt().isAfter(now.minusHours(24)))
        .filter(user -> !user.isVerified())
        .count();

    //when
    //then
    assertThat(unverifiedUsersIn24Hrs).isEqualTo(2);
  }

  @Test
  public void orderMax() throws Exception {
    //given
    Order order1 = new Order()
        .setId(1001)
        .setAmount(BigDecimal.valueOf(3000))
        .setStatus(OrderStatus.CRAETED);
    Order order2 = new Order()
        .setId(1002)
        .setAmount(BigDecimal.valueOf(12000))
        .setStatus(OrderStatus.ERROR);
    Order order3 = new Order()
        .setId(1003)
        .setAmount(BigDecimal.valueOf(4000))
        .setStatus(OrderStatus.ERROR);
    Order order4 = new Order()
        .setId(1004)
        .setAmount(BigDecimal.valueOf(7000))
        .setStatus(OrderStatus.IN_PROGRESS);
    Order order5 = new Order()
        .setId(1005)
        .setAmount(BigDecimal.valueOf(1000))
        .setStatus(OrderStatus.PROCESSED);

    List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

    Order findOrder = orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.ERROR)
        .max((o1, o2) -> o1.getAmount().compareTo(o2.getAmount()))
        .get();

    BigDecimal maxErroredAmount = orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.ERROR)
        .map(Order::getAmount)
        .max(BigDecimal::compareTo)
        .orElse(BigDecimal.ZERO);
    //when
    //then
    assertThat(findOrder).isEqualTo(order2);
    assertThat(maxErroredAmount).isEqualTo(BigDecimal.valueOf(12000));
  }

  @Test
  public void allMatch() throws Exception {
    //given
    List<Integer> numbers = Arrays.asList(3, -4, 2, 7, 9);
    boolean isAllPositive = numbers.stream()
        .allMatch(number -> number > 0);

    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    User user1 = new User()
        .setId(101)
        .setName("alice")
        .setVerified(true)
        .setCreatedAt(now.minusDays(2))
        .setEmailAdddress("alice@naver.com");
    User user2 = new User()
        .setId(102)
        .setName("bob")
        .setVerified(false)
        .setCreatedAt(now.minusHours(10))
        .setEmailAdddress("bob@naver.com");
    User user3 = new User()
        .setId(103)
        .setName("charlie")
        .setVerified(false)
        .setCreatedAt(now.minusHours(1))
        .setEmailAdddress("charlie@naver.com");
    User user4 = new User()
        .setId(104)
        .setName("david")
        .setVerified(false)
        .setCreatedAt(now.minusHours(27))
        .setEmailAdddress("david@naver.com");

    List<User> users = Arrays.asList(user1, user2, user3, user4);

    boolean areAllUserVerified = users.stream()
        .allMatch(User::isVerified);
    //when
    //then
    assertThat(isAllPositive).isFalse();
    assertThat(areAllUserVerified).isFalse();
  }

  @Test
  public void anyMatch() throws Exception {
    //given
    List<Integer> numbers = Arrays.asList(3, -4, 2, 7, 9);
    boolean anyNegative = numbers.stream()
        .anyMatch(number -> number < 0);

    Order order1 = new Order()
        .setId(1001)
        .setAmount(BigDecimal.valueOf(3000))
        .setStatus(OrderStatus.CRAETED);
    Order order2 = new Order()
        .setId(1002)
        .setAmount(BigDecimal.valueOf(12000))
        .setStatus(OrderStatus.ERROR);
    Order order3 = new Order()
        .setId(1003)
        .setAmount(BigDecimal.valueOf(4000))
        .setStatus(OrderStatus.ERROR);
    Order order4 = new Order()
        .setId(1004)
        .setAmount(BigDecimal.valueOf(7000))
        .setStatus(OrderStatus.IN_PROGRESS);
    Order order5 = new Order()
        .setId(1005)
        .setAmount(BigDecimal.valueOf(1000))
        .setStatus(OrderStatus.PROCESSED);

    List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

    boolean isAnyOrderInErrorStatus = orders.stream()
        .anyMatch(order -> order.getStatus() == OrderStatus.ERROR);
    //when
    //then
    assertThat(anyNegative).isTrue();
    assertThat(isAnyOrderInErrorStatus).isTrue();
  }

  @Test
  public void findAny() throws Exception {
    //given
    Optional<Integer> anyNegativeInterger = Stream.of(3, 2, -5, 6)
        .filter(x -> x < 0)
        .findAny();
    //when
    System.out.println(anyNegativeInterger.get());
    //then
  }


  @Test
  public void findFirst() throws Exception {
    //given
    Optional<Integer> firstPositiveInteger = Stream.of(3, 2, -5, 6)
        .filter(x -> x > 0)
        .findFirst();

    //when
    //then
    assertThat(firstPositiveInteger.get()).isEqualTo(3);
  }

  @Test
  public void reduce() throws Exception {
    //given
    List<Integer> numbers = Arrays.asList(1, 4, -2, -5, 3);
    Integer sum = numbers.stream()
        .reduce((x, y) -> x + y)
        .get();

    Integer min = numbers.stream()
        .reduce((x, y) -> x < y ? x : y).get();

    Integer product = numbers.stream()
        .reduce(1, (x, y) -> x * y);

    List<String> numberStrList = Arrays.asList("3", "2", "5", "-4");
    Integer sumOfNumberStrList = numberStrList.stream()
        .map(Integer::parseInt)
        .reduce(0, (x, y) -> x + y);

    Integer sumOfNumberStrList2 = numberStrList.stream()
        .reduce(0, (number, str) -> number + Integer.parseInt(str), (num1, num2) -> num1 + num2);
    //when
    //then
    assertThat(sum).isEqualTo(1);
    assertThat(min).isEqualTo(-5);
    assertThat(product).isEqualTo(120);
    assertThat(sumOfNumberStrList).isEqualTo(6);
    assertThat(sumOfNumberStrList2).isEqualTo(6);
  }

  @Test
  public void reduceUser() throws Exception {
    //given
    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    User user1 = new User()
        .setId(101)
        .setName("alice")
        .setVerified(true)
        .setFriendUserIds(Arrays.asList(201,202,203,204))
        .setCreatedAt(now.minusDays(2))
        .setEmailAdddress("alice@naver.com");
    User user2 = new User()
        .setId(102)
        .setName("bob")
        .setVerified(false)
        .setFriendUserIds(Arrays.asList(204,205))
        .setCreatedAt(now.minusHours(10))
        .setEmailAdddress("bob@naver.com");
    User user3 = new User()
        .setId(103)
        .setName("charlie")
        .setVerified(false)
        .setFriendUserIds(Arrays.asList(206,207,208))
        .setCreatedAt(now.minusHours(1))
        .setEmailAdddress("charlie@naver.com");
    User user4 = new User()
        .setId(104)
        .setName("david")
        .setVerified(false)
        .setFriendUserIds(Arrays.asList(208))
        .setCreatedAt(now.minusHours(27))
        .setEmailAdddress("david@naver.com");

    List<User> users = Arrays.asList(user1, user2, user3, user4);

    Integer sumOfUserFriends = users.stream()
        .map(User::getFriendUserIds)
        .map(List::size)
        .reduce(0, (x, y) -> x + y);
    //when
    //then
    assertThat(sumOfUserFriends).isEqualTo(10);
  }

  @Test
  public void orderReduce() throws Exception {
    //given
    Order order1 = new Order()
        .setId(1000L)
        .setOrderLines(Arrays.asList(
            new OrderLine()
                .setAmount(BigDecimal.valueOf(1000)),
            new OrderLine()
                .setAmount(BigDecimal.valueOf(2000))
        ));
    Order order2 = new Order()
        .setId(1001L)
        .setOrderLines(Arrays.asList(
            new OrderLine()
                .setAmount(BigDecimal.valueOf(2000)),
            new OrderLine()
                .setAmount(BigDecimal.valueOf(3000))
        ));
    Order order3 = new Order()
        .setId(1002L)
        .setOrderLines(Arrays.asList(
            new OrderLine()
                .setAmount(BigDecimal.valueOf(3000)),
            new OrderLine()
                .setAmount(BigDecimal.valueOf(1000))
        ));

    List<Order> orders = Arrays.asList(order1, order2, order3);

    BigDecimal sumOfOrderLines = orders.stream()
        .map(Order::getOrderLines)
        .flatMap(List::stream)
        .map(OrderLine::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    //when
    //then

    assertThat(sumOfOrderLines).isEqualTo(BigDecimal.valueOf(12000));
  }

  @Test
  public void collectors() throws Exception {
    //given
    List<Integer> numberList = Stream.of(3, 5, -3, 3, 4, 5)
        .collect(Collectors.toList());

    Set<Integer> numberSet = Stream.of(3, 5, -3, 3, 4, 5)
        .collect(Collectors.toSet());

    List<Integer> numberList2 = Stream.of(3, 5, -3, 3, 4, 5)
        .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toList()));

    Set<Integer> numberList3 = Stream.of(3, 5, -3, 3, 4, 5)
        .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toSet()));

    Integer sum = Stream.of(3, 5, -3, 3, 4, 5)
        .collect(Collectors.reducing(0, (x, y) -> x + y));
    //when
    System.out.println(numberList);
    System.out.println(numberSet);
    System.out.println(numberList2);
    System.out.println(numberList3);
    //then
    assertThat(sum).isEqualTo(17);
  }

  @Test
  public void toMap() throws Exception {
    //given
    Map<Integer, String> numberMap = Stream.of(3, 5, -4, 2, 6)
        .collect(Collectors.toMap(Function.identity(), x -> "Number is " + x));

    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    User user1 = new User()
        .setId(101)
        .setName("alice")
        .setVerified(true)
        .setFriendUserIds(Arrays.asList(201,202,203,204))
        .setCreatedAt(now.minusDays(2))
        .setEmailAdddress("alice@naver.com");
    User user2 = new User()
        .setId(102)
        .setName("bob")
        .setVerified(false)
        .setFriendUserIds(Arrays.asList(204,205))
        .setCreatedAt(now.minusHours(10))
        .setEmailAdddress("bob@naver.com");
    User user3 = new User()
        .setId(103)
        .setName("charlie")
        .setVerified(false)
        .setFriendUserIds(Arrays.asList(206,207,208))
        .setCreatedAt(now.minusHours(1))
        .setEmailAdddress("charlie@naver.com");
    User user4 = new User()
        .setId(104)
        .setName("david")
        .setVerified(false)
        .setFriendUserIds(Arrays.asList(208))
        .setCreatedAt(now.minusHours(27))
        .setEmailAdddress("david@naver.com");

    List<User> users = Arrays.asList(user1, user2, user3, user4);

    Map<Integer, User> userIdToUserMap = users.stream()
        .collect(Collectors.toMap(User::getId, Function.identity()));

    Order order1 = new Order()
        .setId(1001)
        .setAmount(BigDecimal.valueOf(3000))
        .setStatus(OrderStatus.CRAETED);
    Order order2 = new Order()
        .setId(1002)
        .setAmount(BigDecimal.valueOf(12000))
        .setStatus(OrderStatus.ERROR);
    Order order3 = new Order()
        .setId(1003)
        .setAmount(BigDecimal.valueOf(4000))
        .setStatus(OrderStatus.ERROR);
    Order order4 = new Order()
        .setId(1004)
        .setAmount(BigDecimal.valueOf(7000))
        .setStatus(OrderStatus.IN_PROGRESS);
    Order order5 = new Order()
        .setId(1005)
        .setAmount(BigDecimal.valueOf(1000))
        .setStatus(OrderStatus.PROCESSED);

    List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

    Map<Long, OrderStatus> orderIdToOrderStatusMap = orders.stream()
        .collect(Collectors.toMap(Order::getId, Order::getStatus));
    //when

    System.out.println(numberMap);
    System.out.println(userIdToUserMap);
    System.out.println(orderIdToOrderStatusMap);
    //then
  }

  @Test
  public void groupingBy() throws Exception {
    //given
    List<Integer> numbers = Arrays.asList(13, 2, 101, 203, 304, 402, 305, 349, 2312, 203);
    Map<Integer, List<Integer>> unitDigitMap = numbers.stream()
        .collect(Collectors.groupingBy(number -> number % 10));

    Map<Integer, Set<Integer>> unitDigitSet = numbers.stream()
        .collect(Collectors.groupingBy(number -> number % 10, Collectors.toSet()));

    Map<Integer, List<String>> unitDigitStrMap = numbers.stream()
        .collect(Collectors.groupingBy(number -> number % 10,
            Collectors.mapping(number -> "unit digit is " + number, Collectors.toList())));

    Order order1 = new Order()
        .setId(1001)
        .setAmount(BigDecimal.valueOf(3000))
        .setStatus(OrderStatus.CRAETED);
    Order order2 = new Order()
        .setId(1002)
        .setAmount(BigDecimal.valueOf(12000))
        .setStatus(OrderStatus.ERROR);
    Order order3 = new Order()
        .setId(1003)
        .setAmount(BigDecimal.valueOf(4000))
        .setStatus(OrderStatus.ERROR);
    Order order4 = new Order()
        .setId(1004)
        .setAmount(BigDecimal.valueOf(7000))
        .setStatus(OrderStatus.IN_PROGRESS);
    Order order5 = new Order()
        .setId(1005)
        .setAmount(BigDecimal.valueOf(1000))
        .setStatus(OrderStatus.PROCESSED);

    List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

    Map<OrderStatus, List<Order>> orderStatusMap = orders.stream()
        .collect(Collectors.groupingBy(Order::getStatus));

    Map<OrderStatus, BigDecimal> orderStatusToSumOfAmountMap = orders.stream()
        .collect(Collectors.groupingBy(Order::getStatus, Collectors.mapping(Order::getAmount,
            Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    //when
    System.out.println(unitDigitMap);
    System.out.println(unitDigitSet);
    System.out.println(unitDigitStrMap);
    System.out.println(orderStatusMap);
    System.out.println(orderStatusToSumOfAmountMap);
    //then
  }

}
