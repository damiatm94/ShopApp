package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.domain.ProductToDB;
import com.damiatm94.shopapp.model.Product;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import static com.damiatm94.shopapp.MainApp.getProductData;


/**
 * Created by damian on 24.07.16.
 */
public class ProductsOverviewController
{
    @FXML private WarehouseTabController warehouseTabController;
    @FXML private OrdersTabController ordersTabController;
    @FXML private SalesTabController salesTabController;

    private static List<AnchorPane> anchorPanes = new ArrayList<>();
    private static List<Label> labels = new ArrayList<>();
    private static List<TextField> textFields = new ArrayList<>();

    // Reference to the main application.
    private MainApp ourMainApp;

    public static List<TextField> getTextFields()
    {
        return textFields;
    }
    public static List<Label> getLabels()
    {
        return labels;
    }
    public static List<AnchorPane> getAnchorPanes()
    {
        return anchorPanes;
    }

    public ProductsOverviewController()
    {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
        warehouseTabController.initMainController(this);
        ordersTabController.initMainController(this);
        salesTabController.initMainController(this);
    }

    /**
     * This is a sample method checking if persisting objects to MySQL database work.
     * It will be assigned to another graphic component in the future.
     */
    @FXML
    private void handlePersistToDB()
    {
        ProductToDB.persistProductsToDB();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp)
    {
        this.ourMainApp = mainApp;
        //warehouseTabController.setMainApp(ourMainApp);

        // Add observable list data to the table
        //productsTable.setItems(getProductData());
    }

    public void addNewProduct()
    {
        Product newProduct = new Product();
        warehouseTabController.addNewProduct(newProduct);
        salesTabController.addNewProduct(newProduct);
    }

}
