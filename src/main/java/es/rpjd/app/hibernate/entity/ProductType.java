package es.rpjd.app.hibernate.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "PRODUCT_TYPE")
@Entity
public class ProductType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Long id;

	@Column(name = "TYPE", columnDefinition = "VARCHAR(30)", length = 30, nullable = false, unique = true)
	private String type;
	
	@OneToMany(mappedBy = "productType")
	private List<Product> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
