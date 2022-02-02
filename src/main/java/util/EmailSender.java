package util;

public class EmailSender {

  private EmailProvider emailProvider;

  public EmailSender setEmailProvider(EmailProvider emailProvider) {
    this.emailProvider = emailProvider;
    return this;
  }

  public void sendEmail(FUser user) {
    String email = emailProvider.getEmail(user);
    System.out.println("Sending " + email);
  }

}
