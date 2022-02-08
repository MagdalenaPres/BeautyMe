package com.example.beautyme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import com.example.beautyme.DatabaseURL.DatabaseURL
import com.example.beautyme.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class Account_fragment : Fragment() {
    private lateinit var top_photo: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var add_product: Button
    private var base_url = DatabaseURL.URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        top_photo = view.findViewById(R.id.image_top_account)
        add_product = view.findViewById(R.id.addprod_button)

        add_product.setOnClickListener{
            val addproduct_fr = AddProduct_fragment()

            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container, addproduct_fr).commit()
        }

        addTopPhoto()
        return view
    }

    private fun addTopPhoto()
    {
        database = FirebaseDatabase.getInstance(base_url).getReference("categoryTopImg")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    val photo = snapshot.child("account").value.toString()
                    Picasso.get().load(photo).fit().into(top_photo)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}