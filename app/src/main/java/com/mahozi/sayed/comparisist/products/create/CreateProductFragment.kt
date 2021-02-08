package com.mahozi.sayed.comparisist.products.create

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.mahozi.sayed.comparisist.AppDatabase
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.Status
import com.mahozi.sayed.comparisist.databinding.FragmentCreateProductBinding
import com.mahozi.sayed.comparisist.hideKeyboard
import com.mahozi.sayed.comparisist.products.ProductsSharedViewModel
import com.mahozi.sayed.comparisist.products.create.qr.BarcodeScannerFragment
import com.mahozi.sayed.comparisist.products.database.ProductsRepository
import kotlinx.android.synthetic.main.fragment_create_price.view.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [CreateProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProductFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1


    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!


    private lateinit var productImageButton: ImageView
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

    //No need to provide argument again since is has been instantiated in the previous fragment
    private val productsSharedViewModel: ProductsSharedViewModel by navGraphViewModels(R.id.products_nav_graph)


    private lateinit var createProductViewModel: CreateProductViewModel

    private var barcode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val retrofit = Retrofit.Builder().baseUrl("https://world.openfoodfacts.org/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val productsRepository = ProductsRepository(
            AppDatabase.getInstance(requireContext()),
            retrofit
        )



        createProductViewModel = ViewModelProvider(
            this,
            CreateProductViewModel.Factory(
                productsRepository,
                productsSharedViewModel.productsAndPricesLiveDataList.value ?: listOf()
            )
        ).get(CreateProductViewModel::class.java)




        productAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            createProductViewModel.getProductNames()
        )


        brandAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            createProductViewModel.getBrandNames()
        )


        unitAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            createProductViewModel.getProductUnits()
        )


        storeAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            createProductViewModel.getStoreNames()
        )

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
        productDateEditText.setText(
            SimpleDateFormat(
                "yyyy/MM/dd",
                Locale.getDefault()
            ).format(Date())
        )
        productDateEditText.setOnClickListener {

            DatePickerDialogFragment(object : DatePickerDialogFragment.OnDateReceived {
                override fun getDate(date: String?) {

                    productDateEditText.setText(date)
                }
            }).show(requireActivity().supportFragmentManager, "datePicker")
        }


        isDealCheckBox = binding.isDealCheckBox


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //called after getting back barcode scan from qr scan fragment
        binding.root.findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            BarcodeScannerFragment.SCANNED_BARCODE_KEY
        )?.observe(viewLifecycleOwner) { it ->

            barcode = it



            createProductViewModel.selectProductWithBrand(it).observe(viewLifecycleOwner, {

                if (it.status == Status.SUCCESS) {

                    val data = it.data!!
                    Glide.with(requireContext()).load(data.productImagePath)
                        .override(1100, 400).fitCenter().into(
                            productImageButton
                        )

                    productNameEditText.setText(data.productName)
                    productBrandEditText.setText(data.brandName)


                    productSizeEditText.setText(data.size.toString())
                    productUnitEditText.setText(data.sizeUnit)

                } else if (it.status == Status.ERROR) {

                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }

            })
        }
    }



    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)

    }

    private fun dispatchTakePictureIntent() {

        if(requestCameraPermission()){

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }

            catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }


    }

    private fun requestCameraPermission(): Boolean{

        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {

               return true

        }
            else -> {
                // You can directly ask for the permission.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            }
        }

        return false
    }





    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.create_product_fragment_menu, menu)




    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.create_product_menu_confirm) {

            if (isFormValid()){

            //TODO check if image view has non default image
            val file = createImageFile()
            val outputStream = FileOutputStream(file)
            (productImageButton.drawable as BitmapDrawable).bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                outputStream
            )
            outputStream.close()

            val formInput = ProductFormModel(
                file.absolutePath,
                productNameEditText.text.toString().trim(),
                barcode,
                productBrandEditText.text.toString().trim(),
                productSizeEditText.text.toString().toDouble(),
                productUnitEditText.text.toString().trim(),
                productStoreEditText.text.toString().trim(),
                productQuantityEditText.text.toString().trim().toDouble(),
                productPriceEditText.text.toString().trim().toDouble(),
                productDateEditText.text.toString().trim(),
                isDealCheckBox.isChecked
            )

                createProductViewModel.insertProduct(formInput)

                Navigation.findNavController(requireView()).popBackStack()

                hideKeyboard()

            }
        }

        else if(item.itemId == R.id.create_product_menu_scan_barcode){

            binding.root.findNavController().navigate(CreateProductFragmentDirections.actionCreateProductFragmentToQRScanFragment())
        }

        return super.onOptionsItemSelected(item)
    }


    private fun isFormValid(): Boolean{

        var isFormValid = true
        val productFormValidator = ProductFormValidator()

        productFormValidator.validateProductName(productNameEditText.text.toString()).run {
            if (!isValid) productNameEditText.error = errorMessage
            isFormValid = isFormValid && isValid

        }

        productFormValidator.validateBrandName(productBrandEditText.text.toString()).run {
            if (!isValid) productBrandEditText.error = errorMessage
            isFormValid = isFormValid && isValid

        }

        productFormValidator.validateSize(productSizeEditText.text.toString()).run {
            if (!isValid) productSizeEditText.error = errorMessage
            isFormValid = isFormValid && isValid

        }

        productFormValidator.validateUnit(productUnitEditText.text.toString()).run {
            if (!isValid) productUnitEditText.error = errorMessage
            isFormValid = isFormValid && isValid

        }

        productFormValidator.validateStoreName(productStoreEditText.text.toString()).run {
            if (!isValid) productStoreEditText.error = errorMessage
            isFormValid = isFormValid && isValid

        }

        productFormValidator.validateQuantity(productQuantityEditText.text.toString()).run {
            if (!isValid) productQuantityEditText.error = errorMessage
            isFormValid = isFormValid && isValid

        }

        productFormValidator.validatePrice(productPriceEditText.text.toString()).run {
            if (!isValid) productPriceEditText.error = errorMessage
            isFormValid = isFormValid && isValid

        }
        return isFormValid
    }





    private fun saveImage(){

        //TODO SAVE IMAGE HERE
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

}