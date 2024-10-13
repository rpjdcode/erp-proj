package es.rpjd.app;

import es.rpjd.app.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private RootController root;
	
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new RootController();
		Scene scene = new Scene(root.getView());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
