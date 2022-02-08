package com.example.beautyme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.Product.Product
import com.example.beautyme.Adapters.ProductAdapter
import com.example.beautyme.DatabaseURL.DatabaseURL
import com.example.beautyme.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class Perfumes_fragment : Fragment() {

    private lateinit var top_photo: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var list: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_perfumes, container, false)
        recyclerView = view.findViewById(R.id.rv_perfumes)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.setHasFixedSize(true)

        top_photo = view.findViewById(R.id.image_top_perfumes)

        addTopPhoto()
        list = arrayListOf()
        createProductList()
        return view
    }

    private fun addTopPhoto()
    {
        database = FirebaseDatabase.getInstance(DatabaseURL.URL).getReference("categoryTopImg")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    val photo = snapshot.child("perfumes").value.toString()
                    Picasso.get().load(photo).fit().into(top_photo)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun createProductList()
    {
        database = FirebaseDatabase.getInstance(DatabaseURL.URL).getReference("perfumes")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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
                        list.add(item)
                    }
                    var adapter = ProductAdapter(list)
                    recyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object: ProductAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val clickedItem: Product = list[position]

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