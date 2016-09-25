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

/**
 * Created by damian on 15.08.16.
 */
public class SalesDetailsController
{
    private Stage dialogStage;
    private boolean okClicked = false;
    private List<AnchorPane> sellAnchorPanes = new ArrayList<>();
    private List<Label> sellLabels = new ArrayList<>();
    private List<Product> productList;
    private List<Label> headersLabels = new ArrayList<>(3);
    private static ObservableList<Product> itemsHistory = FXCollections.observableArrayList();
    private boolean buttonOkClicked = false;

    @FXML
    private VBox container;

    @FXML
    private Label sumLabel;

    public static ObservableList<Product> getItemsHistory()
    {
        return itemsHistory;
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

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void createSpaceForDetails(List<Product> productList)
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

    public void displaySalesDetails(List<Product> productList)
    {
        this.productList = productList;
        createSpaceForDetails(productList);

        int labelsIterator = 0;

        for (int i = 0; i < productList.size(); i++)
        {
            sellLabels.get(labelsIterator).setText(productList.get(i).getProductName());
            sellLabels.get(labelsIterator + 1).setText(String.valueOf(productList.get(i).getPrice()));
            sellLabels.get(labelsIterator + 2).setText(String.valueOf(productList.get(i).getQuantity()));

            labelsIterator += 3;
        }

        calculateAndShowSum();
        addProductsToHistory();

    }

    private void calculateAndShowSum()
    {
        double sumPrices = 0;

        for (Product aProductList : productList)
        {
            sumPrices += aProductList.getPrice() * aProductList.getQuantity();
        }

        sumLabel.setText(String.valueOf(sumPrices));
    }

    public void addProductsToHistory()
    {
        productList.forEach(product -> itemsHistory.add(product));
/*        for (Product aProductList : productList)
        {
            itemsHistory.add(aProductList);
        }*/

    }

    @FXML
    private void handleButtonOk()
    {
        buttonOkClicked = true;
        dialogStage.close();

    }
}
