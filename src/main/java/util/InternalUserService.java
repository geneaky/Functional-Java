package util;

public class InternalUserService extends AbstractUserService{

  @Override
  protected boolean validateUser(FUser user) {
    System.out.println("validating internal user " + user.getName());
    return true;
  }

  @Override
  protected void writeToDB(FUser user) {
    System.out.println("Writing user " + user.getName() + " to internal DB");
  }
}
