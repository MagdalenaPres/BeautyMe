package com.example.beautyme.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.Adapters.NewProductsAdapter
import com.example.beautyme.DatabaseURL.DatabaseURL
import com.example.beautyme.Product.Product
import com.example.beautyme.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class Home_fragment : Fragment()
{
    private lateinit var databaseRef: DatabaseReference

    private lateinit var buttonPerfumes: ImageView
    private lateinit var buttonMakeup : ImageView
    private lateinit var buttonBody : ImageView
    private lateinit var buttonAd : ImageButton
    private lateinit var newProducts: ArrayList<Product>

    private lateinit var rvNew: RecyclerView
    private val base_url = DatabaseURL.URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        newProducts = arrayListOf()

        rvNew = view.findViewById(R.id.rv_new)
        rvNew.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvNew.setHasFixedSize(true)

        buttonPerfumes = view.findViewById(R.id.imageButton_perfumes)
        buttonMakeup = view.findViewById(R.id.imageButton_makeup)
        buttonBody = view.findViewById(R.id.imageButton_body)
        buttonAd = view.findViewById(R.id.imageBtn)

        buttonPerfumes.setOnClickListener{
            val perfume_fr = Perfumes_fragment()

            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container, perfume_fr).commit()
        }
        buttonMakeup.setOnClickListener{
            val makeup_fr = Makeup_fragment()

            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container, makeup_fr).commit()
        }
        buttonBody.setOnClickListener{
            val body_fr = Body_fragment()

            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container, body_fr).commit()
        }
        buttonAd.setOnClickListener{
            val interior_fr = Interior_fragment()
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container, interior_fr).commit()
        }

        connectWithDatabase()
        getNewProducts(newProducts, rvNew)

        return view
    }

    private fun connectWithDatabase() {
        databaseRef = FirebaseDatabase.getInstance(base_url).getReference("menu")

        databaseRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val photo1 = snapshot.child("photoBody").value.toString()
                    val photo2 = snapshot.child("photoMakeup").value.toString()
                    val photo3 = snapshot.child("photoPerfumes").value.toString()

                    Picasso.get().load(photo1).fit().into(buttonBody)
                    Picasso.get().load(photo2).fit().into(buttonMakeup)
                    Picasso.get().load(photo3).fit().into(buttonPerfumes)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun getNewProducts(products: ArrayList<Product>, recyclerView: RecyclerView){
        databaseRef = FirebaseDatabase.getInstance(base_url).getReference("new")

        databaseRef.addValueEventListener(object: ValueEventListener {
            var adapter = NewProductsAdapter(products)

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

                    adapter.setOnItemClickListener(object: NewProductsAdapter.OnItemClickListener{
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