package es.rpjd.app.spring;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;

/**
 * Clase encargada de cargar los controladores
 */
@Component
public class SpringFXMLLoader {

	private final ApplicationContext context;

	@Autowired
	public SpringFXMLLoader(ApplicationContext context) {
		this.context = context;
	}

	public <T> T load(String fxmlPath, String controllerName) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		loader.setControllerFactory(context::getBean);
		loader.setController(context.getBean(controllerName));
		//loader.setResources(null); // Internacionalización
		return loader.load();
	}
}
