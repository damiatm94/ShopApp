package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.damiatm94.shopapp.MainApp.getProductData;
import static com.damiatm94.shopapp.view.ProductsOverviewController.getAnchorPanes;
import static com.damiatm94.shopapp.view.ProductsOverviewController.getLabels;
import static com.damiatm94.shopapp.view.ProductsOverviewController.getTextFields;

/**
 * Created by damian on 06.08.16.
 */
public class SellDialogController
{
    private Stage dialogStage;
    private boolean okClicked = false;
    private List<AnchorPane> sellAnchorPanes = new ArrayList<>();
    private List<Label> sellLabels = new ArrayList<>();
    private List<Product> productList;
    private List<Label> headersLabels = new ArrayList<>(3);
    private static ObservableList<String> items = FXCollections.observableArrayList (
            "Example 1", "Example 2", "Dummy data 3", "Example data 4");
    private boolean buttonOkClicked = false;

    @FXML
    private VBox container;

    @FXML
    private Label sumLabel;

    public static ObservableList<String> getItems()
    {
        return items;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
    }

    public boolean isButtonOkClicked()
    {
        return buttonOkClicked;
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void createSpaceForProductsInfo(List<Product> productList)
    {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMaxWidth(Double.MAX_VALUE);
        sellAnchorPanes.add(anchorPane);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10.0, 0.0, 10.0, 10.0));
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(600.0);
        gridPane.setVgap(10.0);

        //---------------------------------ADDING COLUMNS---------------------------------------
        for (int j = 0; j < 3; j++)
        {
            ColumnConstraints column = new ColumnConstraints(100);
            column.setHgrow(Priority.SOMETIMES);
            column.setPrefWidth(100);
            column.setMinWidth(10);
            column.setMaxWidth(Region.USE_COMPUTED_SIZE);
            column.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(column);
        }

        //---------------------Adding sellLabels to gridPane and sellLabels ArrayList----------------------
        for (int k = 0; k < 3; k++)
        {
            Label headerLabel = new Label();
            headersLabels.add(headerLabel);
            gridPane.getChildren().add(headersLabels.get(k));
            gridPane.setRowIndex(headersLabels.get(k), 0);
            gridPane.setColumnIndex(headersLabels.get(k), k);
        }

        headersLabels.get(0).setText("Product Name");
        headersLabels.get(1).setText("Price");
        headersLabels.get(2).setText("Amount");

        for (int j = 0; j < productList.size(); j++)
        {
            for (int i = 0; i < 3; i++)
            {
                Label label = new Label("label");
                sellLabels.add(label);

                gridPane.getChildren().add(label);
                gridPane.setRowIndex(label, j + 1);
                gridPane.setColumnIndex(label, i);

            }
        }

        //----------------------------------Adding children-------------------------------------
        anchorPane.getChildren().add(gridPane);
        anchorPane.setTopAnchor(gridPane, 0.0);
        anchorPane.setLeftAnchor(gridPane, 0.0);
        anchorPane.setRightAnchor(gridPane, 0.0);

        container.getChildren().add(anchorPane);
    }

    public void displayProductsInfo(List<Product> productList)
    {
        this.productList = productList;
        createSpaceForProductsInfo(productList);

        int labelsIterator = 0;

        for (int i = 0; i < productList.size(); i++)
        {
            sellLabels.get(labelsIterator).setText(productList.get(i).getProductName());
            sellLabels.get(labelsIterator + 1).setText(String.valueOf(productList.get(i).getPrice()));
            sellLabels.get(labelsIterator + 2).setText(String.valueOf(productList.get(i).getQuantity()));

            labelsIterator += 3;
        }
        calculateAndShowSum();
    }

    @FXML
    private void handleButtonOk()
    {
        int labelIndex = 2;

        for (int n = 0; n < getAnchorPanes().size(); n++)
        {
            int newProductAmount = getProductData().get(n).getAmount() - getProductData().get(n).getQuantity();
            getProductData().get(n).setAmount(newProductAmount);

            getProductData().get(n).setQuantity(0);
            getTextFields().get(n).setText(String.valueOf(getProductData().get(n).getQuantity()));

            getLabels().get(labelIndex).setText(String.valueOf(getProductData().get(n).getAmount()));
            if (getProductData().get(n).getAmount() == 0)
            {
                getLabels().get(labelIndex).setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }

            labelIndex += 3;
        }

        buttonOkClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleButtonCancel()
    {
        dialogStage.close();
    }

    private void calculateAndShowSum()
    {
        double sumPrices = 0;

        for (int i = 0; i < productList.size(); i++)
        {
            sumPrices += productList.get(i).getPrice() * productList.get(i).getQuantity();
        }

        sumLabel.setText(String.valueOf(sumPrices));
    }
}
