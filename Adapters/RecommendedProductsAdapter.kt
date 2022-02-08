package com.example.beautyme.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.Database.Database
import com.example.beautyme.Product.Product
import com.example.beautyme.R
import com.squareup.picasso.Picasso

class RecommendedProductsAdapter(private val list: ArrayList<Product>):
    RecyclerView.Adapter<RecommendedProductsAdapter.RecommendedProductsViewHolder>() {
    private lateinit var mListener: OnItemClickListener
    private lateinit var database: Database

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedProductsViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.other_products, parent, false)
        return RecommendedProductsViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: RecommendedProductsViewHolder, position: Int) {
        val currentItem = list[position]
        database = Database()

        Picasso.get().load(currentItem.main).fit().into(holder.imageView)
    }

    override fun getItemCount() = list.size

    class RecommendedProductsViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.other_prod_img)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
}