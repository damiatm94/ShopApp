package com.damiatm94.shopapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by damian on 29.10.16.
 */
public class Order
{
    private String name;
    private ObservableList<Product> ordersList = FXCollections.observableArrayList();

    public Order()
    {

    }

    public Order(String name, ObservableList<Product> ordersList)
    {
        this.name = name;
        this.ordersList = ordersList;
    }
}
