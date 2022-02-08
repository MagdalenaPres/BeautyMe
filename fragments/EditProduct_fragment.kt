package com.example.beautyme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.beautyme.DatabaseURL.DatabaseURL
import com.example.beautyme.Product.Product
import com.example.beautyme.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProduct_fragment : Fragment()  {
    private lateinit var group: String
    private lateinit var id: String
    private lateinit var prodName: TextInputEditText
    private lateinit var prodPrice: TextInputEditText
    private lateinit var prodPhoto: TextInputEditText
    private lateinit var prodDescription: TextInputEditText
    private lateinit var bundle: Bundle
    private lateinit var editButton: Button
    private lateinit var receivedProd: Product
    private lateinit var database: DatabaseReference
    private var base_url = DatabaseURL.URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_editproduct, container, false)

        prodDescription = view.findViewById(R.id.edit_description)
        prodName = view.findViewById(R.id.edit_prodname)
        prodPrice = view.findViewById(R.id.edit_price)
        prodPhoto = view.findViewById(R.id.edit_photo)
        editButton = view.findViewById(R.id.edit_button)
        bundle = this.arguments!!
        receivedProd = bundle.getParcelable("prodEdit")!!
        group =""
        id =""

        group = receivedProd.group
        id = receivedProd.id
        prodName.setText(receivedProd.name)
        prodPhoto.setText(receivedProd.main)
        prodPrice.setText(receivedProd.price)
        prodDescription.setText(receivedProd.description)

        editButton.setOnClickListener{
            edit_product(prodName.text.toString(), prodPrice.text.toString(), prodPhoto.text.toString(),
                group, prodDescription.text.toString())
        }
        return view
    }

    private fun edit_product(name:String, price:String, photo:String, category: String, description: String){
        database = FirebaseDatabase.getInstance(base_url).getReference(category).child(receivedProd.id)
        val updates: MutableMap<String, Any> = HashMap()

        updates["name"] = name
        updates["price"] = price
        updates["main"] = photo
        updates["description"] = description
        database.updateChildren(updates)

        Toast.makeText(activity, "Product edited successfully", Toast.LENGTH_SHORT).show()

        val home_fr = Home_fragment()
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, home_fr)?.commit()
    }
}