package util;

public class Suv extends Car{

  public Suv(String name, String brand) {
    super(name, brand);
  }

  @Override
  public void drive() {
    System.out.println("driving a suv " + name + " from " + brand);
  }
}
