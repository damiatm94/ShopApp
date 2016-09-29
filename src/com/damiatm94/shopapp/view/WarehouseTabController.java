package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Product;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> amountColumn;
    @FXML
    private TableColumn<Product, Integer> minAmountColumn;

    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button newButton;


    {
        // Initialize the product table with four columns.
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        minAmountColumn.setCellValueFactory(cellData -> cellData.getValue().minAmountProperty().asObject());


        // Enable deleting only if the product in the table is selected
        deleteButton.disableProperty().bind(Bindings.isEmpty(productsTable.getSelectionModel().getSelectedItems()));

        // Enable editing only if the product in the table is selected
        editButton.disableProperty().bind(Bindings.isEmpty(productsTable.getSelectionModel().getSelectedItems()));
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    public void handleDeleteProduct()
    {
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0)
        {
            productsTable.getItems().remove(selectedIndex);
            setSelectedIndexForSales(selectedIndex);
            salesTab.handleDeleteProduct();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new product.
     */
    @FXML
    public void handleNewProduct(Product newProduct)
    {
        newProduct = new Product();
        boolean isNewProduct = true;
        boolean okClicked = mainApp.showProductEditDialog(newProduct, isNewProduct);

        if (okClicked)
        {
            mainApp.getProductData().add(newProduct);
            salesTab.addProductToSalesPanel(newProduct);
            salesTab.handleNewProduct(newProduct);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected product.
     */
    @FXML
    public void handleEditProduct()
    {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        setSelectedIndexForSales(selectedIndex);

        if (selectedProduct != null)
        {
            boolean isNewProduct = false;
            boolean okClicked = mainApp.showProductEditDialog(selectedProduct, isNewProduct);
            if (okClicked)
            {
                setSelectedProduct(selectedProduct);
                salesTab.handleEditProduct();
            }
        }
    }

    @Override
    public void addProductToSalesPanel(Product product)
    {
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        // Add observable list data to the table
        productsTable.setItems(getProductData());
    }
}
