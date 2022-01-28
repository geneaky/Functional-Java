import util.Adder;

public class Runner {

  public static void main(String[] args) {
    Adder adder = new Adder();
    Integer apply = adder.apply(5);
    System.out.println("apply = " + apply);
  }

}
