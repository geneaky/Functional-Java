package util;

public class MakeMoreFriendsEmailProvider implements EmailProvider{

  @Override
  public String getEmail(FUser user) {
    return "'Make More Friends' email for " + user.getName();
  }
}
