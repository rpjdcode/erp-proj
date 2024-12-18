package es.rpjd.app.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.spring.SpringConstants;

@Service(value = SpringConstants.BEAN_SERVICE_PRODUCT)
public class ProductServiceImpl implements ProductService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public ProductServiceImpl(SessionFactory factory) {
		this.sessionFactory = factory;
	}

	@Transactional
	@Override
	public DBResponseModel<List<Product>> getProducts() {
		Session session = sessionFactory.getCurrentSession();
		
		List<Product> products = session.createQuery("FROM Product", Product.class).list();
		
		LOG.info("Productos obtenidos de bbdd: {}", products);
		return new DBResponseModel<>(DBResponseStatus.OK, "Productos obtenidos", products);
	}

	@Transactional
	@Override
	public DBResponseModel<Product> save(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(product);
		return new DBResponseModel<>(DBResponseStatus.OK, "Producto guardado", product);
	}
	
	@Transactional
	@Override
	public DBResponseModel<Product> modify(Product product) {
		DBResponseModel<Product> out = new DBResponseModel<>();
		Session session = sessionFactory.getCurrentSession();
		
		Product stored = session.get(Product.class, product.getId());
		Product modified = null;
		
		if (stored != null) {
			stored.setModifiedAt(LocalDateTime.now());
			stored.setProductType(product.getProductType());
			stored.setPrice(product.getPrice());
			stored.setPropertyName(product.getPropertyName());
			modified = session.merge(stored);
			out.setStatus(DBResponseStatus.OK);
			out.setMessage("Producto modificado");
			out.setData(modified);
		} else {
			out.setStatus(DBResponseStatus.NO_RESULT);
			out.setMessage("No se encontró el producto indicado");
			out.setData(null);
		}
		return out;
	}

	@Transactional
	@Override
	public DBResponseModel<Boolean> delete(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.remove(product);
		DBResponseModel<Boolean> ret = null;
		if (session.get(Product.class, product.getId()) == null) {
			LOG.info("Se ha eliminado el registro correctamente");
			ret = new DBResponseModel<>(DBResponseStatus.OK, "Producto eliminado", true);
		} else {
			ret = new DBResponseModel<>(DBResponseStatus.ERROR, "No se eliminó el registro indicado", false);
		}
		
		return ret;
	}


}
