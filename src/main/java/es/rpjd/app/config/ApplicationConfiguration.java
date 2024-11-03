package es.rpjd.app.config;

import java.io.Serializable;

import es.rpjd.app.i18n.SupportedLocale;

/**
 * Clase que representa la configuración utilizada por la aplicación
 */
public class ApplicationConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Theme theme;
	private SupportedLocale language;
	
	public ApplicationConfiguration() {}

	public ApplicationConfiguration(Theme theme, SupportedLocale lang) {
		this.theme = theme;
		this.language = lang;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public SupportedLocale getLanguage() {
		return language;
	}

	public void setLanguage(SupportedLocale language) {
		this.language = language;
	}
}
