package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.damiatm94.shopapp.MainApp.getProductData;

/**
 * Created by damian on 05.11.16.
 */
public class OrderOrSaleInfoController

{
    private SalesTabController mainSalesTabController;
    private OrdersTabController mainOrderTabController;
    private boolean isOrderInfo = false;

    private Stage dialogStage;
    //private boolean okClicked = false;
    private List<AnchorPane> rowAnchorPanes = new ArrayList<>();
    private List<Label> sellLabels = new ArrayList<>();
    private List<Product> productList;
    private static ObservableList<String> items = FXCollections.observableArrayList(
            "Example 1", "Example 2", "Dummy data 3", "Example data 4");
    private boolean buttonOkClicked = false;

    @FXML private VBox container;
    @FXML private Button cancelButton;
    @FXML private Label sumLabel;
    @FXML private Label titleLabel;
    @FXML private Label dateLabel;
    @FXML private ButtonBar buttonBar;

    public Label getDateLabel()
    {
        return dateLabel;
    }

    public Label getTitleLabel()
    {
        return titleLabel;
    }

    public static ObservableList<String> getItems()
    {
        return items;
    }

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
        rowAnchorPanes.add(anchorPane);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0.0, 50.0, 10.0, 50.0));
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
            if (j == 0)
            {
                column.setPrefWidth(180);
                column.setHalignment(HPos.LEFT);
            } else
            {
                column.setHalignment(HPos.RIGHT);
            }
            gridPane.getColumnConstraints().add(column);
        }

        //---------------------Adding sellLabels to gridPane and sellLabels ArrayList----------------------
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
        if (isOrderInfo)
        {
            buttonBar.getButtons().remove(cancelButton);
        }
    }

    public void displayListOfProducts(List<Product> productList)
    {
        this.productList = productList;
        createSpaceForProductsInfo(productList);

        int labelsIterator = 0;

        for (int i = 0; i < productList.size(); i++)
        {
            sellLabels.get(labelsIterator).setText(productList.get(i).getProductName());
            sellLabels.get(labelsIterator + 1).setText(String.valueOf(productList.get(i).getPrice()));
            if (!isOrderInfo)
            {
                sellLabels.get(labelsIterator + 2).setText(String.valueOf(productList.get(i).getQuantity()));
            }
            else
            {
                sellLabels.get(labelsIterator + 2).setText(String.valueOf(productList.get(i).getAmount()));
            }
            labelsIterator += 3;
        }
        calculateAndShowSum();
    }

    @FXML
    private void handleButtonOk()
    {
        if (!isOrderInfo)
        {
            int labelIndex = 2;

            for (int n = 0; n < mainSalesTabController.getAnchorPanes().size(); n++)
            {
                int newProductAmount = getProductData().get(n).getAmount() - getProductData().get(n).getQuantity();
                getProductData().get(n).setAmount(newProductAmount);

                getProductData().get(n).setQuantity(0);
                mainSalesTabController.getTextFields().get(n).setText(String.valueOf(getProductData().get(n).getQuantity()));

                mainSalesTabController.getLabels().get(labelIndex).setText(String.valueOf(getProductData().get(n).getAmount()));
                if (getProductData().get(n).getAmount() == 0)
                {
                    mainSalesTabController.getLabels().get(labelIndex).setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }

                labelIndex += 3;
            }
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
            if (isOrderInfo)
            {
                sumPrices += productList.get(i).getPrice() * productList.get(i).getAmount();
            } else
            {
                sumPrices += productList.get(i).getPrice() * productList.get(i).getQuantity();
            }
        }
        double roundSumPrices = Math.round(sumPrices * 100);
        sumPrices = roundSumPrices / 100;
        sumLabel.setText("Sum = " + String.valueOf(sumPrices));
    }

    public void initSalesTabMainController(SalesTabController salesTabController)
    {
        mainSalesTabController = salesTabController;
    }

    public void initOrdersTabMainController(OrdersTabController ordersTabController)
    {
        mainOrderTabController = ordersTabController;
        isOrderInfo = true;
    }
}
