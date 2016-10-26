package com.damiatm94.shopapp.view;

import com.damiatm94.shopapp.model.Product;

/**
 * Created by damian on 29.09.16.
 */
public interface ProductsListener
{
    void addNewProduct(Product product);
    void deleteProduct();
    void editProduct();
}
