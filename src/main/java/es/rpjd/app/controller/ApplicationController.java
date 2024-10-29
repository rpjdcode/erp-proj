package es.rpjd.app.controller;

import javafx.scene.Node;

/**
 * Define los métodos necesarios para ser considerado un controlador de la aplicación
 */
public interface ApplicationController {

	Node getView();
	
	/**
	 * La implementación de este método permitirá la liberación de recursos de un controlador
	 */
	void clearResources();
}
