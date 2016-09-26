package com.damiatm94.shopapp;

import java.io.IOException;
import java.util.List;

import com.damiatm94.shopapp.model.Product;
import com.damiatm94.shopapp.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApp extends Application
{
    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Products.
     */
    private static ObservableList<Product> productData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp()
    {
        // some dummy products
        productData.add(new Product("Pendrive 32 GB", 30.0, 16, 10));
        productData.add(new Product("MemoryCard 16 GB", 45.99, 10, 8));
        productData.add(new Product("Screen Protector", 49.90, 4, 4));
        productData.add(new Product("USB Cabel", 14.49, 10, 8));
        productData.add(new Product("Protective Case", 64.99, 10, 10));
        productData.add(new Product("DVD-RW", 1.19, 20, 30));
    }

    /**
     * Returns the data as an observable list of Products.
     *
     * @return
     */
    public static ObservableList<Product> getProductData()
    {
        return productData;
    }


    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ShopApp");

        //Set the application icon
        this.primaryStage.getIcons().add(new Image("file:resources/images/ShopAppIcon.png"));

        initRootLayout();

        showProductsOverview();
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout()
    {
        try
        {
            // Load root layout from fxml file.
            FXMLLoader sceneLoader = new FXMLLoader();
            sceneLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = sceneLoader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            //give the controller access to the main app
            RootLayoutController controller = sceneLoader.getController();
            controller.setMainApp(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Shows the products overview inside the root layout.
     */
    public void showProductsOverview()
    {
        try
        {
            // Load product overview.
            FXMLLoader productsLoader = new FXMLLoader();
            productsLoader.setLocation(MainApp.class.getResource("view/ProductsOverview.fxml"));
            AnchorPane productOverviewPane = productsLoader.load();

            // Set products overview into the center of root layout.
            rootLayout.setCenter(productOverviewPane);

            // Give the controller access to the main app.
            ProductsOverviewController controller = productsLoader.getController();
            controller.setMainApp(this);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param product the product object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showProductEditDialog(Product product, boolean isNewProduct)
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductEditDialog.fxml"));
            VBox page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
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
            ProductEditDialogController controller = loader.getController();
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

    public boolean showSellDialog(List<Product> productList)
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SellDialog.fxml"));
            BorderPane sellDialogPane = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(sellDialogPane);
            dialogStage.setScene(scene);
            dialogStage.setTitle("Summary");
            //dialogStage.setResizable(false);
            //primaryStage.setResizable(true);

            // Set the person into the controller.
            SellDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.displayProductsInfo(productList);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isButtonOkClicked();


        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog to show sale history.
     */
    public void showSalesHistory()
    {
        try
        {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SalesHistory.fxml"));
            VBox historyScene = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sales History");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(historyScene);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            SalesHistoryController controller = loader.getController();
            controller.setItemsToList();

            dialogStage.show();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}