package es.rpjd.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.controller.product.ProductFilesController;
import es.rpjd.app.controller.product.ProductManagementController;
import es.rpjd.app.controller.product.ProductStadisticsController;
import es.rpjd.app.controller.product.type.ProductTypeFilesController;
import es.rpjd.app.controller.product.type.ProductTypeManagementController;
import es.rpjd.app.controller.product.type.ProductTypeStadisticsController;
import es.rpjd.app.enums.ProductOptions;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.ProductModel;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.StringFormatUtils;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT)
public class ProductController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	private ApplicationContext context;
	private Environment env;

	private ProductTypeService productTypeService;

	private ProductManagementController pmc;
	private ProductStadisticsController psc;
	private ProductFilesController pfc;

	private ProductTypeManagementController ptmc;
	private ProductTypeStadisticsController ptsc;
	private ProductTypeFilesController ptfc;

	@FXML
	private Accordion accordion;

	@FXML
	private TitledPane productsPane;

	@FXML
	private VBox productsBox;

	@FXML
	private TitledPane typesPane;

	@FXML
	private BorderPane productsContent;

	@FXML
	private BorderPane typesContent;

	@FXML
	private VBox productsOptionsBox;

	@FXML
	private VBox typesOptionsBox;

	@FXML
	private Label productsManagementLabel;

	@FXML
	private Label productsStadisticsLabel;

	@FXML
	private Label productsFilesLabel;

	@FXML
	private Label typesManagementLabel;

	@FXML
	private Label typesStadisticsLabel;

	@FXML
	private Label typesFilesLabel;

	@FXML
	private Label noOptionLabel;

	@FXML
	private Label noTypeOptionLabel;

	@FXML
	private VBox productsView;

	@FXML
	private VBox typesView;

	@FXML
	private VBox typesBox;

	@FXML
	private VBox view;

	private ProductModel model;

	@Autowired
	public ProductController(ApplicationContext context, Environment env, ProductService productService,
			ProductTypeService typesService) {
		this.context = context;
		this.env = env;
		this.productTypeService = typesService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new ProductModel();

		ChangeListener<ResourceBundle> changeListener = (o, ov, nv) -> updateTexts(nv);

		model.setI18nListener(changeListener);
		I18N.bundleProperty().addListener(changeListener);

		DBResponseModel<List<ProductType>> response = productTypeService.getTypes();
		LOG.info("Respuesta obtenida: {}", response.getMessage());

		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);

		try {
			String pmcPath = String.format(StringFormatUtils.DOUBLE_PARAMETER,
					env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "products/management/productManagement.fxml");
			loader.load(pmcPath, SpringConstants.BEAN_CONTROLLER_PRODUCT_MANAGEMENT);
			pmc = context.getBean(ProductManagementController.class);

			String ptmcPath = String.format(StringFormatUtils.DOUBLE_PARAMETER,
					env.getProperty(SpringConstants.PROPERTY_FXML_PATH),
					"products/type/management/productTypesManagement.fxml");
			loader.load(ptmcPath, SpringConstants.BEAN_CONTROLLER_PRODUCT_TYPE_MANAGEMENT);
			ptmc = context.getBean(ProductTypeManagementController.class);
		} catch (IOException e) {
			LOG.error("Se ha producido una IOException al cargar controlador ProductManagement : {}", e.getMessage());
		}

		model.productsOptionProperty().addListener((o, ov, nv) -> {
			LOG.info("Cambió el valor de la opción seleccionada: {}", nv);
			switch (nv) {
			case ProductOptions.PROD_MANAGEMENT:
				loadProductsView(pmc);
				break;

			case ProductOptions.PROD_STADISTICS:
				loadProductsView(psc);
				break;

			case ProductOptions.PROD_FILES:
				loadProductsView(pfc);
				break;

			case ProductOptions.TYPE_MANAGEMENT:
				loadTypesView(ptmc);
				break;

			case ProductOptions.PROD_NONE:
				// Se indica que no se ha seleccionado ninguna opción
				noProductOptionDisplayed();
				break;
				
			case ProductOptions.TYPE_STADISTICS:
				loadTypesView(ptsc);
				break;
				
			case ProductOptions.TYPE_FILES:
				loadTypesView(ptfc);
				break;

			case ProductOptions.TYPE_NONE:
				noProductTypeOptionDisplayed();
				break;
			default:
				break;
			}

		});

		model.selectedLabelProperty().addListener((o, ov, nv) -> {
			LOG.info("Cambió la label seleccionada");
			markLabelAsSelected(nv, ov);
		});

	}

	@FXML
	void onProductOptionClick(MouseEvent event) {
		Label src = (Label) event.getSource();

		model.setSelectedLabel(src == model.getSelectedLabel() ? null : src);
	}

	@FXML
	void onTypeOptionClick(MouseEvent event) {
		LOG.info("Click en label de type");
		Label src = (Label) event.getSource();

		model.setSelectedLabel(src == model.getSelectedLabel() ? null : src);
	}

	/**
	 * Método encargado de marcar la label seleccionada con un estilo identificativo
	 * de selección de opción
	 * 
	 * @param label
	 */
	private void markLabelAsSelected(Label label, Label oldLabel) {

		if (label == null) {

			if (oldLabel != null) {
				unmarkLabel(oldLabel);
				model.productsOptionProperty()
						.set(isProductLabel(oldLabel) ? ProductOptions.PROD_NONE : ProductOptions.TYPE_NONE);
			}
		} else {
			if (oldLabel != null) {
				unmarkLabel(oldLabel);
			}
			markLabel(label);
		}

	}

	/**
	 * Verifica si la label pertenece a las opciones de tipos de producto
	 * 
	 * @param label
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isTypeLabel(Label label) {
		return label == typesManagementLabel || label == typesStadisticsLabel || label == typesFilesLabel;
	}

	/**
	 * Verifica si la label pertenece a las opciones de productos
	 * 
	 * @param label
	 * @return
	 */
	private boolean isProductLabel(Label label) {
		return label == productsManagementLabel || label == productsStadisticsLabel || label == productsFilesLabel;
	}

	/**
	 * Marca la label seleccionada como activa y establece el tipo de ProductOption
	 * para cargar el controlador
	 * 
	 * @param label
	 */
	private void markLabel(Label label) {
		label.setTextFill(Constants.OPTION_ACTIVE);
		if (isProductLabel(label)) {
			if (label == productsManagementLabel) {
				model.setProductsOption(ProductOptions.PROD_MANAGEMENT);
			} else if (label == productsStadisticsLabel) {
				model.setProductsOption(ProductOptions.PROD_STADISTICS);
			} else if (label == productsFilesLabel) {
				model.setProductsOption(ProductOptions.PROD_FILES);
			}
		} else {
			if (label == typesManagementLabel) {
				model.setProductsOption(ProductOptions.TYPE_MANAGEMENT);
			} else if (label == typesStadisticsLabel) {
				model.setProductsOption(ProductOptions.TYPE_STADISTICS);
			} else if (label == typesFilesLabel) {
				model.setProductsOption(ProductOptions.TYPE_FILES);
			}
		}

	}

	private void unmarkLabel(Label label) {
		label.setTextFill(Constants.OPTION_DEFAULT);
	}

	/**
	 * Método encargado de incrustar un controlador en el cuadro de vista de
	 * opciones de producto
	 * 
	 * @param controller
	 */
	private void loadProductsView(ApplicationController controller) {
		productsView.getChildren().clear();
		if (controller == null) {
			productsView.setAlignment(Pos.CENTER);
			productsView.getChildren().add(new Label("No se pudo cargar el controlador"));
		} else {
			productsView.setAlignment(Pos.TOP_LEFT);
			productsView.getChildren().add(controller.getView());
		}

	}

	private void loadTypesView(ApplicationController controller) {
		typesView.getChildren().clear();
		if (controller == null) {
			typesView.setAlignment(Pos.CENTER);
			typesView.getChildren().add(new Label("No se pudo cargar el controlador"));
		} else {
			typesView.setAlignment(Pos.TOP_LEFT);
			typesView.getChildren().add(controller.getView());
		}
	}

	/**
	 * Método encargado de mostrar un cartel de opción no seleccionada
	 */
	private void noProductOptionDisplayed() {
		productsView.getChildren().clear();
		productsView.setAlignment(Pos.CENTER);
		productsView.getChildren().add(noOptionLabel);
	}

	private void noProductTypeOptionDisplayed() {
		typesView.getChildren().clear();
		typesView.setAlignment(Pos.CENTER);
		typesView.getChildren().add(noTypeOptionLabel);
	}

	@Override
	public VBox getView() {
		return view;
	}

	@Override
	public void clearResources() {
		I18N.bundleProperty().removeListener(model.getI18nListener());

	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		productsPane.setText(bundle.getString("app.products.pane.prod"));
		typesPane.setText(bundle.getString("app.products.pane.prod.type"));
		productsManagementLabel.setText(bundle.getString("app.lang.products.label.manage"));
		productsStadisticsLabel.setText(bundle.getString("app.lang.products.label.stads"));
		productsFilesLabel.setText(bundle.getString("app.lang.products.label.files"));
		typesManagementLabel.setText(bundle.getString("app.lang.products.types.label.manage"));
		typesStadisticsLabel.setText(bundle.getString("app.lang.products.types.label.stads"));
		typesFilesLabel.setText(bundle.getString("app.lang.products.types.label.files"));
		noOptionLabel.setText(bundle.getString("app.lang.products.no.option"));
		noTypeOptionLabel.setText(bundle.getString("app.lang.products.no.option"));

	}

}
