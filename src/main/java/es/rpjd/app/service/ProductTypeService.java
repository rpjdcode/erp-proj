package es.rpjd.app.service;

import java.util.List;

import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.DBResponseModel;

public interface ProductTypeService {

	DBResponseModel<List<ProductType>> getTypes();
}
