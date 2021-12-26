package com.example.flickerapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickerapp.databinding.ItemRowBinding

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView


class RVAdapter(
    private val Context: Context,
    private val container: ArrayList<Photo>
//    var onItemClick: ((Photo) -> Unit)? = null
) :
    RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        view = parent.rootView

        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cards = container[position]
        holder.binding.apply {
            tvTitle.text = cards.title
            Glide.with(Context).load(cards.url_h).into(ivResult)

            holder.itemView.setOnClickListener {
                view?.findViewById<CardView>(R.id.cvDetails)?.visibility = View.VISIBLE
                view?.findViewById<TextView>(R.id.tvDetails)?.text =
                    cards.title + " , owner:" + cards.owner
                val img = view?.findViewById<ImageView>(R.id.ivDetails)
                img?.let { it1 -> Glide.with(Context).load(cards.url_h).into(it1) }

                println(position)
            }
        }
    }

    override fun getItemCount(): Int = container.size
}
