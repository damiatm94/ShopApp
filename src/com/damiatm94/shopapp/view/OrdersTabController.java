package com.damiatm94.shopapp.view;


import javafx.fxml.FXML;

import javax.persistence.criteria.Order;

/**
 * Created by damian on 29.09.16.
 */
public class OrdersTabController
{
    private ProductsOverviewController mainController;

    public OrdersTabController()
    {

    }

    @FXML
    private void initialize()
    {

    }

    public void initMainController(ProductsOverviewController productsOverviewController)
    {
        mainController = productsOverviewController;
    }

}
