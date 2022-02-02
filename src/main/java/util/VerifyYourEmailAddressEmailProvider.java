package util;

public class VerifyYourEmailAddressEmailProvider implements EmailProvider{

  @Override
  public String getEmail(FUser user) {
    return "'Verify Your Email Address' email for " + user.getName();
  }
}
