package com.mahozi.sayed.comparisist.products.overview


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.RecyclerItemProductBinding
import com.mahozi.sayed.comparisist.products.database.product.ProductDto
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters


class ProductsSection: Section(SectionParameters.builder().itemResourceId(R.layout.recycler_item_product).build()) {

    var dataList = listOf<ProductDto>()

    override fun getContentItemsTotal(): Int {
        return dataList.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {

        val binding = RecyclerItemProductBinding.inflate(LayoutInflater.from(view.context), view as ViewGroup, false)
        return ProductViewHolder(binding)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder: ProductViewHolder = holder as ProductViewHolder

        // bind your view here
        val productModel = dataList[position]
        val productInfo = productModel.productName + ", " + productModel.brandName + ", " + productModel.size + productModel.sizeUnit + "i got you in my sight"


        itemHolder.apply {

            productNameTextView.text = productInfo
            storeNameTextView.text = productModel.storeName
            priceTextView.text = productModel.price.toString()

            val productImageButton = productImageButton
            Glide
                .with(productImageButton.context)
                .load(productModel.productImagePath)
                .fitCenter()
                .into(productImageButton)

        }

    }



    inner class ProductViewHolder(binding: RecyclerItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        val productNameTextView = binding.productInfoTextView
        val storeNameTextView = binding.storeNameTextView
        val priceTextView = binding.priceTextView
        val productImageButton = binding.productImageView

        init {

            binding.root.setOnClickListener {

                val productModel = dataList[layoutPosition]
                val action = ProductsFragmentDirections.actionProductsFragmentToPricesFragment(
                    productModel.productId,
                    productModel.storeId
                )

                itemView.findNavController().navigate(action)
            }


        }

    }




}