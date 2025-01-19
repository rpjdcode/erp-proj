package es.rpjd.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import es.rpjd.app.controller.RootController;
import es.rpjd.app.spring.SpringConfig;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.StringFormatUtils;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal de aplicación JavaFX.
 * 
 * En esta clase se definen los métodos de inicialización de contexto, arranque y detención de la aplicación
 */
public class JavaFXApp extends Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(JavaFXApp.class);
	
	
	private Environment env;

	private AnnotationConfigApplicationContext context;

	@Override
	public void init() throws Exception {
		LOG.info("Inicializando contexto de aplicación");
		context = new AnnotationConfigApplicationContext(SpringConfig.class);
		env = context.getEnvironment();
		ApplicationConfigurer.initialize();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		Font.getFontNames().forEach(System.out::println);
		
		LOG.info("Comenzando con ejecución de aplicación");
		
		String fxmlRootPath = env.getProperty(SpringConstants.PROPERTY_FXML_PATH);
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		
		// Precarga de controladores para optimización
		ApplicationConfigurer.preloadControllers(loader, fxmlRootPath);
		
		String fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER, fxmlRootPath , "root.fxml");
		Parent root = loader.load(fxmlPath, SpringConstants.BEAN_CONTROLLER_ROOT);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/css/test.css");
		primaryStage.setWidth(600);
		primaryStage.setHeight(600);
		primaryStage.setTitle(env.getProperty("app.name"));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/logo.png")));
		primaryStage.setScene(scene);
		primaryStage.show();
		CSSFX.start();

	}

	@Override
	public void stop() throws Exception {
		LOG.info("Deteniendo aplicación");
		context.getBean(RootController.class).clearResources();
		context.close();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
