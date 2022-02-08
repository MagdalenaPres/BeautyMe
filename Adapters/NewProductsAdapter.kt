package com.example.beautyme.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.Product.Product
import com.example.beautyme.R
import com.squareup.picasso.Picasso

class NewProductsAdapter(private val list: ArrayList<Product>): RecyclerView.Adapter<NewProductsAdapter.NewProductsViewHolder>()
{
    private lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProductsViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.new_product, parent, false)
        return NewProductsViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: NewProductsViewHolder, position: Int) {
        val currentItem = list[position]

        Picasso.get().load(currentItem.main).fit().into(holder.imageView)
        holder.nameView.text = currentItem.name
    }

    override fun getItemCount() = list.size

    class NewProductsViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.product_image)
        val nameView: TextView = itemView.findViewById(R.id.product_name)

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