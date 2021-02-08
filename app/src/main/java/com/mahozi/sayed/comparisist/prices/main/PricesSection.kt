package com.mahozi.sayed.comparisist.prices.main

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.RecyclerItemPricesBinding
import com.mahozi.sayed.comparisist.prices.database.PriceDto
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class PricesSection: Section(SectionParameters.builder().itemResourceId(R.layout.recycler_item_prices).build()) {

    var dataList = listOf<PriceDto>()



    override fun getContentItemsTotal(): Int {

        return dataList.size
    }



    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {

        val binding = RecyclerItemPricesBinding.inflate(LayoutInflater.from(view.context), view as ViewGroup, false)
        return PriceViewHolder(binding)
    }



    @SuppressLint("SetTextI18n")
    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        val priceViewHolder = holder as PriceViewHolder

        val priceDto = dataList[position]

        priceViewHolder.apply {

            priceTextView.text = priceDto.price.toString()
            quantityTextView.text = " x${priceDto.quantity}"
            dateTextView.text = priceDto.dateAdded

            if (priceDto.isDeal){
                dealImageView.visibility = View.VISIBLE
            }

            else{
                dealImageView.visibility = View.INVISIBLE
            }
        }


    }



    internal class PriceViewHolder(binding: RecyclerItemPricesBinding): RecyclerView.ViewHolder(binding.root){

        val priceTextView = binding.priceRecyclerItemPriceTextView
        val quantityTextView = binding.priceRecyclerItemQuantityTextView
        val dateTextView = binding.priceRecyclerItemDateTextView
        val dealImageView = binding.priceRecyclerItemDealImageView

    }



}