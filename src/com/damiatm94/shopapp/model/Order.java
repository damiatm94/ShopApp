package com.damiatm94.shopapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by damian on 29.10.16.
 */
public class Order
{
    private String name;
    private ObservableList<Product> listOfProducts;

    public Order()
    {
    }

    public Order(String name, ObservableList<Product> listOfProducts)
    {
        this.name = name;
        this.listOfProducts = listOfProducts;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ObservableList<Product> getListOfProducts()
    {
        return listOfProducts;
    }

    public void setListOfProducts(ObservableList<Product> ordersList)
    {
        this.listOfProducts = ordersList;
    }
}
