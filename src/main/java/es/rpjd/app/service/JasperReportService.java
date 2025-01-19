package es.rpjd.app.service;

import java.util.List;
import java.util.Map;

import es.rpjd.app.hibernate.entity.ProductOrder;
import net.sf.jasperreports.engine.JRException;

public interface JasperReportService {

	/**
	 * Métododo para exportar los datos de una comanda a PDF
	 * @param params Parámetros para pasarle al informe
	 * @param productOrders Datos de productos que componen la comanda
	 * @throws JRException
	 */
	void generateOrderReport(Map<String, Object> params, List<ProductOrder> productOrders) throws JRException;
}
