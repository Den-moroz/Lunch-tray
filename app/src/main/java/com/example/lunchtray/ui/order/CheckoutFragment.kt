package com.example.lunchtray.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lunchtray.R
import com.example.lunchtray.databinding.FragmentCheckoutBinding
import com.example.lunchtray.model.OrderViewModel
import com.google.android.material.snackbar.Snackbar

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val root = binding.root

        sharedViewModel.calculateTaxAndTotal()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            checkoutFragment = this@CheckoutFragment
        }
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_checkoutFragment_to_startOrderFragment)
    }

    fun submitOrder() {
        Snackbar.make(binding.root, R.string.submit_order, Snackbar.LENGTH_SHORT).show()
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_checkoutFragment_to_startOrderFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
