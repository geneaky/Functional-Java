import static org.assertj.core.api.Assertions.*;

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
    Consumer<String> stringConsumer = (str) -> System.out.println(str);
    //when
    stringConsumer.accept("hello");
    //then
  }

  public static void process(List<Integer> inputs, Consumer<Integer> processor) {

  }

}
