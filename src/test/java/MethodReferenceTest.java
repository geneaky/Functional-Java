import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Car;
import util.Sedan;
import util.Suv;
import util.User;
import util.Van;

public class MethodReferenceTest {

  @Test
  public void staticMethodTest() throws Exception {
    //given
    Function<String, Integer> stringIntegerIntegerBiFunction = Integer::parseInt;
    Integer apply = stringIntegerIntegerBiFunction.apply("3");
    //when
    //then
    assertThat(apply).isEqualTo(3);
  }

  @Test
  public void instanceMethodTest() throws Exception {
    //given
    String str = "hello";
    Predicate<String> equalsToHello = str::equals;
    //when
    //then
    assertThat(equalsToHello.test("hello")).isTrue();
    assertThat(calculate(8, 2, (x, y) -> x + y)).isEqualTo(10);
    assertThat(calculate(8, 2, MethodReferenceTest :: multiply)).isEqualTo(16);
    MethodReferenceTest instance = new MethodReferenceTest();
    assertThat(calculate(8, 2, instance :: subtract)).isEqualTo(6);
    assertThat(calculate(8, 2, this :: subtract)).isEqualTo(6);
  }

  private static Integer multiply(Integer x, Integer y) {
    return x * y;
  }

  public Integer subtract(Integer x, Integer y) {
    return x - y;
  }

  public static int calculate(int x, int y, BiFunction<Integer, Integer, Integer> operator) {
    return operator.apply(x,y);
  }

  @Test
  public void methodReferenceTest2() throws Exception {
    //given
    Function<String, Integer> strLength = String::length;
    assertThat(strLength.apply("hello world")).isEqualTo(11);

    BiPredicate<String, String> strEquals = String::equals;
    assertThat(strEquals.test("hello", "hello")).isTrue();

    List<User> users = new ArrayList<>();
    users.add(new User(3, "alice"));
    users.add(new User(1, "charlie"));
    users.add(new User(5, "bob"));
    //when
    printUserField(users, User::getId);
    //then
  }

  public static void printUserField(List<User> users, Function<User, Object> getter) {
    for (User user : users) {
      System.out.println(getter.apply(user));
    }
  }

  @Test
  public void methodReferenceConstructorTest() throws Exception {
    //given
    BiFunction<Integer, String, User> aNew = User::new;
    //when
    User user = aNew.apply(1, "alice");
    //then
    assertThat(user.getId()).isEqualTo(1);
    assertThat(user.getName()).isEqualTo("alice");
  }

  @Test
  public void methodReferenceConstructorTest2() throws Exception {
    //given
    Map<String, BiFunction<String, String, Car>> carTypeToConstructorMap = new HashMap<>();
    carTypeToConstructorMap.put("sedan", Sedan::new);
    carTypeToConstructorMap.put("suv", Suv::new);
    carTypeToConstructorMap.put("van", Van::new);

    String[][] inputs = new String[][]{
        {"sedan", "sonata", "hyundai"},
        {"van", "sienna", "toyota"},
        {"suv", "sorento", "kia"},
        {"sedan", "model s", "tesla"}
    };

    List<Car> cars = new ArrayList<>();
    for (String[] input : inputs) {
      String carType = input[0];
      String name = input[1];
      String brand = input[2];

      cars.add(carTypeToConstructorMap.get(carType).apply(name,brand));
    }
    //when
    for (Car car : cars) {
      car.drive();
    }
    //then
  }

}
