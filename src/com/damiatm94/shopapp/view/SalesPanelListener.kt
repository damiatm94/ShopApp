package com.damiatm94.shopapp.view

import com.damiatm94.shopapp.model.Product

/**
 * Created by damian on 24.08.16.
 */
interface SalesPanelListener {
    fun addProductToSalesPanel(product: Product)

    fun setNewProductDetails(product: Product)
}