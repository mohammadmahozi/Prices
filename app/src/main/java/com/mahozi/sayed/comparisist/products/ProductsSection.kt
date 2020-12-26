package com.mahozi.sayed.comparisist.products


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.products.database.ProductEntity
import com.mahozi.sayed.comparisist.products.database.ProductModel
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters


class ProductsSection: Section(SectionParameters.builder().itemResourceId(R.layout.recycler_item_product).build()) {

    var dataList = listOf<ProductModel>()

    override fun getContentItemsTotal(): Int {
        return dataList.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder? {
        // return a custom instance of ViewHolder for the items of this section
        return ProductViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder: ProductViewHolder = holder as ProductViewHolder

        // bind your view here
        val productModel = dataList[position]
        val productInfo = productModel.productName + ", " + productModel.brandName + ", " + productModel.size + productModel.sizeUnit + ", " + productModel.storeName
        itemHolder.productNameTextView.text = productInfo

        itemHolder.priceTextView.text = productModel.price.toString()
    }



    internal class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productNameTextView: TextView = itemView.findViewById(R.id.product_info_text_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)
        val productImageView: ImageView = itemView.findViewById(R.id.product_image_view)

    }

}