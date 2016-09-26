package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;

/**
 * Created by damian on 24.07.16.
 */
public class RootLayoutController
{
    //reference to the MainApp
    private MainApp mainApp;

    //Main application called this to give a reference back to itself
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }

    /**
     * Opens the Sales History list.
     */
    @FXML
    private void handleShowSalesHistory()
    {
        mainApp.showSalesHistory();
    }
}
