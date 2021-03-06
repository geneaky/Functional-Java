package util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class FUser {

  private int id;
  private String name;
  private String emailAddress;
  private boolean isVerified;
  private List<Integer> friendsUserIds;

  public boolean isVerified() {
    return isVerified;
  }

  public FUser(Builder builder) {
    this.id = builder.id;
    this.name =builder.name;
    this.emailAddress = builder.emailAddress;
    this.friendsUserIds = builder.friendUserIds;
    this.isVerified = builder.isVerified;
  }

  public static Builder builder(int id, String name) {
    return new Builder(id,name);
  }

  public String getName() {
    return this.name;
  }

  public List<Integer> getFrienduserIds() {
    return this.friendsUserIds;
  }

  public Optional<String> getEmailAddress() {
    return Optional.of(this.emailAddress);
  }

  public static class Builder {
    public int id;
    public String name;
    public String emailAddress;
    public List<Integer> friendUserIds;
    public boolean isVerified = false;


    private Builder(int id, String name) {
      this.id = id;
      this.name = name;
    }

    public Builder with(Consumer<Builder> consumer) {
      consumer.accept(this);
      return this;
    }

    public FUser build() {
      return new FUser(this);
    }
  }

}
