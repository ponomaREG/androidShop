package com.ponomar.shoper.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.ItemOrderBinding
import com.ponomar.shoper.model.entities.Order

class OrderAdapter(private val onOrderClick:(Order) -> Unit):RecyclerView.Adapter<OrderAdapter.VHolder>() {
    private val orders:ArrayList<Order> = arrayListOf()

    class VHolder(val binding:ItemOrderBinding):RecyclerView.ViewHolder(binding.root)

    fun addItems(newOrders:List<Order>){
        orders.clear()//TODO:FIX AND IN CART ADAPTER TOO
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }

    fun clearItems(){
        orders.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemOrderBinding = DataBindingUtil.inflate(inflater, R.layout.item_order,parent,false)
        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        Log.e("orders",orders.toString())
        val orderItem = orders[position]
        holder.binding.apply {
            order = orderItem
            root.setOnClickListener {
                onOrderClick(orderItem)
            }
        }
    }

    override fun getItemCount() = orders.size
}