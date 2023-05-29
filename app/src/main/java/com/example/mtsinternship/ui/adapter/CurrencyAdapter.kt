package com.example.mtsinternship.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtsinternship.R
import com.example.mtsinternship.data.model.CurrencyModel
import com.example.mtsinternship.databinding.CurrencyListItemBinding


class CurrencyAdapter(
    private val onItemClicked: (currency: CurrencyModel) -> Unit,
    private val currencies: MutableList<CurrencyModel>,
) : RecyclerView.Adapter<CurrencyAdapter.DataViewHolder>() {

    class DataViewHolder(
        private val onItemClicked: (currency: CurrencyModel) -> Unit,
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(currency: CurrencyModel) {
            val binding = CurrencyListItemBinding.bind(itemView)

            binding.liCurrencyName.text = currency.name
            binding.liCurrencyValue.text = String.format("%.${4}f", currency.value)
            itemView.setOnClickListener {onItemClicked(currency)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = DataViewHolder(
        onItemClicked,
        LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_list_item, parent, false)
    )

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(currencies[position])

    fun clear() {
        currencies.clear()
    }

    fun addData(currencies: List<CurrencyModel>) {
        this.currencies.addAll(currencies)
    }
}