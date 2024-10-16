package es.rpjd.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.rpjd.app.spring.SpringConfig;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

	private AnnotationConfigApplicationContext context;

	@Override
	public void init() throws Exception {
		context = new AnnotationConfigApplicationContext(SpringConfig.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		Parent root = loader.load("/fxml/root.fxml", SpringConstants.BEAN_CONTROLLER_ROOT);

		Scene scene = new Scene(root);
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
