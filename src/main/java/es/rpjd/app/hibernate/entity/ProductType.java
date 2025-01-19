package es.rpjd.app.hibernate.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Table(name = "PRODUCT_TYPE", uniqueConstraints = { @UniqueConstraint(name = "PK_PRODUCT_TYPE", columnNames = "ID"),
		@UniqueConstraint(name = "UQ_PRODUCT_TYPE_CODE", columnNames = "TYP_CODE") })
@Entity
public class ProductType implements ApplicationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Long id;

	@Column(name = "TYP_CODE", columnDefinition = "VARCHAR(30)", length = 30, nullable = false, unique = true)
	private String code;

	@Column(name = "PROPERTY_NAME", columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
	private String propertyName;

	@Column(name = "CREATED_AT", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "MODIFIED_AT", columnDefinition = "DATETIME DEFAULT NULL", nullable = true)
	private LocalDateTime modifiedAt;

	@OneToMany(mappedBy = "productType")
	private List<Product> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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
