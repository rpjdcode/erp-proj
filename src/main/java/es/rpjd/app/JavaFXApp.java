package es.rpjd.app;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import es.rpjd.app.i18n.I18N;
import es.rpjd.app.spring.SpringConfig;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(JavaFXApp.class);
	
	private Environment env;

	private AnnotationConfigApplicationContext context;

	@Override
	public void init() throws Exception {
		LOG.info("Inicializando contexto de aplicación");
		context = new AnnotationConfigApplicationContext(SpringConfig.class);
		env = context.getEnvironment();
		
		Locale systemLocale = Locale.getDefault();
		
		LOG.info("Se intentará establecer el idioma de aplicación a {}", systemLocale.getLanguage());
		I18N.load(Locale.getDefault());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		LOG.info("Comenzando con ejecución de aplicación");
		
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		Parent root = loader.load("/fxml/root.fxml", SpringConstants.BEAN_CONTROLLER_ROOT);

		Scene scene = new Scene(root);
		primaryStage.setTitle(env.getProperty("app.name"));
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void stop() throws Exception {
		context.close();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
