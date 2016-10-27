package com.damiatm94.shopapp.model;

/**
 * Created by damian on 24.07.16.
 */

import javafx.beans.property.*;

/**
 * model class for a product
 */

public class Product
{
    private final StringProperty productName;
    private final DoubleProperty price;
    private final IntegerProperty amount;
    private final IntegerProperty minAmount;
    private final IntegerProperty quantity;

    /**
     * default constructor
     */

    public Product()
    {
        this(null, 0.0, 0, 0);
    }


    /**
     * constructor with some initial data
     */
    public Product(String productName, Double price, Integer amount, Integer minAmount)
    {
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.minAmount = new SimpleIntegerProperty(minAmount);

        this.quantity = new SimpleIntegerProperty(0);
    }

    public String getProductName()
    {
        return productName.get();
    }

    public StringProperty productNameProperty()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName.set(productName);
    }

    public double getPrice()
    {
        return price.get();
    }

    public DoubleProperty priceProperty()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price.set(price);
    }

    public int getAmount()
    {
        return amount.get();
    }

    public IntegerProperty amountProperty()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount.set(amount);
    }

    public int getMinAmount()
    {
        return minAmount.get();
    }

    public IntegerProperty minAmountProperty()
    {
        return minAmount;
    }

    public void setMinAmount(int minAmount)
    {
        this.minAmount.set(minAmount);
    }

    public int getQuantity()
    {
        return quantity.get();
    }

    public IntegerProperty quantityProperty()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity.set(quantity);
    }

    @Override
    public String toString()
    {
        return "Product{" +
                "productName=" + productName +
                ", price=" + price +
                ", amount=" + amount +
                ", minAmount=" + minAmount +
                ", quantity=" + quantity +
                '}';
    }
}
