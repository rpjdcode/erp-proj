package es.rpjd.app.hibernate.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductOrder> productsOrder = new HashSet<>();

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

	public Set<ProductOrder> getProductsOrder() {
		return productsOrder;
	}

	public void setProductsOrder(Set<ProductOrder> productsOrder) {
		this.productsOrder = productsOrder;
	}

}
