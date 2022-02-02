package util;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class UserServiceInFunctinalWay {
  private final Predicate<FUser> validateUser;
  private final Consumer<FUser> writeToDB;

  public UserServiceInFunctinalWay(Predicate<FUser> validateUser,
      Consumer<FUser> writeToDB) {
    this.validateUser = validateUser;
    this.writeToDB = writeToDB;
  }

  public void createUser(FUser user) {
    if (validateUser.test(user)) {
      writeToDB.accept(user);
      return;
    }
    System.out.println("Cannot create user");
  }
}
