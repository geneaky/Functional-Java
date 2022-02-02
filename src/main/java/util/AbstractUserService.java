package util;

public abstract class AbstractUserService {

  protected abstract boolean validateUser(FUser user);

  protected abstract void writeToDB(FUser user);

  public void createUser(FUser user) {
    if (validateUser(user)) {
      writeToDB(user);
      return;
    }
    System.out.println("Cannot create user");
  }

}
