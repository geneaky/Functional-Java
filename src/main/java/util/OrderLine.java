package util;

import java.math.BigDecimal;

public class OrderLine {
  private long id;
  private OrderLineType type;
  private long productid;
  private int quantity;
  private BigDecimal amount;

  public OrderLine() {

  }

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

  public OrderLine setId(long id) {
    this.id = id;
    return this;
  }

  public OrderLineType getType() {
    return type;
  }

  public OrderLine setType(OrderLineType type) {
    this.type = type;
    return this;
  }

  public long getProductid() {
    return productid;
  }

  public OrderLine setProductid(long productid) {
    this.productid = productid;
    return this;
  }

  public int getQuantity() {
    return quantity;
  }

  public OrderLine setQuantity(int quantity) {
    this.quantity = quantity;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OrderLine setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  @Override
  public String toString() {
    return "OrderLine{" +
        "id=" + id +
        ", type=" + type +
        ", productid=" + productid +
        ", quantity=" + quantity +
        ", amount=" + amount +
        '}';
  }
}
