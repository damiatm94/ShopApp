package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.MainApp;
import com.damiatm94.shopapp.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.damiatm94.shopapp.MainApp.getProductData;

/**
 * Created by damian on 29.09.16.
 */
public class SalesTabController implements ProductsListener
{
    private ProductsOverviewController mainController;

    @FXML
    private VBox container;

    private MainApp mainApp;

    private static List<AnchorPane> anchorPanes = new ArrayList<>();
    private static List<Label> labels = new ArrayList<>();
    private static List<TextField> textFields = new ArrayList<>();
    private List<Button> minusButtons = new ArrayList<>();
    private List<Button> plusButtons = new ArrayList<>();
    Image plusImage, minusImage;

    private static int selectedIndexForSales;
    private static Product selectedSalesProduct;

    public static List<AnchorPane> getAnchorPanes()
    {
        return anchorPanes;
    }

    public static List<Label> getLabels()
    {
        return labels;
    }

    public static List<TextField> getTextFields()
    {
        return textFields;
    }

    public static void setSelectedIndexForSales(int selectedIndexForSales)
    {
        SalesTabController.selectedIndexForSales = selectedIndexForSales;
    }

    public static void setSelectedProduct(Product selectedProduct)
    {
        SalesTabController.selectedSalesProduct = selectedProduct;
    }

    public SalesTabController()
    {
    }

    @FXML
    private void initialize()
    {
        plusImage = new Image("file:resources/images/plus_ico.png");
        minusImage = new Image("file:resources/images/minus_ico.png");

        // Load some dummy products' data if they are existing
        loadDummiesToSalesPanel();
    }

