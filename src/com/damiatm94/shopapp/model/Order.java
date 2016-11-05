package com.damiatm94.shopapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

/**
 * Created by damian on 29.10.16.
 */
public class Order
{
    private String name;
    private ObservableList<Product> listOfProducts;
    private String dateOfDeliveryConfirmation;

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

    public String getDateOfDeliveryConfirmation()
    {
        return dateOfDeliveryConfirmation;
    }

    public void setDateOfDeliveryConfirmation(String dateOfDeliveryConfirmation)
    {
        this.dateOfDeliveryConfirmation = dateOfDeliveryConfirmation;
    }

    @Override
    public String toString()
    {
        return "" + name + "    |   Delivered: " + dateOfDeliveryConfirmation;
    }
}
