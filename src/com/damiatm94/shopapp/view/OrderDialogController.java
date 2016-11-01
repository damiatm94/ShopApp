package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.model.Order;
import com.damiatm94.shopapp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by damian on 26.10.16.
 */
public class OrderDialogController
{
    @FXML
    private TableView<Product> ordersTable;
    @FXML
    private TableColumn<Product, String> orderNameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> amountColumn;

    @FXML
    private TextField orderNameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField amountField;

    ObservableList<Product> listOfProducts = FXCollections.observableArrayList();

    private Order order;
    private OrdersTabController ordersMainController;
    private Stage dialogStage;
    private Product product;
    private boolean okClicked = false;

    @FXML
    private void initialize()
    {
        orderNameField.setPromptText("Product Name");
        priceField.setPromptText("Price");
        amountField.setPromptText("Amount");

        orderNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        ordersTable.setItems(listOfProducts);
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    @FXML
    private void handleButtonAdd()
    {
        if (isInputValid())
        {
            System.out.println("Before ADD: " + listOfProducts.size());
            product = new Product();
            product.setProductName(orderNameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setAmount(Integer.parseInt(amountField.getText()));
            product.setMinAmount(0);
            listOfProducts.add(product);

            orderNameField.clear();
            priceField.clear();
            amountField.clear();

            okClicked = true;
        }
    }

    @FXML
    private void handleButtonDelete()
    {

    }

    @FXML
    private void handleOrder()
    {
        Date date = new Date();
        String nameOfOrder = "Order/" + ordersMainController.getDateFormat().format(date);

        if (listOfProducts.size() != 0)
        {
            Order order = new Order(nameOfOrder, listOfProducts);
            ordersMainController.setNameOfOrder(order.getName());
            ordersMainController.getOrdersList().add(order);

            ordersMainController.enrollMadeOrder(
                    ordersMainController.getOrdersList().get(ordersMainController.getOrdersList().size() - 1));
        }
        dialogStage.close();
        System.out.println("After ORDER: " + listOfProducts.size());
    }

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    private boolean isInputValid()
    {
        String errorMessage = "";

        if (orderNameField.getText() == null || orderNameField.getText().length() == 0)
        {
            errorMessage += "No valid product name!\n";
        }

        if (priceField.getText() == null || priceField.getText().length() == 0)
        {
            errorMessage += "No valid price value!\n";
        } else
        {
            // try to parse price into a double.
            try
            {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e)
            {
                errorMessage += "No valid price value (must be an double)!\n";
            }
        }

        if (amountField.getText() == null || amountField.getText().length() == 0)
        {
            errorMessage += "No valid amount value!\n";
        } else
        {
            // try to parse amount into an int.
            try
            {
                Integer.parseInt(amountField.getText());
            } catch (NumberFormatException e)
            {
                errorMessage += "No valid amount value (must be an integer)!\n";
            }
        }


        if (errorMessage.length() == 0)
        {
            return true;
        } else
        {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public void injectMainController(OrdersTabController ordersTabController)
    {
        ordersMainController = ordersTabController;
    }
}
