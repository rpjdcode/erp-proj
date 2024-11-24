package es.rpjd.app.service;

import java.util.List;

import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.model.DBResponseModel;

public interface ProductService {

	DBResponseModel<List<Product>> getProducts();
	DBResponseModel<Product> save(Product product);
	DBResponseModel<Boolean> delete(Product product);

}
