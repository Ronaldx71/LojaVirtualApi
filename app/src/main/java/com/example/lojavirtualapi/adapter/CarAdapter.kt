package com.example.lojavirtualapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lojavirtualapi.databinding.ItemCartBinding
import com.example.lojavirtualapi.model.Cart



class CarAdapter (val onClick: (Cart) -> Unit) : RecyclerView.Adapter<CarAdapter.CartViewHolder>(){

    private var listaCart = mutableListOf<Cart>()

    fun atualizarLista(lista: List<Cart>) {
        listaCart.clear()
        listaCart.addAll(lista)
        notifyDataSetChanged()
    }

    fun adicionarLista(novosItens: List<Cart>) {
        val posicaoInicial = listaCart.size
        listaCart.addAll(novosItens)
        // Mais eficiente para o RecyclerView
        notifyItemRangeInserted(posicaoInicial, novosItens.size)
    }

    fun limparLista() {
        listaCart.clear()
        notifyDataSetChanged()
    }

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cart) {

            binding.txtId.text = "ID: ${item.id}"
            binding.txtTitle.text = "Total Produtos: ${item.totalProducts}"
            /*Glide.with(binding.imgCart.context)
                .load(R.drawable.cart_imagem)
                .into(binding.imgCart)*/
            //binding.imgCart.setImageResource(R.drawable.cart_imagem)

            binding.consLay.setOnClickListener {
                onClick(item)
            }


        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemViewCart = ItemCartBinding.inflate(layoutInflater, parent, false)

        return CartViewHolder(itemViewCart)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        var item = listaCart[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listaCart.size

    }
}