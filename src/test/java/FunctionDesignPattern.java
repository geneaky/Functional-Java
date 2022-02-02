import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.FUser;

public class FunctionDesignPattern {

  @Test
  public void builderPatternTest() throws Exception {
    //given
/*
    FUser user = FUser.builder(1,"testUser")
        .withEmailAddress("test@naver.com")
        .withVerified(true)
        .build();

    FUser userDefault = FUser.builder(1,"testUser")
        .withEmailAddress("test@naver.com")
        .build();
*/

    FUser user = FUser.builder(1, "alice")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
          builder.isVerified = true;
        })
        .build();

    FUser userDefault = FUser.builder(1, "alice")
        .with(builder -> {
          builder.emailAddress = "alice@naver.com";
        })
        .build();

    //when
    //then
    assertThat(user).isInstanceOf(FUser.class);
    assertThat(userDefault.isVerified()).isFalse();
  }



}
