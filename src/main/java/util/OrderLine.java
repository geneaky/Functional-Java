package util;

import java.math.BigDecimal;

public class OrderLine {
  private long id;
  private OrderLineType type;
  private long productid;
  private int quantity;
  private BigDecimal amount;

  public enum OrderLineType {
    PURCHASE,
    DISCOUNT
  }

  public OrderLine(long id, OrderLineType type, long productid, int quantity,
      BigDecimal amount) {
    this.id = id;
    this.type = type;
    this.productid = productid;
    this.quantity = quantity;
    this.amount = amount;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public OrderLineType getType() {
    return type;
  }

  public void setType(OrderLineType type) {
    this.type = type;
  }

  public long getProductid() {
    return productid;
  }

  public void setProductid(long productid) {
    this.productid = productid;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
