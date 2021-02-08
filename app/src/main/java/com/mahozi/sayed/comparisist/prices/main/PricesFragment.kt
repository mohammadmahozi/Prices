package com.mahozi.sayed.comparisist.prices.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mahozi.sayed.comparisist.AppDatabase
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.FragmentPricesBinding
import com.mahozi.sayed.comparisist.prices.PricesSharedViewModel
import com.mahozi.sayed.comparisist.prices.database.PricesRepository
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter



class PricesFragment : Fragment() {

    private var _binding: FragmentPricesBinding? = null
    private val binding get() = _binding!!


    private lateinit var pricesRecyclerView: RecyclerView

    private lateinit var addPriceFab: FloatingActionButton

    lateinit var pricesSharedViewModel: PricesSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args: PricesFragmentArgs by navArgs()
        val productId = args.productId
        val storeId = args.storeId


        val navController = NavHostFragment.findNavController(this)

        val pricesRepository = PricesRepository(AppDatabase.getInstance(requireContext()))

        val pricesSharedViewModelFactory = PricesSharedViewModel.Factory(pricesRepository, productId, storeId)
        val viewModelProvider = ViewModelProvider(navController.getViewModelStoreOwner(R.id.prices_nav_graph), pricesSharedViewModelFactory)

        pricesSharedViewModel = viewModelProvider.get(PricesSharedViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentPricesBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pricesSection = PricesSection()


        val pricesSectionAdapter = SectionedRecyclerViewAdapter()
        pricesSectionAdapter.addSection(pricesSection)


        pricesRecyclerView = binding.pricesRecyclerView
        pricesRecyclerView.layoutManager = LinearLayoutManager(context)
        pricesRecyclerView.adapter = pricesSectionAdapter


        pricesSharedViewModel.pricesDtoLiveDataList.observe(viewLifecycleOwner, Observer {

            pricesSection.dataList = it
            pricesSectionAdapter.notifyDataSetChanged()
        })


        addPriceFab = binding.createPriceFab
        addPriceFab.setOnClickListener {

            binding.root.findNavController().navigate(PricesFragmentDirections.actionPricesFragmentToCreatePriceFragment())
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}