package com.damiatm94.shopapp.view;


import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private ColumnConstraints[] columnsList = new ColumnConstraints[4];
    private static List<Label> ordersLabelsList = new ArrayList<>();
    private static List<AnchorPane> rowAnchorList = new ArrayList<>();
    private List<Button> showButtons = new ArrayList<>();
    private List<Button> confirmButtons = new ArrayList<>();
    private List<Button> undoButtons = new ArrayList<>();
    private GridPane gridPane;

    @FXML
    private VBox vBox;

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
        showOrderDialog();
    }

    @FXML
    public void handleButtonDelete()
    {
        gridPane.getChildren().remove(1);
    }

    public void enrollMadeOrder()
    {
        AnchorPane rowAnchorPane = new AnchorPane();
        rowAnchorPane.setMaxWidth(Double.MAX_VALUE);
        rowAnchorList.add(rowAnchorPane);

        //---------------------------Adding labels to gridPane----------------------------------
        Label ordersDateLabel = new Label();
        ordersDateLabel.setText("Test ");
        ordersLabelsList.add(ordersDateLabel);

        gridPane = createGridPane();
        gridPane.setRowIndex(ordersDateLabel, 0);
        gridPane.setColumnIndex(ordersDateLabel, 0);

        //----------------------------------Adding buttons--------------------------------------
        Image showImage = new Image("file:resources/images/confirm_ico.png");
        Button buttonShowPreview = new Button("Confirm");
        showButtons.add(buttonShowPreview);
        buttonShowPreview.setMaxWidth(90);
        buttonShowPreview.setMaxHeight(20);
        buttonShowPreview.setGraphic(new ImageView(showImage));
        gridPane.setConstraints(buttonShowPreview, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);

        Image confirmImage = new Image("file:resources/images/show_ico.png");
        Button buttonConfirmDelivery = new Button("Show");
        confirmButtons.add(buttonConfirmDelivery);
        buttonConfirmDelivery.setMaxWidth(80);
        buttonConfirmDelivery.setMaxHeight(20);;
        buttonConfirmDelivery.setGraphic(new ImageView(confirmImage));
        gridPane.setConstraints(buttonConfirmDelivery, 2, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

        Image undoImage = new Image("file:resources/images/undo_ico.png");
        Button buttonUndoOrder = new Button("Undo");
        undoButtons.add(buttonUndoOrder);
        buttonUndoOrder.setMaxWidth(80);
        buttonUndoOrder.setMaxHeight(20);
        buttonUndoOrder.setGraphic(new ImageView(undoImage));
        gridPane.setConstraints(buttonUndoOrder, 3, 0, 1, 1, HPos.LEFT, VPos.CENTER);

        //Image confirmImage = new Image(getClass().getResourceAsStream("ShopAppIcon.png"));



        //----------------------------------Adding children-------------------------------------
        gridPane.getChildren().addAll(ordersDateLabel, buttonShowPreview, buttonConfirmDelivery, buttonUndoOrder);

        rowAnchorPane.getChildren().add(gridPane);
        rowAnchorPane.setTopAnchor(gridPane, 0.0);
        rowAnchorPane.setLeftAnchor(gridPane, 0.0);
        rowAnchorPane.setRightAnchor(gridPane, 0.0);

        vBox.getChildren().add(rowAnchorPane);
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderDialog.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //dialogStage.setResizable(false);
            //primaryStage.setResizable(true);

            dialogStage.setTitle("Make new order");

            OrderDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.injectMainController(this);

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
