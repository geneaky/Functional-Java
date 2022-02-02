package util;

public class BasicPriceProcessor implements PriceProcessor{

  @Override
  public Price process(Price price) {
    return price;
  }
}
