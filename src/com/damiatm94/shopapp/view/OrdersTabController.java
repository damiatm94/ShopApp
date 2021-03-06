package com.damiatm94.shopapp.view;


import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Order;
import com.damiatm94.shopapp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.damiatm94.shopapp.MainApp.getProductData;

/**
 * Created by damian on 29.09.16.
 */
public class OrdersTabController
{
    private MainApp mainApp = new MainApp();
    private ProductsOverviewController mainController;
    private String nameOfOrder;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/ HH:mm:ss");

    private ObservableList<Order> listOfOrdersHistory = FXCollections.observableArrayList();
    ArrayList<Order> ordersList = new ArrayList<>();
    private ColumnConstraints[] columnsList = new ColumnConstraints[4];
    private static List<Label> ordersLabelsList = new ArrayList<>();
    private static List<AnchorPane> rowAnchorList = new ArrayList<>();
    private List<Button> showButtons = new ArrayList<>();
    private List<Button> confirmButtons = new ArrayList<>();
    private List<Button> undoButtons = new ArrayList<>();
    Image confirmImage, showImage, undoImage;
    private GridPane gridPane;

    @FXML
    private VBox madeOrdersVBox;
    @FXML
    private ListView<Order> listView;

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

    public DateFormat getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public OrdersTabController()
    {
    }

    @FXML
    private void initialize()
    {
        confirmImage = new Image("file:resources/images/show_ico.png");
        showImage = new Image("file:resources/images/confirm_ico.png");
        undoImage = new Image("file:resources/images/undo_ico.png");
        listView.setItems(listOfOrdersHistory);

    }

    @FXML
    public void handleButtonNewOrder()
    {
        showOrderDialog();
    }

    public void handleButtonShowInfo(Order order)
    {
        showMadeOrderInfo(order);
    }

    public void handleButtonConfirm(Order order)
    {
        Date dateOfDeliveryConfirmation = new Date();
        order.setDateOfDeliveryConfirmation(dateFormat.format(dateOfDeliveryConfirmation));
        boolean isOrderedProductAlreadyExisting = false;

        for (int i = 0; i < order.getListOfProducts().size(); i++)
        {
            //Searching if ordered product already exists
            for (int j = 0; j < getProductData().size(); j++)
            {
                System.out.println("zamiawany: " + order.getListOfProducts().get(i).getProductName() +
                        " | istniejący: " + getProductData().get(j).getProductName());
                if (order.getListOfProducts().get(i).getProductName().equals(getProductData().get(j).getProductName())
                        && order.getListOfProducts().get(i).getPrice() == getProductData().get(j).getPrice())
                {
                    System.out.println("Znalazłem produkt identyczny z zamawianym!");
                    int newAmount = getProductData().get(j).getAmount() + order.getListOfProducts().get(i).getAmount();
                    getProductData().get(j).setAmount(newAmount);
                    mainController.getSalesTabController().setEditProductDetails(getProductData().get(j), j);
                    isOrderedProductAlreadyExisting = true;
                }
            }
            if (!isOrderedProductAlreadyExisting)
            {
                getProductData().add(order.getListOfProducts().get(i));
                mainController.getSalesTabController().addNewProduct(order.getListOfProducts().get(i));
            }

            isOrderedProductAlreadyExisting = false;
        }

        listOfOrdersHistory.add(order);
    }

    public void handleButtonUndo(
            AnchorPane anchorPane, Label label, Button button1, Button button2, Button button3, Order order)
    {
        ordersLabelsList.remove(label);
        showButtons.remove(button1);
        confirmButtons.remove(button2);
        undoButtons.remove(button3);
        madeOrdersVBox.getChildren().remove(anchorPane);
        rowAnchorList.remove(anchorPane);
        getOrdersList().remove(order);
    }

    public void handleButtonShowDetails()
    {
        Order order = listView.getSelectionModel().getSelectedItem();
        showOrdersHistoryItem(order);
    }

    public void enrollMadeOrder(Order order)
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
        Button buttonShowPreview = new Button("Show");
        confirmButtons.add(buttonShowPreview);
        buttonShowPreview.setMaxWidth(80);
        buttonShowPreview.setMaxHeight(20);
        ;
        buttonShowPreview.setGraphic(new ImageView(confirmImage));
        buttonShowPreview.setOnAction(event -> handleButtonShowInfo(order));
        gridPane.setConstraints(buttonShowPreview, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);

        Button buttonConfirmDelivery = new Button("Confirm");
        confirmButtons.add(buttonConfirmDelivery);
        buttonConfirmDelivery.setMaxWidth(90);
        buttonConfirmDelivery.setMaxHeight(20);
        buttonConfirmDelivery.setGraphic(new ImageView(showImage));
        buttonConfirmDelivery.setOnAction(event ->
        {
            handleButtonConfirm(order);
            handleButtonUndo(
                    rowAnchorPane, orderName, buttonShowPreview, buttonConfirmDelivery, buttonConfirmDelivery, order);
        });
        gridPane.setConstraints(buttonConfirmDelivery, 2, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

        Button buttonUndoOrder = new Button("Undo");
        undoButtons.add(buttonUndoOrder);
        buttonUndoOrder.setMaxWidth(80);
        buttonUndoOrder.setMaxHeight(20);
        buttonUndoOrder.setGraphic(new ImageView(undoImage));
        buttonUndoOrder.setOnAction(event -> handleButtonUndo(
                rowAnchorPane, orderName, buttonShowPreview, buttonConfirmDelivery, buttonConfirmDelivery, order));
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

    public boolean showOrderDialog()
    {
        try
        {
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

    public boolean showMadeOrderInfo(Order order)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderOrSaleInfo.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //dialogStage.setResizable(false);
            //primaryStage.setResizable(true);

            dialogStage.setTitle("Details");

            OrderOrSaleInfoController controller = loader.getController();
            controller.initOrdersTabMainController(this);
            controller.getTitleLabel().setText("Info about: " + order.getName());
            controller.setDialogStage(dialogStage);
            controller.displayListOfProducts(order.getListOfProducts());

            dialogStage.showAndWait();
            return controller.isButtonOkClicked();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showOrdersHistoryItem(Order order)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderOrSaleInfo.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //dialogStage.setResizable(false);
            //primaryStage.setResizable(true);

            dialogStage.setTitle("Show info abot delivered order");

            OrderOrSaleInfoController controller = loader.getController();
            controller.initOrdersTabMainController(this);
            controller.getTitleLabel().setText("Info about: " + order.getName());
            controller.getDateLabel().setText("Delivery date: \n" + order.getDateOfDeliveryConfirmation());
            controller.setDialogStage(dialogStage);
            controller.displayListOfProducts(order.getListOfProducts());

            dialogStage.showAndWait();
            return controller.isButtonOkClicked();
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
