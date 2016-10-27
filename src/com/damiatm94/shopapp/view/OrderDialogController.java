package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.model.Product;
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


    @FXML
    private TextField orderNameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField minAmountField;

    private Stage dialogStage;
    private Product product;
    private boolean isNewProduct;
    private boolean okClicked = false;

    @FXML
    private void initialize()
    {
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setProduct(Product product, boolean isNewProduct)
    {
        this.product = product;
        this.isNewProduct = isNewProduct;

        if (!isNewProduct)
        {
            orderNameField.setText(product.getProductName());
            priceField.setText(Double.toString(product.getPrice()));
            amountField.setText(Integer.toString(product.getAmount()));
            minAmountField.setText(Integer.toString(product.getMinAmount()));
        } else
        {
            orderNameField.setText("");
            priceField.setText("");
            amountField.setText("");
            minAmountField.setText("");
        }

    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    @FXML
    private void handleOrder()
    {
        if (isInputValid())
        {
            product.setProductName(orderNameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setAmount(Integer.parseInt(amountField.getText()));
            product.setMinAmount(Integer.parseInt(minAmountField.getText()));

            okClicked = true;
            dialogStage.close();
        }
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
