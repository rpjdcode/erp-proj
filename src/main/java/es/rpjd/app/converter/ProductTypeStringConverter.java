package es.rpjd.app.converter;

import java.io.IOException;

import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.utils.CustomPropertiesAssistant;
import javafx.util.StringConverter;

/**
 * Convertidor de objeto ProductType a String.
 * 
 * Si el propertyName del ProductType se detecta como una propiedad por defecto de la aplicación
 * (incluida en internacionalización), se leerá desde estos recursos. En caso contrario, se intenta
 * obtener el valor de la propiedad a través del asistente de propiedades personalizadas
 * 
 * @see CustomPropertiesAssistant
 * @see I18N
 */
public class ProductTypeStringConverter extends StringConverter<ProductType> {
	
	private CustomPropertiesAssistant assistant;
	
	public ProductTypeStringConverter() {
		super();
		assistant = new CustomPropertiesAssistant();
	}

	@Override
	public String toString(ProductType object) {
		String name = null;
		
		if (object != null) {
			String propertyName = object.getPropertyName();
			try {
				name = propertyName.contains("app.lang") ? I18N.getString(object.getPropertyName()) : assistant.getProperty(propertyName);
			} catch (IOException e) {
				name = object.getPropertyName();
			}
		}
		

		return object != null ? name : "";
	}

	@Override
	public ProductType fromString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
