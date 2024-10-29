package es.rpjd.app.hibernate.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "PRODUCT")
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Long id;
	
	@Column(name = "NAME", columnDefinition = "VARCHAR(90)", nullable = false, unique = true)
	private String name;
	
	@Column(name = "PRODUCT_CODE", columnDefinition = "CHAR(10)", nullable = false, unique = true)
	private String productCode;
	
	@Column(name = "PRICE", columnDefinition = "DECIMAL(19,2)", precision = 19, scale = 2)
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_TYPE", columnDefinition = "INT(11)")
	private ProductType productType;
	
	public Product() {
		// Constructor vacío
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
	
	
}
