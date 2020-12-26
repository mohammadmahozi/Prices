package com.mahozi.sayed.comparisist.products

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.FragmentCreateProductBinding
import com.mahozi.sayed.comparisist.hideKeyboard
import kotlinx.android.synthetic.main.fragment_create_product.*
import java.lang.Exception
import java.lang.NumberFormatException


/**
 * A simple [Fragment] subclass.
 * Use the [CreateProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProductFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1


    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductsViewModel


    private lateinit var productImageButton: ImageButton
    private lateinit var productNameEditText: AutoCompleteTextView
    private lateinit var productBrandEditText: AutoCompleteTextView
    private lateinit var productSizeEditText: AutoCompleteTextView
    private lateinit var productUnitEditText: AutoCompleteTextView
    private lateinit var productStoreEditText: AutoCompleteTextView
    private lateinit var productQuantityEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var productDateEditText: EditText
    private lateinit var isDealCheckBox: CheckBox

    private lateinit var productAdapter: ArrayAdapter<String>

    private lateinit var brandAdapter: ArrayAdapter<String>

    private lateinit var unitAdapter: ArrayAdapter<String>

    private lateinit var storeAdapter: ArrayAdapter<String>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)



        productAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, viewModel.getProductNames())


        brandAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, viewModel.getBrandNames())


        unitAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, viewModel.getProductUnits())


        storeAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, viewModel.getStoreNames())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)


        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)



        productImageButton = binding.productImageButton
        productImageButton.setOnClickListener{

            dispatchTakePictureIntent()
        }



        productNameEditText = binding.productNameEditText
        productNameEditText.setAdapter(productAdapter)



        productBrandEditText = binding.brandNameEditText
        productBrandEditText.setAdapter(brandAdapter)


        productSizeEditText = binding.sizeEditText


        productUnitEditText = binding.unitEditText
        productUnitEditText.setAdapter(unitAdapter)


        productStoreEditText = binding.storeNameEditText
        productStoreEditText.setAdapter(storeAdapter)



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


            val size = try {

                val sizeAsString = productSizeEditText.text.toString().trim()

                if (sizeAsString == ""){
                    Toast.makeText(requireContext(), "Size field cannot be empty", Toast.LENGTH_LONG).show()
                    return false
                }

                sizeAsString.toDouble()


                }

                catch (e: NumberFormatException){

                    0.0
                }


            val quantity = try {

                productQuantityEditText.text.toString().trim().toDouble()
            }

            catch (e: NumberFormatException){
                -1.0
            }

            val price = try {

                productPriceEditText.text.toString().trim().toDouble()
            }

            catch (e: NumberFormatException){
                -1.0
            }

            val formInput = ProductFormModel(
                productNameEditText.listSelection.toLong(),
                productNameEditText.text.toString().trim(),
                productBrandEditText.listSelection.toLong(),
                productBrandEditText.text.toString().trim(),
                size,
                productUnitEditText.text.toString().trim(),
                productStoreEditText.listSelection.toLong(),
                productStoreEditText.text.toString().trim(),
                quantity,
                price,
                productDateEditText.text.toString().trim(),
                isDealCheckBox.isChecked
            )

            if (isFormValid(formInput)){

                viewModel.insertProduct(formInput)


                Navigation.findNavController(requireView()).popBackStack()
                //Navigation.findNavController(requireView()).navigate(CreateProductFragmentDirections.actionCreateProductFragmentToCreateProductItemFragment())
                hideKeyboard()

            }

            else{
                Toast.makeText(
                    requireContext(),
                    "Name, brand, size, and unit cannot be Empty",
                    Toast.LENGTH_LONG
                ).show()
            }



        }

        else if(item.itemId == R.id.scan_barcode_menu_item){

            view?.findNavController()?.navigate(CreateProductFragmentDirections.actionCreateProductFragmentToQRScanFragment())

        }
            return super.onOptionsItemSelected(item)
    }


    private fun isFormValid(input: ProductFormModel): Boolean{

        if (input.productName.isEmpty() || input.brandName.isEmpty() || input.unit.isEmpty()){
            return false
        }

        return true
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            productImageButton.setImageBitmap(imageBitmap)
        }

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