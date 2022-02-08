package com.example.beautyme.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.Database.Database
import com.example.beautyme.Product.Product
import com.example.beautyme.R
import com.squareup.picasso.Picasso

class CartProductAdapter(private val list: ArrayList<Product>): RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>()
{
    private lateinit var database: Database

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_in_cart, parent, false)
        return CartProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val currentItem = list[position]
        database = Database()

        Picasso.get().load(currentItem.main).fit().into(holder.imageView)
        holder.nameView.text = currentItem.name
        holder.priceView.text = currentItem.price

        holder.closeSign.setOnClickListener {
            list.removeAt(holder.bindingAdapterPosition)
            notifyItemRemoved(holder.bindingAdapterPosition)
            database.removeFromCart(currentItem)
        }
    }

    override fun getItemCount() = list.size

    class CartProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val closeSign: ImageView = itemView.findViewById(R.id.close_sign_cart)
        val imageView: ImageView = itemView.findViewById(R.id.item_img_cart)
        val nameView: TextView = itemView.findViewById(R.id.item_name_cart)
        val priceView: TextView = itemView.findViewById(R.id.item_price_cart)
    }

}