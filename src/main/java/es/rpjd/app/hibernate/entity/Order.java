package es.rpjd.app.hibernate.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "CUSTOMER_ORDER")
@Entity
public class Order implements ApplicationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Long id;

	@Column(name = "ORDER_CODE", columnDefinition = "CHAR(20)", length = 20, nullable = false)
	private String orderCode;

	@Column(name = "CREATED_AT", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "MODIFIED_AT", columnDefinition = "DATETIME", nullable = true)
	private LocalDateTime modifiedAt;

	@Column(name = "PROCESSED", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
	private boolean processed;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductOrder> productsOrder = new ArrayList<>();

	public Order() {
		/* Constructor vacío */}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public List<ProductOrder> getProductsOrder() {
		return productsOrder;
	}

	public void setProductsOrder(List<ProductOrder> productsOrder) {
		this.productsOrder = productsOrder;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Order order = (Order) o;

		return id != null && id.equals(order.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Devuelve el importe actual de la comanda
	 * 
	 * @return
	 */
	public BigDecimal calculateAmount() {
		BigDecimal ret = new BigDecimal(0);
		if (productsOrder.isEmpty()) {
			return ret;
		}

		return productsOrder.stream()
				.map(po -> po.getProduct().getPrice().multiply(BigDecimal.valueOf(po.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
