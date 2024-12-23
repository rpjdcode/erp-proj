package es.rpjd.app.listeners;

import es.rpjd.app.controller.OrderController;
import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.utils.StringFormatUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
		detailsBox.getChildren().clear();
		if (newValue != null) {
			if (newValue.getProductsOrder().isEmpty()) {
				detailsBox.getChildren().add(new Label("No hay detalles de comanda"));
				detailsBox.setAlignment(Pos.CENTER);
			} else {
				detailsBox.setAlignment(Pos.TOP_LEFT);
				Label detailsLabel = new Label(I18N.getString("app.lang.orders.details.summary"));
				detailsLabel.setStyle("-fx-font-weight: bold;");
				detailsBox.getChildren().add(detailsLabel);
				detailsBox.setSpacing(5);
				detailsBox.setPadding(new Insets(5, 5, 5, 5));
				GridPane detailsPane = new GridPane(5, 5);
				detailsPane.addRow(0, new Label(String.format(I18N.getString("app.lang.orders.details.quantity"),
						newValue.getProductsOrder().size())));
				detailsPane.addRow(1, new Label(
						String.format(I18N.getString("app.lang.orders.details.amount"), newValue.calculateAmount())));
				detailsBox.getChildren().add(detailsPane);
			}

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
