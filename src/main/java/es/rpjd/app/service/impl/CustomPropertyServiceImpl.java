package es.rpjd.app.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.rpjd.app.hibernate.entity.ApplicationEntity;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.CustomPropertiesAssistant;

@Service(value = SpringConstants.BEAN_SERVICE_PROPERTY)
public class CustomPropertyServiceImpl implements CustomPropertyService {

	private static final Logger LOG = LoggerFactory.getLogger(CustomPropertyServiceImpl.class);

	/**
	 * Asistente de propiedades personalizadas
	 */
	private CustomPropertiesAssistant assistant;

	public CustomPropertyServiceImpl() {
		assistant = new CustomPropertiesAssistant();
	}

	@Override
	public boolean deleteProperty(String propertyName) {
		LOG.info("Intentando eliminar propiedad {} del fichero custom.properties", propertyName);

		boolean deleted = false;
		try {
			deleted = assistant.deleteProperty(propertyName);
		} catch (IOException e) {
			LOG.error("Se ha producido un error al intentar eliminar la propiedad {}", propertyName, e);
		}
		return deleted;
	}

	/**
	 * Añade una propiedad personalizada al fichero custom.properties si no existe.
	 * En caso de existir, modifica el valor de la propiedad existente
	 * 
	 * @return true si se añadió/modificó la propiedad, false si no
	 */
	@Override
	public boolean addProperty(String propertyName, String propertyValue) {
		boolean added = false;
		try {
			added = assistant.addProperty(propertyName, propertyValue);
		} catch (IOException e) {
			LOG.error("Se ha producido un error al intentar añadir propiedad personalizada {}", propertyName, e);
			added = false;
		}
		return added;
	}

	@Override
	public String generatePropertyName(ApplicationEntity entity) {
		return assistant.generatePropertyName(entity);
	}

	@Override
	public String getProperty(String propertyName) {
		String propValue = null;
		try {
			propValue = assistant.getProperty(propertyName);
		} catch (IOException e) {
			LOG.error("Se produjo un error al intentar encontrar la propiedad {}", propertyName, e);
		}
		return propValue;
	}

}
