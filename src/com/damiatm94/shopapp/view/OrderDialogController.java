package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by damian on 26.10.16.
 */
public class OrderDialogController
{
    @FXML private TableView<Product> ordersTable;
    @FXML private TableColumn<Product, String> orderNameColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, Integer> amountColumn;
    @FXML private TableColumn<Product, Integer> minAmountColumn;

    @FXML private TextField orderNameField;
    @FXML private TextField priceField;
    @FXML private TextField amountField;
    @FXML private TextField minAmountField;

    ObservableList<Product> ordersList = FXCollections.observableArrayList();

    private Stage dialogStage;
    private Product product;
    private boolean okClicked = false;

    @FXML
    private void initialize()
    {
        orderNameField.setPromptText("Product Name");
        priceField.setPromptText("Price");
        amountField.setPromptText("Amount");
        minAmountField.setPromptText("Minimal amount");

        orderNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        minAmountColumn.setCellValueFactory(cellData -> cellData.getValue().minAmountProperty().asObject());
        ordersTable.setItems(ordersList);
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
            product = new Product();
            product.setProductName(orderNameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setAmount(Integer.parseInt(amountField.getText()));
            product.setMinAmount(Integer.parseInt(minAmountField.getText()));

            ordersList.add(product);

            orderNameField.clear();
            priceField.clear();
            amountField.clear();
            minAmountField.clear();

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
        dialogStage.close();
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
                Double.parseDouble(minAmountField.getText());
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
                Integer.parseInt(minAmountField.getText());
            } catch (NumberFormatException e)
            {
                errorMessage += "No valid amount value (must be an integer)!\n";
            }
        }

        if (minAmountField.getText() == null || minAmountField.getText().length() == 0)
        {
            errorMessage += "No valid minimal amount value!\n";
        } else
        {
            // try to parse minimal amount into an int.
            try
            {
                Integer.parseInt(minAmountField.getText());
            } catch (NumberFormatException e)
            {
                errorMessage += "No valid minimal amount value (must be an integer)!\n";
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
}
