package es.rpjd.app.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.entity.ProductOrder;
import es.rpjd.app.jasper.ProductOrderJRDataSource;
import es.rpjd.app.service.JasperReportService;
import es.rpjd.app.utils.AppUtils;
import es.rpjd.app.utils.StringFormatUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class JasperReportServiceImpl implements JasperReportService {

	@Override
	public void generateOrderReport(Map<String, Object> params, List<ProductOrder> productOrders) throws JRException {
		if (productOrders != null && !productOrders.isEmpty()) {
			Order order = productOrders.get(0).getOrder();
			// Carga de archivo .jasper o .jrxml
			InputStream stream = getClass().getResourceAsStream("/reports/order_invoice.jasper");

			if (!params.containsKey("LogoPath")) {
				params.put("LogoPath", getClass().getResourceAsStream("/App_Logo_v1.png"));
			}

			ProductOrderJRDataSource customSource = new ProductOrderJRDataSource(productOrders);

			// Cargar el report con datos
			JasperPrint print = JasperFillManager.fillReport(stream, params, customSource);

			// Exportar a PDF
			JasperExportManager.exportReportToPdfFile(print,
					String.format(StringFormatUtils.PENTA_PARAMETER, AppUtils.APP_PDF_DIRECTORY, File.separator,
							Constants.PDF.PDF_ORDER_FOLDER, File.separator,
							order.getOrderCode().concat(Constants.FileExtensions.FILE_EXTENSION_PDF)));
		}
	}
}
