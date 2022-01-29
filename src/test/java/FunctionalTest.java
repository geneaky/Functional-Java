import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
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

}
