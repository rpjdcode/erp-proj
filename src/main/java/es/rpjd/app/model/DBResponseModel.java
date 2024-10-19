package es.rpjd.app.model;

import es.rpjd.app.constants.DBResponseStatus;

/**
 * 
 * @param <T>
 */
public class DBResponseModel<T> {

	private DBResponseStatus status;
	private String message;
	private T data;

	public DBResponseModel() {
	}

	/**
	 * 
	 * @param status Tipo de respuesta de base de datos
	 * @param message Mensaje explicativo
	 * @param data Información a retornar
	 */
	public DBResponseModel(DBResponseStatus status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public DBResponseStatus getStatus() {
		return status;
	}

	public void setStatus(DBResponseStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
