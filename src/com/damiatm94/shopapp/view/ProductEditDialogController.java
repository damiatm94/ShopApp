package com.damiatm94.shopapp.view;

/**
 * Created by damian on 30.07.16.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.damiatm94.shopapp.model.Product;

/**
 * Dialog to edit details of a person.
 */
public class ProductEditDialogController
{

    @FXML
    private TextField productNameField;
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

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets product to be edited in the dialog.
     */
    public void setProduct(Product product, boolean isNewProduct)
    {
        this.product = product;
        this.isNewProduct = isNewProduct;

        if (!isNewProduct)
        {
            productNameField.setText(product.getProductName());
            priceField.setText(Double.toString(product.getPrice()));
            amountField.setText(Integer.toString(product.getAmount()));
            minAmountField.setText(Integer.toString(product.getMinAmount()));
        } else
        {
            productNameField.setText("");
            priceField.setText("");
            amountField.setText("");
            minAmountField.setText("");
        }

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked()
    {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk()
    {
        if (isInputValid())
        {
            product.setProductName(productNameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setAmount(Integer.parseInt(amountField.getText()));
            product.setMinAmount(Integer.parseInt(minAmountField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid()
    {
        String errorMessage = "";

        if (productNameField.getText() == null || productNameField.getText().length() == 0)
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
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
