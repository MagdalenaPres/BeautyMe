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

class FavoriteProductsAdapter(private val list: ArrayList<Product>):
        RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteProductsViewHolder>()
{
    private lateinit var database: Database

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductsViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_in_favorites, parent, false)
        return FavoriteProductsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteProductsViewHolder, position: Int) {
        val currentItem = list[position]
        database = Database()

        Picasso.get().load(currentItem.main).fit().into(holder.imageView)
        holder.nameView.text = currentItem.name

        holder.closeSign.setOnClickListener {
            list.removeAt(holder.bindingAdapterPosition)
            notifyItemRemoved(holder.bindingAdapterPosition)
            database.removeFromFav(currentItem)
        }
    }

    override fun getItemCount() = list.size

    class FavoriteProductsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val closeSign: ImageView = itemView.findViewById(R.id.close_sign_fav)
        val imageView: ImageView = itemView.findViewById(R.id.item_img_fav)
        val nameView: TextView = itemView.findViewById(R.id.item_name_fav)

    }

}