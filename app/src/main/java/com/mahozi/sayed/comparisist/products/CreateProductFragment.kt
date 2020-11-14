package com.mahozi.sayed.comparisist.products

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.FragmentCreateProductBinding
import com.mahozi.sayed.comparisist.hideKeyboard
import com.mahozi.sayed.comparisist.products.domain.ProductModel


/**
 * A simple [Fragment] subclass.
 * Use the [CreateProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProductFragment : Fragment() {

    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductsViewModel


    private lateinit var productNameEditText: AutoCompleteTextView
    private lateinit var productBrandEditText: AutoCompleteTextView
    private lateinit var productSizeEditText: AutoCompleteTextView
    private lateinit var productUnitEditText: AutoCompleteTextView
    private lateinit var productStoreEditText: AutoCompleteTextView
    private lateinit var productQuantityEditText: AutoCompleteTextView
    private lateinit var productPriceEditText: AutoCompleteTextView
    private lateinit var productDateEditText: AutoCompleteTextView
    private lateinit var isDealCheckBox: CheckBox


    private lateinit var brandAdapter: ArrayAdapter<String>
    private lateinit var sizeAdapter: ArrayAdapter<Double>
    private lateinit var unitAdapter: ArrayAdapter<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)


        brandAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, viewModel.getBrandNames())
        sizeAdapter = ArrayAdapter<Double>(requireContext(), android.R.layout.simple_dropdown_item_1line, viewModel.getSizeNumbers())
        unitAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, viewModel.getSizeUnits())







    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)


        productNameEditText = binding.productNameEditText


        productBrandEditText = binding.brandNameEditText
        productBrandEditText.setAdapter(brandAdapter)


        productSizeEditText = binding.sizeEditText
        productSizeEditText.setAdapter(sizeAdapter)


        productUnitEditText = binding.unitEditText
        productUnitEditText.setAdapter(unitAdapter)


        productStoreEditText = binding.storeNameEditText
        productQuantityEditText = binding.quantityEditText
        productPriceEditText = binding.priceEditText
        productDateEditText = binding.dateEditText
        isDealCheckBox = binding.isDealCheckBox



        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.confirm_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.confirm) {
            viewModel.onConfirmCreateProduct(
                ProductModel(

                    productNameEditText.text.toString().trim(),
                    productBrandEditText.listSelection.toLong(),
                    productSizeEditText.listSelection.toLong()
                    )
            )

            viewModel.selectedBrandName = productBrandEditText.text.toString().trim()
            viewModel.selectedSizeNumber = productSizeEditText.text.toString().trim()
            viewModel.selectedSizeUnit = productUnitEditText.text.toString().trim()


            //Navigation.findNavController(requireView()).popBackStack()
            Navigation.findNavController(requireView()).navigate(CreateProductFragmentDirections.actionCreateProductFragmentToCreateProductItemFragment())
            hideKeyboard()

        }
            return super.onOptionsItemSelected(item)
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
         * @return A new instance of fragment CreateProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateProductFragment().apply {
                arguments = Bundle().apply {


                }
            }
    }
}