    /**
     * Make AnchorPanes, GridPanes and labels for our data in tab "Sales Panel"
     */
    public void addProductToSalesPanel(Product product)
    {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMaxWidth(Double.MAX_VALUE);
        anchorPanes.add(anchorPane);
        //System.out.println(anchorPanes.size());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(1000.0);

        //---------------------------------ADDING COLUMNS---------------------------------------
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setHgrow(Priority.SOMETIMES);
        column0.setPrefWidth(100);
        column0.setMinWidth(Region.USE_PREF_SIZE);
        column0.setMaxWidth(Region.USE_PREF_SIZE);
        gridPane.getColumnConstraints().add(column0);

        for (int j = 1; j < 4; j++)
        {
            ColumnConstraints column = new ColumnConstraints(100);
            column.setHgrow(Priority.SOMETIMES);
            column.setPrefWidth(100);
            column.setMinWidth(10);
            column.setMaxWidth(Region.USE_COMPUTED_SIZE);
            gridPane.getColumnConstraints().add(column);
        }

        ColumnConstraints freeSpaceColumn = new ColumnConstraints(140);
        freeSpaceColumn.setHgrow(Priority.SOMETIMES);
        freeSpaceColumn.setPrefWidth(140);
        freeSpaceColumn.setMinWidth(10);
        freeSpaceColumn.setMaxWidth(Region.USE_PREF_SIZE);
        gridPane.getColumnConstraints().add(freeSpaceColumn);

        ColumnConstraints buttonsColumn = new ColumnConstraints(150);
        buttonsColumn.setHgrow(Priority.SOMETIMES);
        buttonsColumn.setPrefWidth(150);
        buttonsColumn.setMinWidth(Region.USE_PREF_SIZE);
        buttonsColumn.setMaxWidth(Region.USE_PREF_SIZE);
        gridPane.getColumnConstraints().add(buttonsColumn);

        ColumnConstraints lastColumn = new ColumnConstraints(100);
        lastColumn.setHgrow(Priority.SOMETIMES);
        lastColumn.setPrefWidth(100);
        lastColumn.setMinWidth(Region.USE_PREF_SIZE);
        lastColumn.setMaxWidth(Region.USE_PREF_SIZE);
        gridPane.getColumnConstraints().add(lastColumn);


        //---------------------Adding labels to gridPane and labels ArrayList----------------------
        for (int i = 0; i < 3; i++)
        {
            Label label = new Label();
            labels.add(label);

            gridPane.getChildren().add(label);
            gridPane.setRowIndex(label, 0);
            gridPane.setColumnIndex(label, i + 1);
        }


        //--------------------Adding buttonsMinus and text field to buttonsColumn------------------
        TextField quantityTextField = new TextField();
        textFields.add(quantityTextField);
        quantityTextField.setText(String.valueOf(0));
        quantityTextField.setPrefWidth(50);
        quantityTextField.setMaxWidth(quantityTextField.getPrefWidth());
        quantityTextField.setAlignment(Pos.CENTER);
        gridPane.setConstraints(quantityTextField, 5, 0, 1, 1, HPos.CENTER, VPos.CENTER);

        Button buttonMinus = new Button();
        buttonMinus.setGraphic(new ImageView(minusImage));
        minusButtons.add(buttonMinus);
        buttonMinus.setMaxWidth(20);
        buttonMinus.setMaxHeight(20);
        buttonMinus.setOnAction(event ->
        {
            int k = product.getQuantity() - 1;
            if (k >= 0)
            {
                product.setQuantity(k);
                quantityTextField.setText(String.valueOf(product.getQuantity()));
            }
        });
        gridPane.setConstraints(buttonMinus, 5, 0, 1, 1, HPos.LEFT, VPos.CENTER);


        Button buttonPlus = new Button();
        plusButtons.add(buttonPlus);
        buttonPlus.setMaxWidth(20);
        buttonPlus.setMaxHeight(20);
        buttonPlus.setGraphic(new ImageView(plusImage));
        buttonPlus.setOnAction(event ->
        {
            if (product.getQuantity() + 1 <= product.getAmount())
            {
                int k = product.getQuantity() + 1;
                product.setQuantity(k);
                quantityTextField.setText(String.valueOf(product.getQuantity()));
            }
        });
        gridPane.setConstraints(buttonPlus, 5, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

        //----------------------------------Adding children-------------------------------------
        gridPane.getChildren().addAll(buttonMinus, quantityTextField, buttonPlus);
        anchorPane.getChildren().add(gridPane);
        anchorPane.setTopAnchor(gridPane, 0.0);
        anchorPane.setLeftAnchor(gridPane, 0.0);
        anchorPane.setRightAnchor(gridPane, 0.0);

        container.getChildren().add(anchorPane);
    }

    /**
     * Fill the labels with info from the product object.
     */
    private void setNewProductDetails(Product product)
    {
        if (product != null)
        {
            int labelsIterator = anchorPanes.size() - 1;
            int textFieldsIterator = anchorPanes.size() - 1;

            labels.get(3 * labelsIterator).setText(product.getProductName());
            labels.get(3 * labelsIterator + 1).setText(Double.toString(product.getPrice()));
            labels.get(3 * labelsIterator + 2).setText(Integer.toString(product.getAmount()));

            textFields.get(textFieldsIterator).setText(Integer.toString(product.getQuantity()));
        }
    }

    private void loadDummiesToSalesPanel()
    {
        if (getProductData().size() > 0)
        {
            for (int i = 0; i < getProductData().size(); i++)
            {
                addProductToSalesPanel(getProductData().get(i));
                setNewProductDetails(getProductData().get(i));
            }
        }
    }

    @FXML
    private void handleSellButton()
    {
        List<Product> productsToSell = new ArrayList<>();

        for (int m = 0; m < anchorPanes.size(); m++)
        {
            // Adding products to sellList when quantity to sell > 0
            if (getProductData().get(m).getQuantity() > 0)
            {
                productsToSell.add(getProductData().get(m));
            }
        }
        showSellDialog(productsToSell);
    }

    @Override
    public void deleteProduct()
    {
        container.getChildren().remove(anchorPanes.get(selectedIndexForSales));

        labels.remove(3 * selectedIndexForSales + 2);
        labels.remove(3 * selectedIndexForSales + 1);
        labels.remove(3 * selectedIndexForSales);

        textFields.remove(selectedIndexForSales);
        minusButtons.remove(selectedIndexForSales);
        plusButtons.remove(selectedIndexForSales);

        anchorPanes.remove(selectedIndexForSales);
    }

    private void setEditProductDetails(Product product)
    {
        if (product != null)
        {
            labels.get(3 * selectedIndexForSales).setText(product.getProductName());
            labels.get(3 * selectedIndexForSales + 1).setText(Double.toString(product.getPrice()));
            labels.get(3 * selectedIndexForSales + 2).setText(Integer.toString(product.getAmount()));
        }
    }

    @Override
    public void addNewProduct(Product product)
    {
        addProductToSalesPanel(product);
        setNewProductDetails(product);
        System.out.println("Dzialam!");
    }

    @Override
    public void editProduct()
    {
        setEditProductDetails(selectedSalesProduct);
    }

    public boolean showSellDialog(List<Product> productList)
    {
        try
        {
            mainApp = new MainApp();
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderOrSaleInfo.fxml"));
            VBox sellDialogPane = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(sellDialogPane);
            dialogStage.setScene(scene);
            dialogStage.setTitle("Summary");
            //dialogStage.setResizable(false);
            //primaryStage.setResizable(true);

            OrderOrSaleInfoController controller = loader.getController();
            controller.initSalesTabMainController(this);
            controller.setDialogStage(dialogStage);
            controller.getTitleLabel().setText("Are you sure you want to sell these products?");
            controller.displayListOfProducts(productList);

            // Show the dialog and wait until the user closes it
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
