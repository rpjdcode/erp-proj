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
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;

@Service(value = SpringConstants.BEAN_SERVICE_PRODUCT_TYPE)
public class ProductTypeServiceImpl implements ProductTypeService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public ProductTypeServiceImpl(SessionFactory factory) {
		this.sessionFactory = factory;
	}

	@Transactional
	@Override
	public DBResponseModel<List<ProductType>> getTypes() {
		Session session = sessionFactory.getCurrentSession();
		
		List<ProductType> types = session.createQuery("FROM ProductType", ProductType.class).list();
		
		LOG.info("Tipos de producto obtenidos de bbdd: {}", types);
		return new DBResponseModel<>(DBResponseStatus.OK, "Tipos de producto obtenidos", types);
	}

}
