package com.example.mtsinternship.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.mtsinternship.App
import com.example.mtsinternship.R
import com.example.mtsinternship.databinding.FragmentCurrencyDetailsBinding
import com.example.mtsinternship.viewmodel.CurrencyDetailsViewModel
import java.text.NumberFormat
import javax.inject.Inject

class CurrencyDetailsFragment : Fragment() {

    private var _binding: FragmentCurrencyDetailsBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: CurrencyDetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.currencyName = it.getString("currencyName").toString()
            viewModel.exchangeRate = it.getFloat("exchangeRate")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCurrencyDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupObserver() {
        viewModel.convertedValue.observe(requireActivity(), Observer {
            if (it == 0.0) {
                binding.etConvertedCurrencyAmount.setText("")
            } else {
                binding.etConvertedCurrencyAmount.setText(it.toString())
            }
        })
    }

    private fun setupView() {
        binding.apply {
            val numberFormat = NumberFormat.getInstance()
            numberFormat.maximumFractionDigits = 4
            numberFormat.minimumFractionDigits = 4
            tvExchangeRate.setText(
                getString(
                    R.string.convertation_text,
                    numberFormat.format(viewModel.exchangeRate),
                    viewModel.currencyName
                )
            )
            tilConvertedCurrencyAmount.hint = viewModel.currencyName
            etBaseCurrency.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(string: Editable) {
                    viewModel.convertValue(etBaseCurrency.text.toString().toDoubleOrNull()?: 0.0)
                }

            })
        }
    }
}
