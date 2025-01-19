package es.rpjd.app.hibernate.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Table(name = "PRODUCT", uniqueConstraints = { @UniqueConstraint(name = "PK_PRODUCT", columnNames = "ID"),
		@UniqueConstraint(name = "UQ_PRODUCT_PROPERTY", columnNames = "PROPERTY_NAME"),
		@UniqueConstraint(name = "UQ_PRODUCT_CODE", columnNames = "PRODUCT_CODE") })
@Entity
public class Product implements ApplicationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Long id;

	@Column(name = "PROPERTY_NAME", columnDefinition = "VARCHAR(100)", length = 100, nullable = false, unique = true)
	private String propertyName;

	@Column(name = "PRODUCT_CODE", columnDefinition = "CHAR(10)", nullable = false, unique = true)
	private String productCode;

	@Column(name = "PRICE", columnDefinition = "DECIMAL(19,2)", precision = 19, scale = 2, nullable = false)
	private BigDecimal price;

	@Column(name = "CREATED_AT", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "MODIFIED_AT", columnDefinition = "DATETIME DEFAULT NULL", nullable = true)
	private LocalDateTime modifiedAt;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductOrder> comandaProductos = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "PRODUCT_TYPE", columnDefinition = "INT(11)", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_TYPE"))
	private ProductType productType;

	public Product() {
		/* Constructor vacío */ }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
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

	public Set<ProductOrder> getComandaProductos() {
		return comandaProductos;
	}

	public void setComandaProductos(Set<ProductOrder> comandaProductos) {
		this.comandaProductos = comandaProductos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Product product = (Product) o;

		return id != null && id.equals(product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
