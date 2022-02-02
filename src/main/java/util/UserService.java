package util;

public class UserService extends AbstractUserService{

  @Override
  protected boolean validateUser(FUser user) {
    System.out.println("Validating user " + user.getName());
    return user.getName() != null && user.getEmailAddress().isPresent();
  }

  @Override
  protected void writeToDB(FUser user) {
    System.out.println("Writing user " + user.getName() + " to DB");
  }
}
