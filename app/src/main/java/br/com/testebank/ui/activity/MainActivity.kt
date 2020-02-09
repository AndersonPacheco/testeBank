package br.com.testebank.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.testebank.R
import br.com.testebank.adapter.ListaAdapter
import br.com.testebank.databinding.ActivityMainBinding
import br.com.testebank.model.ListaRepositorio
import br.com.testebank.ui.viewmodel.ListaViewModel
import androidx.lifecycle.ViewModelProviders
import br.com.testebank.model.ItemLista
import br.com.testebank.model.ListaRepositorioResposta
import br.com.testebank.util.ResultCode

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ListaAdapter
    private var items: List<ItemLista?> = ArrayList()

    private val listaViewModel: ListaViewModel by lazy {
        ViewModelProviders.of(this).get(ListaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.rvPrinc.setHasFixedSize(true)
        binding.rvPrinc.layoutManager = LinearLayoutManager(this)
        adapter = ListaAdapter(this)
        binding.rvPrinc.adapter = adapter

        if (isOnline()) {
            addObservable()
        }else{
            val builder = android.app.AlertDialog.Builder(this)
            builder.setMessage("Verifique sua conexão!")
            builder.setTitle("Atenção")
            builder.setPositiveButton("OK", null)
            builder.show()
        }
        addObservableWithOnCreate()
    }

    fun isOnline(): Boolean {
        var connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo = connMgr.getActiveNetworkInfo()
        return (networkInfo != null && networkInfo.isConnected())
    }

    private fun carregar(isCarregar: Boolean){
        if(isCarregar && binding.pbLoading.visibility == View.VISIBLE){
            return
        }

        binding.pbLoading.visibility = if(isCarregar) View.VISIBLE else View.GONE
    }

    private fun addObservable() {
        carregar(true)
        listaViewModel.getDados(1)

    }

    private fun addObservableWithOnCreate(){

        listaViewModel.getListaObservable().observe(this, Observer<ListaRepositorioResposta<ListaRepositorio>> { listaResponse ->

            if(listaResponse != null) {
                when(listaResponse.resultCode) {
                    ResultCode.SUCESS.value -> {
                        carregar(false)
                        items = listaResponse.data!!.items

                        adapter.setData(listaResponse.data!!.items)

                    }
                    ResultCode.NOTFOUND.value -> {
                        carregar(false)
                    }

                    ResultCode.UNAUTHORIZED.value -> {
                        carregar(false)
                    }
                }
            }
        })
    }
}
