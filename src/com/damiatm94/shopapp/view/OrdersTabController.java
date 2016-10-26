package com.damiatm94.shopapp.view;


import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by damian on 29.09.16.
 */
public class OrdersTabController
{
    private MainApp mainApp;
    private ProductsOverviewController mainController;

    public OrdersTabController()
    {
    }

    @FXML
    private void initialize()
    {
    }

    @FXML
    public void handleButtonNewOrder()
    {
        Product product = new Product();
        boolean isNewProduct = true;
        showOrderDialog(product, isNewProduct);
    }

    public boolean showOrderDialog(Product product, boolean isNewProduct)
    {
        try
        {
            mainApp = new MainApp();
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderDialog.fxml"));
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
                dialogStage.setTitle("Make new order");
            } else
            {
                dialogStage.setTitle("Edit Product");
            }

            // Set the person into the controller.
            OrderDialogController controller = loader.getController();
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
