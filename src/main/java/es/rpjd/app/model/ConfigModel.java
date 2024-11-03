package es.rpjd.app.model;

import es.rpjd.app.config.Theme;
import es.rpjd.app.i18n.SupportedLocale;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConfigModel extends ApplicationModel {

	private ListProperty<SupportedLocale> supportedLanguages;
	private ListProperty<Theme> applicationThemes;

	public ConfigModel() {
		super();
		supportedLanguages = new SimpleListProperty<>(FXCollections.observableArrayList(SupportedLocale.values()));
		applicationThemes = new SimpleListProperty<>(FXCollections.observableArrayList());
	}

	public final ListProperty<SupportedLocale> supportedLanguagesProperty() {
		return this.supportedLanguages;
	}

	public final ObservableList<SupportedLocale> getSupportedLanguages() {
		return this.supportedLanguagesProperty().get();
	}

	public final void setSupportedLanguages(final ObservableList<SupportedLocale> supportedLanguages) {
		this.supportedLanguagesProperty().set(supportedLanguages);
	}

	public final ListProperty<Theme> applicationThemesProperty() {
		return this.applicationThemes;
	}

	public final ObservableList<Theme> getApplicationThemes() {
		return this.applicationThemesProperty().get();
	}

	public final void setApplicationThemes(final ObservableList<Theme> applicationThemes) {
		this.applicationThemesProperty().set(applicationThemes);
	}

}
