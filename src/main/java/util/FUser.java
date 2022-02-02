package util;

import java.util.function.Consumer;

public class FUser {

  private int id;
  private String name;
  private String emailAddress;
  private boolean isVerified;

  public boolean isVerified() {
    return isVerified;
  }

  public FUser(Builder builder) {
    this.id = builder.id;
    this.name =builder.name;
    this.emailAddress = builder.emailAddress;
    this.isVerified = builder.isVerified;
  }

  public static Builder builder(int id, String name) {
    return new Builder(id,name);
  }

  public static class Builder {
    public int id;
    public String name;
    public String emailAddress;
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
