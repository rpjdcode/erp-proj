package es.rpjd.app.spring;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import es.rpjd.app.i18n.I18N;
import javafx.fxml.FXMLLoader;

/**
 * Clase encargada de cargar los controladores
 */
@Component
public class SpringFXMLLoader {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringFXMLLoader.class);

	private final ApplicationContext context;

	@Autowired
	public SpringFXMLLoader(ApplicationContext context) {
		this.context = context;
	}

	public <T> T load(String fxmlPath, String controllerName) throws IOException {
		LOG.info("Se está llamando al load del controlador {}", controllerName);
		LOG.info("FXMLPath: {}", fxmlPath);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		loader.setControllerFactory(context::getBean);
		loader.setController(context.getBean(controllerName));
		loader.setResources(I18N.getResourceBundle());
		//loader.setResources(null); // Internacionalización
		return loader.load();
	}
}
