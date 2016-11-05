package com.damiatm94.shopapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Created by damian on 14.08.16.
 */
public class SalesHistoryController
{

    @FXML
    private ListView<String> historyList;

    @FXML
    private void initialize()
    {
    }

    public void setItemsToList()
    {
        historyList.setItems(OrderOrSaleInfoController.getItems());
    }

}
