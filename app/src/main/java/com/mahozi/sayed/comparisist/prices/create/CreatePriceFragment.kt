package com.mahozi.sayed.comparisist.prices.create

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CheckBox
import android.widget.EditText
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.mahozi.sayed.comparisist.AppDatabase
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.FragmentCreatePriceBinding
import com.mahozi.sayed.comparisist.hideKeyboard
import com.mahozi.sayed.comparisist.prices.PricesSharedViewModel
import com.mahozi.sayed.comparisist.prices.database.PriceEntity
import com.mahozi.sayed.comparisist.prices.database.PricesRepository
import com.mahozi.sayed.comparisist.products.create.DatePickerDialogFragment
import java.text.SimpleDateFormat
import java.util.*


class CreatePriceFragment : Fragment() {

    private var _binding: FragmentCreatePriceBinding? = null
    private val binding get() = _binding!!

    private val productQuantityEditText: EditText by lazy { binding.quantityEditText }
    private val productPriceEditText: EditText by lazy { binding.priceEditText }
    private val productDateEditText: EditText by lazy { binding.dateEditText }
    private val isDealCheckBox: CheckBox by lazy { binding.isDealCheckBox3 }

    //No need to provide argument again since is has been instantiated in the previous fragment (PricesFragment)
    private val pricesSharedViewModel: PricesSharedViewModel by navGraphViewModels(R.id.prices_nav_graph)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        _binding = FragmentCreatePriceBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDateEditText.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date()))
        productDateEditText.setOnClickListener {

            DatePickerDialogFragment(object : DatePickerDialogFragment.OnDateReceived {
                override fun getDate(date: String?) {

                    productDateEditText.setText(date)
                }
            }).show(requireActivity().supportFragmentManager, "datePicker")
        }

        pricesSharedViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.create_price_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.create_price_menu_confirm){

            if (isFormValid()){

                pricesSharedViewModel.run {

                    val price = productPriceEditText.text.toString().toDouble()
                    val quantity = productQuantityEditText.text.toString().toDouble()
                    val date = productDateEditText.text.toString()
                    val isDeal = isDealCheckBox.isChecked

                    //TODO check date is unique before inserting
                    insertPrice(PriceEntity(
                        productId,
                        price,
                        storeId,
                        quantity,
                        isDeal,
                        date
                    ))
                }

                Navigation.findNavController(requireView()).popBackStack()

                hideKeyboard()
            }



        }

        return super.onOptionsItemSelected(item)
    }

    private fun isFormValid(): Boolean{

        var isFormValid = true
        val priceFormValidator = PriceFormValidator()


        priceFormValidator.validateQuantity(productQuantityEditText.text.toString()).run {

            if (!isValid) productQuantityEditText.error = errorMessage

            isFormValid = isFormValid && isValid
        }


        priceFormValidator.validatePrice(productPriceEditText.text.toString()).run {

            if (!isValid) productPriceEditText.error = errorMessage

            isFormValid = isFormValid && isValid

        }

        return isFormValid
    }







//    private fun getInputFromFields(): PriceFormModel{
//
//
//
//    }
}