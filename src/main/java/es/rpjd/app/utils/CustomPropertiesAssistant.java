package es.rpjd.app.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.hibernate.entity.ApplicationEntity;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductType;

/**
 * Clase que actúa como soporte para trabajar con propiedades personalizadas
 * establecidas por el usuario y utilizarlas en la aplicación
 */
public class CustomPropertiesAssistant {
	
	/**
	 * Clase interna que se encargará de generar propiedades personalizadas en base al tipo de entidad de BBDD
	 */
	protected class CustomPropertyGenerator {
		
		private ApplicationEntity entity;
		
		private CustomPropertyGenerator(ApplicationEntity entity) {
			this.entity = entity;
		}
		
		protected void changeEntity(ApplicationEntity entity) {
			this.entity = entity;
		}
		
		protected String generateProperty() {
			String out = null;
			if (entity instanceof Product p) {
				out = String.format("custom.prod.%s", p.getProductCode());
			} else if (entity instanceof ProductType pt) {
				out = String.format("custom.prodtype.%s", pt.getCode());
			}
			
			return out;
		}
		
		
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomPropertiesAssistant.class);

	/**
	 * Reemplaza el valor de una property personalizada en caso de existir
	 * @param name
	 * @param value
	 * @throws IOException
	 */
	private void replacePropertyValue(String name, String value) throws IOException {
		String propertiesFile = System.getProperty(Constants.CUSTOM_PROPS_PROPERTY);
		File file = new File(propertiesFile);
		List<String> lines = Files.readAllLines(Path.of(file.toURI()));

		List<String> updated = lines.stream()
				.map(line -> line.contains(name) ? String.format("%s=%s", name, value) : line).toList();

		Files.write(Path.of(file.toURI()), updated, StandardOpenOption.TRUNCATE_EXISTING);
	}

	/**
	 * Añade en el fichero de custom.properties la propiedad y su valor de property correspondiente
	 * @param propertyName
	 * @param value
	 * @throws IOException
	 */
	public void addProperty(String propertyName, String value) throws IOException {
		String propertiesFile = System.getProperty(Constants.CUSTOM_PROPS_PROPERTY);
		File file = new File(propertiesFile);
		File created = FileUtils.createNewFile(file);

		if (created == null) {
			return;
		}

		String existingPropertyValue = getProperty(propertyName);

		if (existingPropertyValue != null) {
			// Si el valor de la propiedad en fichero no es igual al nuevo proporcionado, reemplazamos
			if (!existingPropertyValue.equals(value)) {
				replacePropertyValue(propertyName, value);
			}
			
		} else {
			LOG.info("La propiedad {} no existe", propertyName);
			Path filePath = Path.of(created.toURI());
			
			String content = String.format("%s=%s%s", propertyName, value, System.lineSeparator());
			
			Files.writeString(filePath, content, StandardOpenOption.APPEND);
		}

	}

	/**
	 * Obtiene una propiedad almacenada en el fichero de custom.properties
	 * @param property
	 * @return
	 * @throws IOException
	 */
	public String getProperty(String property) throws IOException {
		String propertiesPath = System.getProperty(Constants.CUSTOM_PROPS_PROPERTY);
		File fi = FileUtils.getFile(propertiesPath);

		if (fi.exists()) {
			List<String> lines = Files.readAllLines(Path.of(fi.toURI()));

			for (String line : lines) {
				String prop = line.substring(0, line.indexOf("="));
				if (prop.equals(property)) {
					return line.substring(line.indexOf("=") + 1, line.length());
				}
			}
		}

		return null;
	}
	
	/**
	 * Cuenta la cantidad de propiedades encontradas en el fichero
	 * @return
	 * @throws IOException
	 */
	public int countProperties() throws IOException {
		String propertiesPath = System.getProperty(Constants.CUSTOM_PROPS_PROPERTY);
		File fi = FileUtils.getFile(propertiesPath);
		
		if (fi.exists()) {
			List<String> lines = Files.readAllLines(Path.of(fi.toURI()));
			return lines.size();
		}
		
		return 0;
	}
	
	/**
	 * Genera un nombre de property en base al identificador único (código) de cada tipo de entidad de base de datos
	 * @param entity Entidad de base de datos
	 * @return Nombre de propiedad personalizada
	 */
	public String generatePropertyName(ApplicationEntity entity) {
		CustomPropertyGenerator generator = new CustomPropertyGenerator(entity);
		return generator.generateProperty();
	}
}
