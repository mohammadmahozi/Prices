package com.mahozi.sayed.comparisist.products


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.products.database.ProductEntity
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder


class ProductsSection: Section(SectionParameters.builder().itemResourceId(R.layout.recycler_item_product).build()) {

    var dataList = listOf<ProductEntity>()

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
        itemHolder.productNameTextView.text = dataList.get(position).productName
    }



    internal class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productNameTextView: TextView = itemView.findViewById(R.id.product_name_text_view)

    }

}