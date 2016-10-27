package com.damiatm94.shopapp.view;


import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 29.09.16.
 */
public class OrdersTabController
{
    private MainApp mainApp;
    private ProductsOverviewController mainController;

    private ArrayList<RowConstraints> rowList = new ArrayList<>();
    private ColumnConstraints[] columnsList = new ColumnConstraints[4];
    private static List<Label> ordersLabelsList = new ArrayList<>();

    private GridPane gridPane;
    int rowIndex = 0;

    @FXML
    private ScrollPane scrollPane;

    public OrdersTabController()
    {
    }

    @FXML
    private void initialize()
    {
        gridPane = createGridPane();
        scrollPane.setContent(gridPane);
    }

    @FXML
    public void handleButtonNewOrder()
    {
        showOrderDialog();
    }

    @FXML
    public void handleButtonDelete()
    {
        gridPane.getChildren().remove(1);
    }

    public void enrollMadeOrder()
    {
        RowConstraints row = new RowConstraints();
        rowList.add(row);
        gridPane.getRowConstraints().add(rowList.get(rowIndex));
        //---------------------======Adding labels to gridPane----------------------------------
        Label ordersDateLabel = new Label();
        ordersDateLabel.setText("Test " + rowIndex);
        ordersLabelsList.add(ordersDateLabel);

        gridPane.getChildren().add(ordersDateLabel);
        gridPane.setRowIndex(ordersDateLabel, rowIndex);
        gridPane.setColumnIndex(ordersDateLabel, 0);
        rowIndex++;
    }

    public GridPane createGridPane()
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        gridPane.setVgap(10.0);
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

        //---------------------------------ADDING COLUMNS---------------------------------------
        for (int i = 0; i < columnsList.length; i++)
        {
            columnsList[i] = new ColumnConstraints();
            columnsList[i].setHgrow(Priority.SOMETIMES);
            columnsList[i].setPrefWidth(100);
            columnsList[i].setMinWidth(10);
            columnsList[i].setMaxWidth(Region.USE_COMPUTED_SIZE);
            gridPane.getColumnConstraints().add(columnsList[i]);
        }

        return gridPane;
    }

    public boolean showOrderDialog()
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

            dialogStage.setTitle("Make new order");

            // Set the person into the controller.
            OrderDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.injectMainController(this);

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
