package es.rpjd.app.listeners;

import es.rpjd.app.controller.OrderController;
import es.rpjd.app.hibernate.entity.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * Listener utilizado para detectar cambios en la propiedad de comanda seleccionada, se encarga
 * de ocultar/mostrar el panel de pestañas y la caja con el mensaje de que no ha seleccionado nada aún.
 * 
 * Implementación exclusiva para el controlador de comandas 
 * @see OrderController
 */
public class SelectedOrderChangedListener implements ChangeListener<Order> {
	
	private TabPane ordersTabPane;
	private SplitPane ordersSplitPane;
	private VBox noOrderSelectedBox;
	
	public SelectedOrderChangedListener(SplitPane ordersSplitPane, VBox noOrderSelectedBox, TabPane ordersTabPane) {
		super();
		this.ordersSplitPane = ordersSplitPane;
		this.noOrderSelectedBox = noOrderSelectedBox;
		this.ordersTabPane = ordersTabPane;
	}

	@Override
	public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
		if (newValue != null) {
			if (ordersSplitPane.getItems().contains(noOrderSelectedBox)) {
				ordersSplitPane.getItems().remove(noOrderSelectedBox);
			}
			if (!ordersSplitPane.getItems().contains(ordersTabPane)) {
				ordersSplitPane.getItems().add(ordersTabPane);
				ordersTabPane.requestFocus();
			}
			
		} else {
			if (ordersSplitPane.getItems().contains(ordersTabPane)) {
				ordersSplitPane.getItems().remove(ordersTabPane);
			}
			if (!ordersSplitPane.getItems().contains(noOrderSelectedBox)) {
				ordersSplitPane.getItems().add(noOrderSelectedBox);
			}
			
		}
		
	}

}
