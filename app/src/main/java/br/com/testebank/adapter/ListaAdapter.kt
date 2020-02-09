package br.com.testebank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.testebank.databinding.CardViewListaBinding
import br.com.testebank.model.ItemLista
import com.squareup.picasso.Picasso


class ListaAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ItemLista?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardViewListaBinding.inflate(inflater, parent, false)
        return CardViewListaViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position]!!.id.toInt()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CardViewListaViewHolder) {
            holder.bind(items[position])
        }
    }

    fun setData(lista: List<ItemLista?>) {
        items = lista
        notifyDataSetChanged()
    }



    inner class CardViewListaViewHolder(private val binding: CardViewListaBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: ItemLista?) {

            binding.tvNomeRepositorio.text = "Repositório: ${if (!item?.nomeRepositorio.isNullOrEmpty()) item?.nomeRepositorio else ""}"
            binding.tvEstrela.text = "Qtde Estrelas: ${item?.stargazersCount.toString()}"
            binding.tvFork.text = "Qtde Forks: ${item?.forksCount.toString()}"
            binding.tvNomeAutor.text = "Nome do Autor: ${if (!item?.owner!!.login.isNullOrEmpty()) item?.owner!!.login else ""}"
            Picasso.get().load(item?.owner!!.foto).into(binding.ivIcone)

        }

        override fun onClick(p0: View?) {
        }

    }
}