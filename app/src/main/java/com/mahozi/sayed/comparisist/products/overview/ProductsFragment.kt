package com.mahozi.sayed.comparisist.products.overview

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mahozi.sayed.comparisist.AppDatabase
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.FragmentProductsBinding
import com.mahozi.sayed.comparisist.products.ProductsSharedViewModel
import com.mahozi.sayed.comparisist.products.database.ProductsRepository
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var createProductFab: FloatingActionButton

    private lateinit var searchView: SearchView

    private lateinit var productsSharedViewModel: ProductsSharedViewModel
    private lateinit var productsViewModel: ProductsViewModel

    private val productsSection: ProductsSection by lazy { ProductsSection() }
    private val sectionAdapter: SectionedRecyclerViewAdapter by lazy { SectionedRecyclerViewAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = NavHostFragment.findNavController(this)

        val retrofit = Retrofit.Builder().baseUrl("https://world.openfoodfacts.org/")
            .addConverterFactory(GsonConverterFactory.create()).build()


        val productsRepository = ProductsRepository(
            AppDatabase.getInstance(requireContext()),
            retrofit
        )


        val sharedProductsViewModelFactory = ProductsSharedViewModel.Factory(productsRepository)
        val viewModelProvider = ViewModelProvider(
            navController.getViewModelStoreOwner(R.id.products_nav_graph),
            sharedProductsViewModelFactory
        )
        productsSharedViewModel = viewModelProvider.get(ProductsSharedViewModel::class.java)


        productsViewModel = ViewModelProvider(this, ProductsViewModel.Factory(productsRepository)).get(
            ProductsViewModel::class.java
        )

        sectionAdapter.addSection(productsSection)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.createProductsFab.setOnClickListener {

            val action = ProductsFragmentDirections.actionProductsFragmentToCreateProductFragment()

            view.findNavController().navigate(action)
        }



        binding.productsRecyclerView.apply {

            layoutManager = LinearLayoutManager(context)
            adapter = sectionAdapter
        }



        productsSharedViewModel.productsAndPricesLiveDataList.observe(viewLifecycleOwner, Observer {

            productsSection.dataList = it
            sectionAdapter.notifyDataSetChanged()
        })
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_fragment_menu, menu)

        searchView = menu.findItem(R.id.product_search).actionView as SearchView
        initiateSearchView()

        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun initiateSearchView(){

        // Associate searchable configuration with the SearchView
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.apply {

            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

            setOnQueryTextListener(buildSearchQueryTextListener())
        }

    }


    private fun buildSearchQueryTextListener(): SearchView.OnQueryTextListener{

        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {

                filterProductsAndUpdateSection(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }
        }
    }


    private fun filterProductsAndUpdateSection(query: String){

        val filterData = productsViewModel.filter(productsSharedViewModel.productsAndPricesLiveDataList.value?: listOf(), query)
        productsSection.dataList = filterData
        sectionAdapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    fun onBackPressed(): Boolean {
//        if (!searchView.isIconified) {
//            searchView.isIconified = true
//            return true
//        }
//
//        return false
//    }

}