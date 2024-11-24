package es.rpjd.app.controller;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import es.rpjd.app.ApplicationConfigurer;
import es.rpjd.app.config.ApplicationConfiguration;
import es.rpjd.app.config.Theme;
import es.rpjd.app.constants.Constants;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.i18n.SupportedLocale;
import es.rpjd.app.model.ConfigModel;
import es.rpjd.app.spring.SpringConstants;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Controller(value = SpringConstants.BEAN_CONTROLLER_CONFIG)
public class ConfigController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

	@FXML
	private ComboBox<SupportedLocale> langCombo;

	@FXML
	private Label langLabel;

	@FXML
	private ComboBox<Theme> themeCombo;

	@FXML
	private Label themeLabel;

	@FXML
	private GridPane view;

	@FXML
	private HBox buttonBox;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private ConfigModel model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new ConfigModel();
		LOG.info("Inicializando controlador de configuración");

		langCombo.itemsProperty().bind(model.supportedLanguagesProperty());

		langCombo.getSelectionModel().select(SupportedLocale.localeCasting(I18N.getApplicationLocale()));

		langCombo.getSelectionModel().selectedItemProperty()
				.addListener((o, ov, nv) -> LOG.debug(String.format("OV: %s --- NV: %s", ov, nv)));

		// Obtención y vinculación de temas
		obtainThemes();
		themeCombo.itemsProperty().bind(model.applicationThemesProperty());

		Theme temaSeleccionado = new Theme(Constants.CSS.CSS_THEME_NAME_TEST, Paths.get(Constants.CSS.CSS_THEME_TEST));

		LOG.info("Tema seleccionado: {}", temaSeleccionado.getPath());
		LOG.info("App Tema seleccionado: {}", ApplicationConfigurer.getApplicationConfiguration().getTheme().getPath());

		ObservableList<Theme> temas = themeCombo.getItems();

		for (Theme theme : temas) {
			String infoTema = String.format("NOMBRE TEMA: %s --- PATH: %s", theme.getName(), theme.getPath());
			LOG.info(infoTema);
		}

		if (themeCombo.getItems().contains(temaSeleccionado)) {
			ApplicationConfiguration conf = ApplicationConfigurer.getApplicationConfiguration();
			themeCombo.getSelectionModel().select(conf.getTheme());
		}

	}

	@Override
	public Node getView() {
		return view;
	}

	@Override
	public void clearResources() {
		LOG.info("Se llama al cleanResources de ConfigController");
		langCombo.itemsProperty().unbind();
		themeCombo.itemsProperty().unbind();

	}

	/**
	 * Método encargado de recorrer las hojas estilos proporcionadas a la aplicación
	 * para recoger sus nombres de temas y añadirlos al modelo
	 */
	private void obtainThemes() {

		List<Theme> themes = ApplicationConfigurer.getApplicationThemes();
		model.applicationThemesProperty().addAll(themes);
	}

	@FXML
	void onSaveAction(ActionEvent event) {
		LOG.info("Aplicando configuración de aplicación");
		ApplicationConfiguration config = new ApplicationConfiguration();
		config.setLanguage(langCombo.getSelectionModel().getSelectedItem());
		config.setTheme(themeCombo.getSelectionModel().getSelectedItem());

		ApplicationConfigurer.saveConfig(config);

		((Stage) getView().getScene().getWindow()).close();
	}

	@FXML
	void onCancelAction(ActionEvent event) {
		LOG.info("Cancelando modificación de configuración");
		((Stage) getView().getScene().getWindow()).close();
	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		throw new UnsupportedOperationException("Operación no permitida. No es necesario su uso en este controlador");
	}

}
