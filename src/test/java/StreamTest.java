import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import util.Order;
import util.Order.OrderStatus;
import util.User;

public class StreamTest {

  @Test
  public void stream() throws Exception {
    //given
    Stream<String> nameStream = Stream.of("alice", "bob", "charlie");
    List<String> names = nameStream.collect(Collectors.toList());
    System.out.println(names);

    String[] cityArray = new String[]{"San Jose", "Seoul", "Tokyo"};
    Stream<String> cityStream = Arrays.stream(cityArray);
    List<String> cities = cityStream.collect(Collectors.toList());
    System.out.println(cities);

    Set<Integer> numberSet = new HashSet<>(Arrays.asList(3,5,7));
    Stream<Integer> numberStream = numberSet.stream();
    List<Integer> numbers = numberStream.collect(Collectors.toList());
    System.out.println(numbers);
    //when
    //then
  }

  @Test
  public void streamFilterTest() throws Exception {
    //given
    List<Integer> filteredNumbers = Stream.of(3, -5, 7, 10, -3)
        .filter(x -> x > 0)
        .collect(Collectors.toList());
    System.out.println(filteredNumbers);
    //when
    //then
  }

  @Test
  public void userFilterTest() throws Exception {
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
    List<User> verifiedUsers = users.stream()
        .filter(User::isVerified)
        .collect(Collectors.toList());

    System.out.println(verifiedUsers);

    List<User> unverifiedUsers = users.stream()
        .filter(u -> !u.isVerified())
        .collect(Collectors.toList());

    System.out.println(unverifiedUsers);

    Order order1 = new Order()
        .setId(1001)
        .setStatus(OrderStatus.CRAETED);
    Order order2 = new Order()
        .setId(1002)
        .setStatus(OrderStatus.ERROR);
    Order order3 = new Order()
        .setId(1003)
        .setStatus(OrderStatus.ERROR);
    Order order4 = new Order()
        .setId(1004)
        .setStatus(OrderStatus.IN_PROGRESS);
    Order order5 = new Order()
        .setId(1005)
        .setStatus(OrderStatus.PROCESSED);

    List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
    //filter orders in error state
    List<Order> filteredOrder = orders.stream()
        .filter(o -> o.getStatus() == OrderStatus.ERROR)
        .collect(Collectors.toList());

    System.out.println(filteredOrder);

    //when
    //then
  }

  @Test
  public void streamMapTest() throws Exception {
    //given
    List<Integer> numberList = Arrays.asList(3, 6, -4);
    List<Integer> numberListX2 = numberList.stream().map(integer -> integer * 2).collect(Collectors.toList());
    System.out.println(numberListX2);

    List<String> stringList = numberList.stream()
        .map(x -> "Number is " + x)
        .collect(Collectors.toList());
    System.out.println(stringList);
    //when
    //then
  }

  @Test
  public void orderStreamMapTest() throws Exception {
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

    List<String> userEmailList = users.stream()
        .map(User::getEmailAdddress)
        .collect(Collectors.toList());
    System.out.println(userEmailList);

    Order order1 = new Order()
        .setId(1001)
        .setStatus(OrderStatus.CRAETED)
        .setCreatedByUserId(101);
    Order order2 = new Order()
        .setId(1002)
        .setStatus(OrderStatus.ERROR)
        .setCreatedByUserId(103);
    Order order3 = new Order()
        .setId(1003)
        .setStatus(OrderStatus.ERROR)
        .setCreatedByUserId(102);
    Order order4 = new Order()
        .setId(1004)
        .setStatus(OrderStatus.IN_PROGRESS)
        .setCreatedByUserId(104);
    Order order5 = new Order()
        .setId(1005)
        .setStatus(OrderStatus.PROCESSED)
        .setCreatedByUserId(102);

    List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
    List<Long> orderUserId = orders.stream()
        .map(Order::getCreatedByUserId)
        .collect(Collectors.toList());
    System.out.println(orderUserId);
    //when
    //then
  }

  @Test
  public void pipelineTest() throws Exception {
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

    List<String> emails = new ArrayList<>();
    for (User user : users) {
      if (!user.isVerified()) {
        emails.add(user.getEmailAdddress());
      }
    }

    System.out.println(emails);

    List<String> emails2 = users.stream()
        .filter(user -> !user.isVerified())
        .map(User::getEmailAdddress)
        .collect(Collectors.toList());
    System.out.println(emails2);

    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    Order order1 = new Order()
        .setId(1001)
        .setStatus(OrderStatus.CRAETED)
        .setCratedAt(now.minusHours(4))
        .setCreatedByUserId(101);
    Order order2 = new Order()
        .setId(1002)
        .setStatus(OrderStatus.ERROR)
        .setCratedAt(now.minusHours(1))
        .setCreatedByUserId(103);
    Order order3 = new Order()
        .setId(1003)
        .setStatus(OrderStatus.ERROR)
        .setCratedAt(now.minusHours(36))
        .setCreatedByUserId(102);
    Order order4 = new Order()
        .setId(1004)
        .setStatus(OrderStatus.IN_PROGRESS)
        .setCratedAt(now.minusHours(15))
        .setCreatedByUserId(104);
    Order order5 = new Order()
        .setId(1005)
        .setStatus(OrderStatus.PROCESSED)
        .setCratedAt(now.minusHours(10))
        .setCreatedByUserId(102);

    List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

    List<Long> userIds = orders.stream()
        .filter(oder -> oder.getStatus() == OrderStatus.ERROR)
        .map(Order::getCreatedByUserId)
        .collect(Collectors.toList());
    System.out.println(userIds);

    List<Long> userIds2 = orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.ERROR)
        .filter(order -> order.getCratedAt().isAfter(now.minusHours(24)))
        .map(Order::getCreatedByUserId)
        .collect(Collectors.toList());
    System.out.println(userIds2);
    //when
    //then
  }

  @Test
  public void sortTest() throws Exception {
    //given
    List<Integer> numbers = Arrays.asList(3, -5, 7, 4);
    List<Integer> sortedNumbers = numbers.stream()
        .sorted()
        .collect(Collectors.toList());
    System.out.println(sortedNumbers);
    //when
    //then
  }

}
