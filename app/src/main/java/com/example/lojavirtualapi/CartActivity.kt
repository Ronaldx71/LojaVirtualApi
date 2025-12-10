package com.example.lojavirtualapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lojavirtualapi.adapter.CarAdapter
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.databinding.ActivityCartBinding
import com.example.lojavirtualapi.model.RespostaCart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }

    private val TAG = "Info_Cart"
    private lateinit var cartAdapter: CarAdapter

    private val api by lazy {
        RetrofitInstance.api
    }

    var jobCart: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Carrinho"

        inicializacao()

        recuperarCarts()

    }

    private fun recuperarCarts() {

        jobCart = CoroutineScope(Dispatchers.IO).launch {
            var resposta: Response<RespostaCart>? = null

            try {
                resposta = RetrofitInstance.api.recuperaCarts()

            } catch (e: Exception) {
                e.printStackTrace()
                exibirMensagem("Erro ao realizar a requisição, verifique o acesso a internet")
                return@launch

            }
            if (resposta != null) {
                if (resposta.isSuccessful) {

   
                    val respostaCart = resposta.body()

                    if (respostaCart != null) {
                        val listaCarts = respostaCart.carts
                        withContext(Dispatchers.Main) {
                            cartAdapter.atualizarLista(listaCarts)
                            Log.i(
                                TAG,
                                "Dados carregados com sucesso: ${listaCarts.size} carrinhos."
                            )
                        }
                    }
                } else {

                    val codigoErro = resposta.code()
                    Log.e(TAG, "Erro na resposta da API: $codigoErro")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@CartActivity,
                            "Erro ao carregar dados: $codigoErro",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun exibirMensagem(mensagem: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                applicationContext,
                mensagem,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun inicializacao() {
        //Adapter
        cartAdapter = CarAdapter() { cart ->
            val intent = Intent(this, CartDetalhes::class.java)
            intent.putExtra("cart", cart)
            startActivity(intent)
        }

        //Recycler
        binding.recyclerCart.adapter = cartAdapter
        binding.recyclerCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerCart.setHasFixedSize(true)
        binding.recyclerCart.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerCart.context,
                LinearLayoutManager.VERTICAL
            )
        )
    }


    override fun onStart() {
        super.onStart()
        recuperarCarts()
    }

    override fun onStop() {
        super.onStop()
        jobCart?.cancel()
    }

}