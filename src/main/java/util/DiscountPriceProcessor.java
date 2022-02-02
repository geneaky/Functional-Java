package util;

public class DiscountPriceProcessor implements PriceProcessor{

  @Override
  public Price process(Price price) {
    return new Price(price.getPrice() + ", then applied discount");
  }
}
