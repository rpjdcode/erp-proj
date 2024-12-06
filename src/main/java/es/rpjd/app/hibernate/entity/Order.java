package es.rpjd.app.hibernate.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "CUSTOMER_ORDER")
@Entity
public class Order implements ApplicationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Long id;
	
	@Column(name = "ORDER_CODE", columnDefinition = "CHAR(11)", length = 11, nullable = false, unique = true)
	private String orderCode;
	
	@Column(name = "CREATED_AT", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "MODIFIED_AT", columnDefinition = "DATETIME", nullable = true)
	private LocalDateTime modifiedAt;
	
	public Order() { /* Constructor vacío */}
	
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
}
