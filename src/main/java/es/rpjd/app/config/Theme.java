package es.rpjd.app.config;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * Clase que representa un tema de la aplicación, compuesto por su nombre y ubicación
 * del archivo .css
 */
public class Theme implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String path;

	public Theme(String name, Path path) {
		this.name = name;
		this.path = path.toString();
	}
	
	public Theme(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setPath(Path path) {
		if (path != null) {
			this.path = path.toString();
		} else {
			this.path = null;
		}
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Theme other = (Theme) obj;
	    return name.equals(other.name) && path.equals(other.path);
	}

	@Override
	public int hashCode() {
	    int result = name.hashCode();
	    result = 31 * result + path.hashCode();
	    return result;
	}
}
