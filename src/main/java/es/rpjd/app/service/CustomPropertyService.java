package es.rpjd.app.service;

import es.rpjd.app.hibernate.entity.ApplicationEntity;

public interface CustomPropertyService {

	public boolean deleteProperty(String propertyName);
	public boolean addProperty(String propertyName, String propertyValue);
	public String generatePropertyName(ApplicationEntity entity);
	public String getProperty(String propertyName);
}
