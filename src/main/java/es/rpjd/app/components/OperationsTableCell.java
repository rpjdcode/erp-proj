package es.rpjd.app.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.ApplicationModel;
import es.rpjd.app.model.observables.ProductOrderObservable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/**
 * Celda personalizada para, actualmente, incluir botones de incremento y
 * decremento de cantidades de producto en una comanda.
 * 
 * @param <T>
 */
public class OperationsTableCell<T> extends TableCell<T, Void> {
	
	private static final Logger LOG = LoggerFactory.getLogger(OperationsTableCell.class);
	
	private ApplicationModel model;

	private final HBox hbox = new HBox(5); // Espaciado entre botones
	private final Button increaseButton = new Button(I18N.getString("app.lang.orders.po.btn.inc"));
	private final Button decreaseButton = new Button(I18N.getString("app.lang.orders.po.btn.dec"));

	public OperationsTableCell(ApplicationModel model) {
		this.model = model;
		// Configuración de los botones
		increaseButton.setOnAction(event -> handleIncreaseAction());
		decreaseButton.setOnAction(event -> handleDecreaseAction());
		hbox.getChildren().addAll(increaseButton, decreaseButton);
	}

	private void handleIncreaseAction() {
		LOG.info("Llamando a incrementar");
		T item = getTableView().getItems().get(getIndex());
		if (item instanceof ProductOrderObservable productOrder) {
			if (!model.isEditionMode()) {
				model.setEditionMode(true);
			}
			productOrder.setQuantity(productOrder.getQuantity() + 1);
		}
		getTableView().refresh();
	}

	private void handleDecreaseAction() {
		LOG.info("Llamando a decrementar");
		T item = getTableView().getItems().get(getIndex());

		if (item instanceof ProductOrderObservable productOrder) {

			if (!model.isEditionMode()) {
				model.setEditionMode(true);
			}
			Integer decrease = productOrder.getQuantity() - 1;
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
