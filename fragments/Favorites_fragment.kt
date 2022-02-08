package com.example.beautyme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.Adapters.RecommendedProductsAdapter
import com.example.beautyme.Database.Database
import com.example.beautyme.DatabaseURL.DatabaseURL
import com.example.beautyme.Product.Product
import com.example.beautyme.R
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class Favorites_fragment : Fragment() {

    private lateinit var database: Database
    private lateinit var databaseRef: DatabaseReference
    private lateinit var products: ArrayList<Product>
    private lateinit var otherProducts: ArrayList<Product>
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewOtherProd: RecyclerView
    private val base_url = DatabaseURL.URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        products = arrayListOf()
        otherProducts = arrayListOf()
        database = Database()

        recyclerView = view.findViewById(R.id.favorite_items)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        recyclerView.setHasFixedSize(true)

        database.getItemsFromFavorites(products, recyclerView)

        recyclerViewOtherProd = view.findViewById(R.id.other_items)
        recyclerViewOtherProd.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewOtherProd.setHasFixedSize(true)

        getRecommendedProducts(otherProducts, recyclerViewOtherProd)

        return view
    }

    private fun getRecommendedProducts(products: ArrayList<Product>, recyclerView: RecyclerView)
    {
        val adapter = RecommendedProductsAdapter(products)
        databaseRef = FirebaseDatabase.getInstance(base_url).getReference("recommended")

        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var shouldRefresh = false
                if(snapshot.exists())
                {
                    for(ProductSnapshot in snapshot.children)
                    {
                        val id = ProductSnapshot.child("id").value.toString()
                        val name = ProductSnapshot.child("name").value.toString()
                        val price = ProductSnapshot.child("price").value.toString()
                        val img = ProductSnapshot.child("main").value.toString()
                        val description = ProductSnapshot.child("description").value.toString()
                        val group = ProductSnapshot.child("group").value.toString()

                        val item = Product(id, img, name, price, description, group)
                        if(!products.contains(item)){
                            products.add(item)
                            shouldRefresh = true
                        }
                        if(shouldRefresh)
                        {
                            recyclerView.adapter = adapter
                        }
                    }
                    recyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object: RecommendedProductsAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val clickedItem: Product = products[position]
                            val prodDetailsFr = ProductDetails_fragment()
                            val bundle = Bundle()

                            bundle.putParcelable("prodInfo", clickedItem)
                            prodDetailsFr.arguments = bundle

                            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, prodDetailsFr)?.commit()
                            recyclerView.adapter?.notifyItemChanged(position)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}