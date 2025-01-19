package es.rpjd.app.hibernate.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Table(name = "PRODUCT_ORDER", uniqueConstraints = { @UniqueConstraint(name = "PK_PRODUCT_ORDER", columnNames = "ID") })
@Entity
public class ProductOrder implements ApplicationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PO_ORDER"))
	private Order order;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PO_PRODUCT"))
	private Product product;

	@Column(name = "QUANTITY", columnDefinition = "INT(11)", length = 11, nullable = false)
	private Integer quantity;

	public ProductOrder() {
		/* Constructor vacío */ }

	public ProductOrder(ProductOrder data) {
		id = data.getId();
		order = data.getOrder();
		product = data.getProduct();
		quantity = data.getQuantity();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, product, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ProductOrder)) {
			return false;
		}
		ProductOrder other = (ProductOrder) obj;
		return Objects.equals(id, other.id) && Objects.equals(product, other.product)
				&& Objects.equals(quantity, other.quantity);
	}

}
