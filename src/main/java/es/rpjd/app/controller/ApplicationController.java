package es.rpjd.app.controller;

import java.util.ResourceBundle;

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
	
	/**
	 * La implementación de este método definirá los textos de la interfaz a actualizar
	 * cuando se realiza un cambio de lenguaje en la aplicación
	 */
	void updateTexts(ResourceBundle bundle);
}
