package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Product;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static com.damiatm94.shopapp.MainApp.getProductData;
import static com.damiatm94.shopapp.view.SalesTabController.setSelectedIndexForSales;
import static com.damiatm94.shopapp.view.SalesTabController.setSelectedProduct;

/**
 * Created by damian on 29.09.16.
 */
public class WarehouseTabController implements ProductsListener
{
    SalesTabController salesTab = new SalesTabController();

    // Reference to the main application.
    private MainApp mainApp;

    private ProductsOverviewController mainController;

    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, Integer> amountColumn;
    @FXML private TableColumn<Product, Integer> minAmountColumn;

    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button newButton;

    public WarehouseTabController()
    {
    }

    @FXML
    private void initialize()
    {
        // some dummy products
        getProductData().add(new Product("Pendrive 32 GB", 30.0, 16, 10));
        getProductData().add(new Product("MemoryCard 16 GB", 45.99, 10, 8));
        getProductData().add(new Product("Screen Protector", 49.90, 4, 4));
        getProductData().add(new Product("USB Cabel", 14.49, 10, 8));
        getProductData().add(new Product("Protective Case", 64.99, 10, 10));
        getProductData().add(new Product("DVD-RW", 1.19, 20, 30));

        // Initialize the product table with four columns.
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        minAmountColumn.setCellValueFactory(cellData -> cellData.getValue().minAmountProperty().asObject());

        productsTable.setItems(getProductData());


        // Enable deleting only if the product in the table is selected
        deleteButton.disableProperty().bind(Bindings.isEmpty(productsTable.getSelectionModel().getSelectedItems()));

        // Enable editing only if the product in the table is selected
        editButton.disableProperty().bind(Bindings.isEmpty(productsTable.getSelectionModel().getSelectedItems()));
    }

    public void addNewProduct(Product newProduct)
    {
        boolean isNewProduct = true;
        boolean okClicked = showProductNewOrEditDialog(newProduct, isNewProduct);

        if (okClicked)
        {
            getProductData().add(newProduct);
        }
    }

    public void editProduct()
    {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        setSelectedIndexForSales(selectedIndex);

        if (selectedProduct != null)
        {
            boolean isNewProduct = false;
            boolean okClicked = showProductNewOrEditDialog(selectedProduct, isNewProduct);
            if (okClicked)
            {
                setSelectedProduct(selectedProduct);
                salesTab.editProduct();
            }
        }
    }

    public void deleteProduct()
    {
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0)
        {
            productsTable.getItems().remove(selectedIndex);
            setSelectedIndexForSales(selectedIndex);
            salesTab.deleteProduct();
        }
    }

    @FXML public void handleButtonNew()
    {
        mainController.addNewProduct();
    }

    public boolean showProductNewOrEditDialog(Product product, boolean isNewProduct)
    {
        try
        {
            mainApp = new MainApp();
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductEditDialog.fxml"));
            VBox page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //dialogStage.setResizable(false);
            //primaryStage.setResizable(true);
            if (isNewProduct)
            {
                dialogStage.setTitle("Add Product");
            } else
            {
                dialogStage.setTitle("Edit Product");
            }

            // Set the person into the controller.
            ProductNewOrEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(product, isNewProduct);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void initMainController(ProductsOverviewController productsOverviewController)
    {
        mainController = productsOverviewController;
    }
}
