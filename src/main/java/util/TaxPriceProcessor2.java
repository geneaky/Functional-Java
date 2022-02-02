package util;

public class TaxPriceProcessor2 implements PriceProcessor{

  @Override
  public Price process(Price price) {
    return new Price(price.getPrice() + ", then applied tax");
  }
}
