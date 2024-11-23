package es.rpjd.app.service.impl;

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

	private static final Logger LOG = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
	
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
		return new DBResponseModel<Product>(DBResponseStatus.OK, "Producto guardado", product);
	}
}
