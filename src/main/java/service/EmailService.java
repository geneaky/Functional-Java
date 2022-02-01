package service;

import util.User;

public class EmailService {

  public void sendPalyWithFriendsEmail(User user) {
    user.getEmailAdddress().ifPresent(email -> System.out.println("Sending 'Play With Friends' email to " + email));
  }

  public void sendMakeMoreFriendsEmail(User user) {
    user.getEmailAdddress().ifPresent(email -> System.out.println("Sending 'Make More Friens' email to " + email));
  }

  public void snedVerifiyYourEmail(User user) {
    user.getEmailAdddress().ifPresent(email -> System.out.println("Sending 'Verify Your Email' email to " + email));
  }

}
