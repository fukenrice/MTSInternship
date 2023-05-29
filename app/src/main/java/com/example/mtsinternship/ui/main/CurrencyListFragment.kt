package com.example.mtsinternship.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mtsinternship.App
import com.example.mtsinternship.data.model.CurrencyModel
import com.example.mtsinternship.databinding.FragmentCurrencyListBinding
import com.example.mtsinternship.ui.adapter.CurrencyAdapter
import com.example.mtsinternship.ui.decoration.GridSpacingItemDecoration
import com.example.mtsinternship.utils.Status
import com.example.mtsinternship.viewmodel.CurrencyListViewModel
import java.util.Locale
import javax.inject.Inject


class CurrencyListFragment : Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: CurrencyListViewModel

    private lateinit var adapter: CurrencyAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
        viewModel.getCurrencies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        binding.apply {
            rvMain.layoutManager = GridLayoutManager(context, 2)
            adapter = CurrencyAdapter({ currency -> onCurrencyClick(currency) }, mutableListOf())
            rvMain.adapter = adapter

            val spanCount = 2

            val spacing = 30

            val includeEdge = true
            rvMain.addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    spacing,
                    includeEdge
                )
            )

            pullToRefresh.setOnRefreshListener {
                viewModel.getCurrencies()
                pullToRefresh.isRefreshing = false
            }

            enableSearchView(searchView, false)
            searchView.setIconifiedByDefault(false)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filterList(newText?.uppercase(Locale.ROOT) ?: "")
                    return true
                }
            })
        }
    }

    private fun setupObserver() {
        viewModel.getCurrencyList().observe(requireActivity(), Observer {
            when(it.status) {
                Status.LOADING -> {
                    enableSearchView(binding.searchView, false)
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { it1 -> renderList(it1) }
                    enableSearchView(binding.searchView, true)
                }
                Status.ERROR -> {
                    enableSearchView(binding.searchView, false)
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun onCurrencyClick(currency: CurrencyModel) {
        val action = CurrencyListFragmentDirections.actionListFragmentToDetailsFragment(exchangeRate = currency.value.toFloat(), currencyName = currency.name)
        findNavController().navigate(action)
    }

    private fun renderList(currencies: List<CurrencyModel>) {
        adapter.clear()
        adapter.addData(currencies)
        adapter.notifyDataSetChanged()
    }

    // Взято с https://stackoverflow.com/a/39408050.
    private fun enableSearchView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val viewGroup = view
            for (i in 0 until viewGroup.childCount) {
                val child = viewGroup.getChildAt(i)
                enableSearchView(child, enabled)
            }
        }
    }
}
