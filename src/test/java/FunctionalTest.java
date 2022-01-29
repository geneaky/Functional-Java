import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionalTest {

  @Test
  public void supplyTest() throws Exception {
    //given
    Supplier<String> supplier = () -> "hello world";
    Supplier<Double> doubleSupplier = () -> Math.random();
    //when
    randomDoubles(doubleSupplier,3);
    //then
    assertThat(supplier.get()).isEqualTo("hello world");
    assertThat(doubleSupplier.get()).isInstanceOf(Double.class);
  }

  public static void randomDoubles(Supplier<Double> randomSupplier, int count) {
    for (int i = 0; i < count; i++) {
      System.out.println(randomSupplier.get());
    }
  }

  @Test
  public void consumerTest() throws Exception {
    //given
    Consumer<String> stringConsumer = str -> System.out.println(str);
    Consumer<Integer> integerConsumer = input -> System.out.println(input);
    //when
    stringConsumer.accept("hello");
    process(Arrays.asList(1,2,3,4,5),integerConsumer);
    //then
  }

  public static <T> void process(List<T> inputs, Consumer<T> processor) {
    for (T input : inputs) {
      processor.accept(input);
    }
  }

  @Test
  public void biConsumerTest() throws Exception {
    //given
    BiConsumer<Integer, Double> biConsumer = (index, input) -> System.out.println("process " + input + "at index " + index);
    List<Double> inputs = Arrays.asList(1.1,2.2,3.3);
    //when
    process(inputs,biConsumer);
    //then
  }

  public static <T> void process(List<T> inputs, BiConsumer<Integer, T> biConsumer) {
    for (int i =0; i < inputs.size(); i++) {
      biConsumer.accept(i,inputs.get(i));
    }
  }

  @Test
  public void predicateTest() throws Exception {
    //given
    Predicate<Integer> isPositive = x -> x > 0;
    List<Integer> list = Arrays.asList(0,-2,1,3,5);
    //when
    List<Integer> result = filter(list,isPositive);
    List<Integer> result1 = filter(list,isPositive.negate());
    List<Integer> result2 = filter(list,isPositive.or(x -> x==0));
    List<Integer> result3 = filter(list,isPositive.and(x -> x % 2 == 0));
    System.out.println(isPositive instanceof Object);
    //then
    assertThat(isPositive.test(10)).isTrue();
    assertThat(result).containsExactly(1,3,5);
    assertThat(result1).containsExactly(0,-2);
    assertThat(result2).containsExactly(0,1,3,5);
    assertThat(result3).isEmpty();
  }

  public static <T> List<T> filter(List<T> inputs, Predicate<T> condition) {
    List<T> output = new ArrayList<>();

    for (T input : inputs) {
      if (condition.test(input)) {
        output.add(input);
      }
    }

    return output;
  }

}
