package com.example.lojavirtualapi

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lojavirtualapi.databinding.ActivityCartDetalhesBinding
import com.example.lojavirtualapi.model.Cart
import kotlin.io.root

class CartDetalhes : AppCompatActivity() {
    private lateinit var binding: ActivityCartDetalhesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCartDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cartRecebido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("cart", Cart::class.java)
        } else {
            intent.getParcelableExtra<Cart>("cart")
        }

        if (cartRecebido != null) {

            val detalhesFormatados = formatarListaProdutos(cartRecebido)


            binding.txtDetalhesProdutos.text = detalhesFormatados


            supportActionBar?.title = "Detalhes do Carrinho #${cartRecebido.id}"
        }
    }

    private fun formatarListaProdutos(cart: Cart): String {


    }
}