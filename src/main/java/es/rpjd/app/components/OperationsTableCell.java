package es.rpjd.app.components;

import es.rpjd.app.hibernate.entity.ProductOrder;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.ApplicationModel;
import es.rpjd.app.model.OrderModel;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/**
 * Celda personalizada para, actualmente, incluir botones de incremento y decremento
 * de cantidades de producto en una comanda.
 * @param <T>
 */
public class OperationsTableCell<T> extends TableCell<T, Void> {
	
    private final HBox hbox = new HBox(5); // Espaciado entre botones
    private final Button increaseButton = new Button(I18N.getString("app.lang.orders.po.btn.inc"));
    private final Button decreaseButton = new Button(I18N.getString("app.lang.orders.po.btn.dec"));

    public OperationsTableCell(ApplicationModel model) {
        // Configuración de los botones
    	increaseButton.setOnAction(event -> handleIncreaseAction(model));
    	decreaseButton.setOnAction(event -> handleDecreaseAction(model));
        hbox.getChildren().addAll(increaseButton, decreaseButton);
    }

    private void handleIncreaseAction(ApplicationModel model) {
    	T item = getTableView().getItems().get(getIndex());
    	if (item instanceof ProductOrder productOrder) {
    		model.editionModeProperty().set(true);
    		productOrder.setQuantity(productOrder.getQuantity()+1);
    	}
    	getTableView().refresh();
    }

    private void handleDecreaseAction(ApplicationModel model) {
    	T item = getTableView().getItems().get(getIndex());
        if (item instanceof ProductOrder productOrder) {
        	model.editionModeProperty().set(true);
        	Integer decrease = productOrder.getQuantity()-1;
        	if (decrease < 1) {
        		getTableView().getItems().remove(productOrder);
        	} else {
        		productOrder.setQuantity(decrease);
        	}
        	getTableView().refresh();
        }
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getIndex() >= getTableView().getItems().size()) {
            setGraphic(null);
        } else {
            setGraphic(hbox);
        }
    }
}

