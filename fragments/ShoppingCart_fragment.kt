package com.example.beautyme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.*
import com.example.beautyme.Database.Database
import com.example.beautyme.Product.Product

class ShoppingCart_fragment : Fragment() {

    private lateinit var database: Database
    private lateinit var products: ArrayList<Product>
    private lateinit var recyclerView: RecyclerView
    private lateinit var sumOfProducts: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        products = arrayListOf()
        database = Database()

        recyclerView = view.findViewById(R.id.cart_items)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        recyclerView.setHasFixedSize(true)

        sumOfProducts = view.findViewById(R.id.cart_cost)

        database.getItemsFromCart(products, recyclerView)
        database.countTotalCartCost(sumOfProducts)

        return view
    }
}


