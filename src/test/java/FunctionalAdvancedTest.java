import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

  @Test
  public void lazyEvaluationTest() throws Exception {
    //given
    if (returnTrue() || returnFalse()) {
      System.out.println("true");
    }

    if (or(returnTrue(), returnFalse())) {
      System.out.println("true");
    }

    if (lazyOr(() -> returnTrue(), () -> returnFalse())) {
      System.out.println("lazyTrue");
    }

    //stream은 종결 처리가 이루어질때까지 연산을 미룬다.
    Stream<Integer> intergerStream = Stream.of(3,-2,5,8,-3,10)
        .filter(x -> x > 0)
        .peek(x -> System.out.println(x))
        .filter(x -> x % 2 == 0);
    System.out.println("Before collect");

    List<Integer> integers = intergerStream.collect(Collectors.toList());

    System.out.println("After collect: " + integers);

    //when
    //then
  }

  public static boolean or(boolean x, boolean y) {
    return x||y;
  }

  public static boolean lazyOr(Supplier<Boolean> x, Supplier<Boolean> y) {
    return x.get() || y.get();
  }

  public static boolean returnTrue() {
    System.out.println("Returning true");
    return true;
  }

  public static boolean returnFalse() {
    System.out.println("Returning false");
    return false;
  }

}
