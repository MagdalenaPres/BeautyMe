package com.example.beautyme.Database
import android.annotation.SuppressLint
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.Adapters.CartProductAdapter
import com.example.beautyme.Adapters.FavoriteProductsAdapter
import com.example.beautyme.Product.Product
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import kotlin.collections.ArrayList
import com.example.beautyme.DatabaseURL.DatabaseURL

class Database
{
    private val base_url = DatabaseURL.URL
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference

    fun getItemsFromCart(products: ArrayList<Product>, recyclerView: RecyclerView){
        database = FirebaseDatabase.getInstance(base_url).getReference("cart")

        database.addValueEventListener(object: ValueEventListener {
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
                            recyclerView.adapter = CartProductAdapter(products)
                        }
                    }
                    recyclerView.adapter = CartProductAdapter(products)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun addItemToCart(product: Product){
        val time = System.currentTimeMillis()
        product.id = time.toString()
        database = FirebaseDatabase.getInstance(base_url).getReference("cart")
        database.child(time.toString()).setValue(product)
    }

    fun removeFromCart(product: Product){
        database = FirebaseDatabase.getInstance(base_url).getReference("cart")
        database.child(product.id).removeValue()
    }

    fun countTotalCartCost(textView: TextView){
        database = FirebaseDatabase.getInstance(base_url).getReference("cart")

        database.addValueEventListener(object: ValueEventListener {

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                var totalCost = 0.0
                if(snapshot.exists())
                {
                    for(ProductSnapshot in snapshot.children)
                    {
                        val price = ProductSnapshot.child("price").value.toString()
                        totalCost += price.toDouble()
                    }
                }
                textView.text = "$totalCost zł"
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun getItemsFromFavorites(products: ArrayList<Product>, recyclerView: RecyclerView){
        database = FirebaseDatabase.getInstance(base_url).getReference("favorites")

        database.addValueEventListener(object: ValueEventListener {
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
                            recyclerView.adapter = FavoriteProductsAdapter(products)
                        }
                    }
                    recyclerView.adapter = FavoriteProductsAdapter(products)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun addItemToFav(product: Product){
        val time = System.currentTimeMillis()
        product.id = time.toString()
        database = FirebaseDatabase.getInstance(base_url).getReference("favorites")
        database.child(time.toString()).setValue(product)
    }

    fun removeFromFav(product: Product){
        database = FirebaseDatabase.getInstance(base_url).getReference("favorites")
        database.child(product.id).removeValue()
    }

}
