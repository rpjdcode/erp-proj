package es.rpjd.app.jasper;

import java.util.Iterator;
import java.util.List;

import es.rpjd.app.hibernate.entity.ProductOrder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ProductOrderJRDataSource implements JRDataSource {

    private final Iterator<ProductOrder> iterator;
    private ProductOrder current;
    
    public ProductOrderJRDataSource(List<ProductOrder> data) {
    	this.iterator = data.iterator();
    }
	
	@Override
	public boolean next() throws JRException {
		if (iterator.hasNext()) {
			current = iterator.next();
			return true;
		}
		return false;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		String fieldName = jrField.getName().toUpperCase();
		switch (fieldName.toUpperCase()) {
		case "ORDER":
			return current.getOrder();
			
		case "PRODUCT":
			return current.getProduct();
		
		case "QUANTITY":
			return current.getQuantity();
			
		case "ORDER_CODE":
			return current.getOrder().getOrderCode();
			
		case "PRODUCT_CODE":
			return current.getProduct().getProductCode();
			
		case "PROPERTY_NAME":
			return current.getProduct().getPropertyName();
		
		case "CANTIDAD":
			return current.getQuantity();
			
		case "PRECIO/UNIDAD":
			return current.getProduct().getPrice();
		default:
            throw new IllegalArgumentException("Campo no encontrado: " + fieldName);
		}
	}

}
