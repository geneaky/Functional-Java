import static org.assertj.core.api.Assertions.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionalAdvancedTest {

  @Test
  public void lexicalScopeClosure() throws Exception {
    //given
    Supplier<String> supplier = getStringSupplier();  //closure
    System.out.println(supplier.get());

    //curry
    BiFunction<Integer, Integer, Integer> add = (x,y) -> x + y;
    Function<Integer, Function<Integer,Integer>> curriedAdd = x -> y -> x + y;

    Function<Integer, Integer> addThree = curriedAdd.apply(3);
    Integer result = addThree.apply(10);
    //when
    //then
    assertThat(result).isEqualTo(13);
  }

  public static Supplier<String> getStringSupplier() {
    String hello = "Hello";
    Supplier<String> supplier = () -> {
      String world = "World";
      return hello + world;
    };

    return supplier;
  }

}
