package com.mahozi.sayed.comparisist.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mahozi.sayed.comparisist.databinding.FragmentProductsBinding
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter


class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var createProductFab: FloatingActionButton

    private lateinit var viewModel: ProductsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductsBinding.inflate(inflater, container, false)


        createProductFab = binding.createProductsFab
        createProductFab.setOnClickListener {

            view?.findNavController()?.navigate(ProductsFragmentDirections.actionProductsFragmentToCreateProductFragment())
        }


        val sectionAdapter = SectionedRecyclerViewAdapter()

        val productsSection = ProductsSection()
        viewModel.productsAndPricesLiveDataList.observe(viewLifecycleOwner, Observer {

            productsSection.dataList = it
        })
        sectionAdapter.addSection(productsSection)




        val recyclerView = binding.productsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = sectionAdapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}