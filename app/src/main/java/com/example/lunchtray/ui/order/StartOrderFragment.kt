package com.example.lunchtray.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lunchtray.R
import com.example.lunchtray.databinding.FragmentStartOrderBinding

class StartOrderFragment : Fragment() {

    private var _binding: FragmentStartOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startFragment = this
    }

    fun startOrder() {
        findNavController().navigate(R.id.action_startOrderFragment_to_entreeMenuFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
