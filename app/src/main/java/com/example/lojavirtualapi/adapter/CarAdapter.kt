package com.example.lojavirtualapi.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lojavirtualapi.databinding.ItemCartBinding

class CarAdapter : RecyclerView.Adapter<CarAdapter.CartViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}