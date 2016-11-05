package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by damian on 01.11.16.
 */
public class MadeOrderInfoController
{
    private Stage dialogStage;
    private boolean okClicked = false;
    private Order order;

    @FXML private Label titleLabel;
    @FXML private VBox container;

    public Label getTitleLabel()
    {
        return titleLabel;
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public MadeOrderInfoController()
    {
    }

    @FXML
    private void initialize()
    {
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    public void setMadeOrder(Order order)
    {
        this.order = order;
    }
}
