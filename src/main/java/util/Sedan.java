package util;

public class Sedan extends Car{

  public Sedan(String name, String brand) {
    super(name, brand);
  }

  @Override
  public void drive() {
    System.out.println("driving a sedan " + name + " from " + brand);
  }
}
