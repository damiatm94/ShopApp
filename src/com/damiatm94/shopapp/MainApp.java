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

public class MainApp extends Application
{
    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Products.
     */
    private static ObservableList<Product> productData = FXCollections.observableArrayList();

    public MainApp()
    {
    }

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
            //controller.setMainApp(this);
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

    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}