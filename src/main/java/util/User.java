package util;

import java.util.List;
import java.util.Optional;

public class User {

  private int id;
  private String name;
  private String emailAdddress;
  private boolean isVerified;
  private List<Integer> friendUserIds;



  public Optional<String> getEmailAdddress() {
    return Optional.ofNullable(emailAdddress);
  }

  public User setEmailAdddress(String emailAdddress) {
    this.emailAdddress = emailAdddress;
    return this;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public User setVerified(boolean verified) {
    isVerified = verified;
    return this;
  }

  public List<Integer> getFriendUserIds() {
    return friendUserIds;
  }

  public User setFriendUserIds(List<Integer> friendUserIds) {
    this.friendUserIds = friendUserIds;
    return this;
  }

  public int getId() {
    return id;
  }

  public User setId(int id)
  {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public User setName(String name) {
    this.name = name;
    return this;
  }

  public User() {
  }

  public User(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
