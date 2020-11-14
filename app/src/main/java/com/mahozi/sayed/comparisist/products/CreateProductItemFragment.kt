package com.mahozi.sayed.comparisist.products

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mahozi.sayed.comparisist.R
import com.mahozi.sayed.comparisist.databinding.FragmentCreateProductBinding
import com.mahozi.sayed.comparisist.databinding.FragmentCreateProductItemBinding
import com.mahozi.sayed.comparisist.hideKeyboard
import com.mahozi.sayed.comparisist.products.database.ProductEntity


/**
 * A simple [Fragment] subclass.
 * Use the [CreateProductItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProductItemFragment : Fragment() {


    private var _binding: FragmentCreateProductItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentCreateProductItemBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.confirm_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.confirm) {

            //TODO add

            Navigation.findNavController(requireView()).popBackStack()

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
         * @return A new instance of fragment CreateProductItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateProductItemFragment().apply {
                arguments = Bundle().apply {


                }
            }
    }
}