package com.damiatm94.shopapp.view;


import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * Created by damian on 29.09.16.
 */
public class OrdersTabController
{
    private MainApp mainApp;
    private ProductsOverviewController mainController;
    private boolean isNewOrder = true;
    private String nameOfOrder;

    ArrayList<Order> ordersList = new ArrayList<>();
    private ColumnConstraints[] columnsList = new ColumnConstraints[4];
    private static List<Label> ordersLabelsList = new ArrayList<>();
    private static List<AnchorPane> rowAnchorList = new ArrayList<>();
    private List<Button> showButtons = new ArrayList<>();
    private List<Button> confirmButtons = new ArrayList<>();
    private List<Button> undoButtons = new ArrayList<>();
    Image confirmImage, showImage, undoImage;
    private GridPane gridPane;

    public ArrayList<Order> getOrdersList()
    {
        return ordersList;
    }

    public void setOrdersList(ArrayList<Order> ordersList)
    {
        this.ordersList = ordersList;
    }

    public void setNameOfOrder(String nameOfOrder)
    {
        this.nameOfOrder = nameOfOrder;
    }

    @FXML
    private VBox madeOrdersVBox;

    @FXML
    private ListView<Label> listView;

    public OrdersTabController()
    {
    }

    @FXML
    private void initialize()
    {
        confirmImage = new Image("file:resources/images/show_ico.png");
        showImage = new Image("file:resources/images/confirm_ico.png");
        undoImage = new Image("file:resources/images/undo_ico.png");
    }

    @FXML
    public void handleButtonNewOrder()
    {
        showOrderDialog(isNewOrder);
    }

    public void handleButtonShowInfo()
    {
        isNewOrder = false;
        showOrderDialog(isNewOrder);
    }

    public void handleButtonConfirm()
    {

    }

    public void handleButtonUndo(AnchorPane anchorPane, Label label, Button button1, Button button2, Button button3)
    {
        ordersLabelsList.remove(label);
        showButtons.remove(button1);
        confirmButtons.remove(button2);
        undoButtons.remove(button3);
        madeOrdersVBox.getChildren().remove(anchorPane);
        rowAnchorList.remove(anchorPane);
    }

    public void enrollMadeOrder()
    {
        AnchorPane rowAnchorPane = new AnchorPane();
        rowAnchorPane.setMaxWidth(Double.MAX_VALUE);
        rowAnchorList.add(rowAnchorPane);

        //---------------------------Adding labels to gridPane----------------------------------
        Label orderName = new Label();
        orderName.setText(nameOfOrder);
        ordersLabelsList.add(orderName);

        gridPane = createGridPane();
        gridPane.setRowIndex(orderName, 0);
        gridPane.setColumnIndex(orderName, 0);

        //----------------------------------Adding buttons--------------------------------------
        Button buttonConfirmDelivery = new Button("Show");
        confirmButtons.add(buttonConfirmDelivery);
        buttonConfirmDelivery.setMaxWidth(80);
        buttonConfirmDelivery.setMaxHeight(20);
        ;
        buttonConfirmDelivery.setGraphic(new ImageView(confirmImage));
        buttonConfirmDelivery.setOnAction(event -> handleButtonShowInfo());
        gridPane.setConstraints(buttonConfirmDelivery, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);

        Button buttonShowPreview = new Button("Confirm");
        showButtons.add(buttonShowPreview);
        buttonShowPreview.setMaxWidth(90);
        buttonShowPreview.setMaxHeight(20);
        buttonShowPreview.setGraphic(new ImageView(showImage));
        buttonShowPreview.setOnAction(event -> handleButtonConfirm());
        gridPane.setConstraints(buttonShowPreview, 2, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

        Button buttonUndoOrder = new Button("Undo");
        undoButtons.add(buttonUndoOrder);
        buttonUndoOrder.setMaxWidth(80);
        buttonUndoOrder.setMaxHeight(20);
        buttonUndoOrder.setGraphic(new ImageView(undoImage));
        buttonUndoOrder.setOnAction(event -> handleButtonUndo(
                rowAnchorPane, orderName, buttonShowPreview, buttonConfirmDelivery, buttonConfirmDelivery));
        gridPane.setConstraints(buttonUndoOrder, 3, 0, 1, 1, HPos.LEFT, VPos.CENTER);

        //----------------------------------Adding children-------------------------------------
        gridPane.getChildren().addAll(orderName, buttonShowPreview, buttonConfirmDelivery, buttonUndoOrder);

        rowAnchorPane.getChildren().add(gridPane);
        rowAnchorPane.setTopAnchor(gridPane, 0.0);
        rowAnchorPane.setLeftAnchor(gridPane, 0.0);
        rowAnchorPane.setRightAnchor(gridPane, 0.0);

        madeOrdersVBox.getChildren().add(rowAnchorPane);
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
            columnsList[i].setPrefWidth(100);
            columnsList[i].setMinWidth(10);
            columnsList[i].setMaxWidth(Region.USE_COMPUTED_SIZE);
            gridPane.getColumnConstraints().add(columnsList[i]);
        }
        columnsList[0].setHgrow(Priority.SOMETIMES);

        return gridPane;
    }

    public boolean showOrderDialog(boolean isNewOrder)
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

            if (isNewOrder)
            {
                dialogStage.setTitle("Make new order");
            } else
            {
                dialogStage.setTitle("Info about: " + "NAZWA");
            }
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
