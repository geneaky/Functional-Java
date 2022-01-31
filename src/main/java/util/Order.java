package util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
  private long id;
  private LocalDateTime cratedAt;
  private long createdByUserId;
  private OrderStatus status;
  private BigDecimal amount;
  private List<OrderLine> orderLines;

  public Order() {

  }

  public enum OrderStatus {
    CRAETED,
    IN_PROGRESS,
    ERROR,
    PROCESSED
  }

  public Order(long id, LocalDateTime cratedAt, long createdByUserId, OrderStatus status,
      BigDecimal amount, List<OrderLine> orderLines) {
    this.id = id;
    this.cratedAt = cratedAt;
    this.createdByUserId = createdByUserId;
    this.status = status;
    this.amount = amount;
    this.orderLines = orderLines;
  }

  public long getId() {
    return id;
  }

  public Order setId(long id) {
    this.id = id;
    return this;
  }

  public LocalDateTime getCratedAt() {
    return cratedAt;
  }

  public Order setCratedAt(LocalDateTime cratedAt) {
    this.cratedAt = cratedAt;
    return this;
  }

  public long getCreatedByUserId() {
    return createdByUserId;
  }

  public Order setCreatedByUserId(long createdByUserId) {
    this.createdByUserId = createdByUserId;
    return this;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public Order setStatus(OrderStatus status) {
    this.status = status;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public List<OrderLine> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderLine> orderLines) {
    this.orderLines = orderLines;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", cratedAt=" + cratedAt +
        ", createdByUserId=" + createdByUserId +
        ", status=" + status +
        ", amount=" + amount +
        ", orderLines=" + orderLines +
        '}';
  }
}
