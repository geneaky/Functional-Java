import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.User;

public class OptionalTest {

  @Test
  public void option() throws Exception {
    //given
    User user1 = new User()
        .setId(1001)
        .setName("Alice")
        .setVerified(false);

    User user2 = new User()
        .setId(1001)
        .setName("Alice")
        .setEmailAdddress("alice@naver.com")
        .setVerified(false);

//    System.out.println(userEquals(user1,user2)); <- NPE
    String someEmail = "some@email.com";
    String nullEmail = null;
    Optional<String> maybeEmail = Optional.of(someEmail);
    Optional<String> maybeEmail2 = Optional.empty();
    Optional<String> maybeEmail3 = Optional.ofNullable(someEmail);
    Optional<String> maybeEmail4 = Optional.ofNullable(nullEmail);

    assertThat(maybeEmail.get()).isEqualTo(someEmail);
    assertThat(maybeEmail2.isPresent()).isFalse();
    assertThat(maybeEmail2.orElse("")).isEmpty();
    assertThat(maybeEmail2.orElseGet(() -> "")).isEmpty();
    assertThatThrownBy(() -> maybeEmail2.orElseThrow(() -> new RuntimeException())).isInstanceOf(RuntimeException.class);
    assertThat(maybeEmail3.get()).isEqualTo(someEmail);
    assertThat(maybeEmail4.orElse("")).isEmpty();



    //when
    //then
  }

  @Test
  public void advanceOptionalTest() throws Exception {
    //given
    Optional<User> maybeUser = Optional.ofNullable(maybeGetUser(false));
    maybeUser.ifPresent(System.out::println);

    Optional<Integer> maybeId = Optional.ofNullable(maybeGetUser(true))
        .map(user -> user.getId());
    maybeId.ifPresent(System.out::println);

    String userName = Optional.ofNullable(maybeGetUser(true))
        .map(User::getName)
        .map(name -> "The name is " + name)
        .orElse("Name is empty");
    System.out.println(userName);

    Optional<String> maybeEmail7 = Optional.ofNullable(maybeGetUser(false))
        .flatMap(User::getEmailAdddress);
    maybeEmail7.ifPresent(System.out::println);

    //when
    //then
  }

  public static boolean userEquals(User u1, User u2) {
    return u1.getId() == u2.getId()
        && u1.getName().equals(u2.getName())
        && u1.getEmailAdddress().equals(u2.getEmailAdddress())
        && u1.isVerified() == u2.isVerified();
  }

  public static User maybeGetUser(boolean returnUser) {
    if (returnUser) {
      return new User()
          .setId(1001)
          .setName("Alice")
          .setEmailAdddress("alice@naver.com")
          .setVerified(false);

    }
    return null;
  }

}
