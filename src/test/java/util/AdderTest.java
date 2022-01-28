package util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AdderTest {

  @Test
  public void addNumber() throws Exception {
    //given
    Adder adder = new Adder();
    //when
    Integer apply = adder.apply(5);
    //then
    assertThat(apply).isEqualTo(15);
  }

  @Test
  public void addNumberLambda() throws Exception {
    //given
    Function<Integer,Integer> adder1 = (Integer x) -> {
      return x + 10;
    };

    Function<Integer,Integer> adder2 = x -> x+10;
    //when
    //then
    assertThat(adder1.apply(5)).isEqualTo(15);
    assertThat(adder2.apply(5)).isEqualTo(15);
  }
}