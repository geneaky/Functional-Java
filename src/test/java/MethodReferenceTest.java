import static org.assertj.core.api.Assertions.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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

}
