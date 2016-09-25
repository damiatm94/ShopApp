//package com.damiatm94.address.view
//
//import com.damiatm94.shopapp.MainApp
//import com.damiatm94.shopapp.model.Product
//
///**
// * Created by damian on 24.08.16.
// */
//class NewProductController(var listener: SalesPanelListener,
//                           val mainApp: MainApp): OnProductAddedListener {
//    var tempProduct: Product? = null
//    var isNewProduct: Boolean = true
//
//
//    override fun onProductAdded(wasAdded: Boolean) {
//        if (wasAdded)
//        {
//            MainApp.productData.add(tempProduct!!)
//            listener.addProductToSalesPanel(tempProduct!!)
//            listener.setNewProductDetails(tempProduct!!)
//        }
//    }
//
//    fun handleNewProduct()
//    {
//        tempProduct = Product("", 0.0, 0, 0)
//        isNewProduct = true
//        mainApp.showProductEditDialog(tempProduct!!, isNewProduct);
//    }
//
//}
//
//interface OnProductAddedListener
//{
//    fun onProductAdded(wasAdded: Boolean)
//
//    //void onProductAdded(boolean wasAdded);
//}
//
