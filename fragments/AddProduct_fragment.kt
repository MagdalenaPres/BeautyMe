package com.example.beautyme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.beautyme.DatabaseURL.DatabaseURL
import com.example.beautyme.Product.Product
import com.example.beautyme.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddProduct_fragment : Fragment()  {
    private lateinit var s: Spinner
    private lateinit var chosenCategory: String
    private lateinit var prodName: TextInputEditText
    private lateinit var prodPrice: TextInputEditText
    private lateinit var prodPhoto: TextInputEditText
    private lateinit var addButton: Button
    private lateinit var database: DatabaseReference
    private var base_url = DatabaseURL.URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_addproduct, container, false)
        s = view.findViewById(R.id.add_category)

        chosenCategory = ""
        prodName = view.findViewById(R.id.add_prodname)
        prodPrice = view.findViewById(R.id.add_price)
        prodPhoto = view.findViewById(R.id.add_photo)
        addButton = view.findViewById(R.id.add_button)

        s.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    chosenCategory = parent.selectedItem.toString()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                chosenCategory = "Perfumes"
            }
        }

        addButton.setOnClickListener{
            add_product(prodName.text.toString(), prodPrice.text.toString(), prodPhoto.text.toString(), chosenCategory)
        }

        return view
    }

    private fun add_product(name:String, price:String, photo:String, category: String){
        database = FirebaseDatabase.getInstance(base_url).getReference(category)
        val time = System.currentTimeMillis()

        val product: Product =
            if(photo == ""){
                Product(time.toString(), "https://dobrarobota.org/wp-content/uploads/2017/02/default-thumbnail.jpg", name, price, null, category)
            } else{
                Product(time.toString(), photo, name, price, null, category)
            }

        database = FirebaseDatabase.getInstance(base_url).getReference(category)
        database.child(time.toString()).setValue(product)

        Toast.makeText(activity, "Product successfully added", Toast.LENGTH_SHORT).show()
    }
}