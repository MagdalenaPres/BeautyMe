package com.example.beautyme.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beautyme.R
import com.squareup.picasso.Picasso
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.beautyme.Database.Database
import com.example.beautyme.DatabaseURL.DatabaseURL
import com.example.beautyme.Product.Product
import com.google.firebase.database.*

class ProductDetails_fragment : Fragment() {

    private lateinit var database: Database
    private lateinit var databaseRef: DatabaseReference
    private lateinit var group: String
    private lateinit var id: String
    private lateinit var prodName: TextView
    private lateinit var mainImg: ImageView
    private lateinit var priceTxt: TextView
    private lateinit var description: TextView
    private lateinit var receivedProd: Product
    private lateinit var bundle: Bundle
    private var base_url = DatabaseURL.URL

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)

        group =""
        id =""
        prodName = view.findViewById(R.id.name_txt)
        mainImg = view.findViewById(R.id.main_img)
        priceTxt = view.findViewById(R.id.price_txt)
        description = view.findViewById(R.id.description_txt)

        val cartButton = view.findViewById<Button>(R.id.cart_button)
        val favButton = view.findViewById<Button>(R.id.favorites_button)
        val backButton = view.findViewById<ImageView>(R.id.back_sign)
        val menuButton = view.findViewById<ImageView>(R.id.menu_sign)
        bundle = this.arguments!!

        receivedProd = bundle.getParcelable("prodInfo")!!

        group = receivedProd.group
        id = receivedProd.id
        prodName.text = receivedProd.name
        val img = receivedProd.main
        Picasso.get().load(img).fit().into(mainImg)
        priceTxt.text = receivedProd.price + " zł"
        description.text = receivedProd.description.toString()

        description.movementMethod = ScrollingMovementMethod()
        database = Database()

        backButton.setOnClickListener{
            val perfume_fr = Perfumes_fragment()
            val makeup_fr = Makeup_fragment()
            val body_fr = Body_fragment()

            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            if(group=="perfumes"){
                transaction.replace(R.id.fragment_container, perfume_fr).commit()
            }
            else if(group=="makeup"){
                transaction.replace(R.id.fragment_container, makeup_fr).commit()
            }
            else if(group=="body"){
                transaction.replace(R.id.fragment_container, body_fr).commit()
            }
        }
        menuButton.setOnClickListener {
            showPopup(menuButton)
        }

        cartButton.setOnClickListener{
            database.addItemToCart(receivedProd)
        }
        favButton.setOnClickListener{
            database.addItemToFav(receivedProd)
        }

        return view
    }
    private fun showPopup(view:View){
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.menu_manageprod)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.edit_product -> {
                    editProduct()
                }
                R.id.delete_product -> {
                    deleteProduct()
                    Toast.makeText(activity, "Product deleted", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popupMenu.show()
    }

    private fun deleteProduct(){
        val home_fr = Home_fragment()

        databaseRef = FirebaseDatabase.getInstance(base_url).getReference(receivedProd.group)
        databaseRef.child(receivedProd.id).removeValue()
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, home_fr)?.commit()
    }

    private fun editProduct(){
        val edit_fr = EditProduct_fragment()
        val bundle = Bundle()

        bundle.putParcelable("prodEdit", receivedProd)
        edit_fr.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, edit_fr)?.commit()
    }
}