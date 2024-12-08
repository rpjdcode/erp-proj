package es.rpjd.app.listeners;

import es.rpjd.app.controller.OrderController;
import es.rpjd.app.hibernate.entity.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Listener utilizado para detectar cambios en la propiedad de comanda
 * seleccionada, se encarga de ocultar/mostrar el panel de pestañas y el
 * contenedor con el mensaje de que no ha seleccionado nada aún.
 * 
 * Implementación exclusiva para el controlador de comandas
 * 
 * @see OrderController
 */
public class SelectedOrderChangedListener implements ChangeListener<Order> {

	
	private VBox detailsBox;
	private VBox bottomBox;
	private HBox ordersBox;
	private VBox noOrderSelectedBoxTop;
	private VBox noOrderSelectedBox;

	public SelectedOrderChangedListener(VBox detailsBox, VBox bottomBox, VBox noOrderSelectedBoxTop,
			VBox noOrderSelectedBox, HBox ordersBox) {
		super();
		this.detailsBox = detailsBox;
		this.bottomBox = bottomBox;
		this.noOrderSelectedBox = noOrderSelectedBox;
		this.ordersBox = ordersBox;
		this.noOrderSelectedBoxTop = noOrderSelectedBoxTop;
	}

	@Override
	public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
		if (newValue != null) {
			if (bottomBox.getChildren().contains(noOrderSelectedBox)) {
				detailsBox.getChildren().remove(noOrderSelectedBoxTop);
				bottomBox.getChildren().remove(noOrderSelectedBox);
			}
			if (!bottomBox.getChildren().contains(ordersBox)) {
				// TODO: Añadir en el contenedor de detalles el nodo correspondiente
				bottomBox.getChildren().add(ordersBox);
				bottomBox.requestFocus();
			}

		} else {
			if (bottomBox.getChildren().contains(ordersBox)) {
				// TODO: Eliminar del contenedor de detalles el nodo correspondiente
				bottomBox.getChildren().remove(ordersBox);
			}
			if (!bottomBox.getChildren().contains(noOrderSelectedBox)) {
				detailsBox.getChildren().add(noOrderSelectedBoxTop);
				bottomBox.getChildren().add(noOrderSelectedBox);
			}

		}

	}

}
