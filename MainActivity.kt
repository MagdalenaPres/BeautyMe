package com.example.beautyme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.example.beautyme.fragments.Account_fragment
import com.example.beautyme.fragments.Favorites_fragment
import com.example.beautyme.fragments.Home_fragment
import com.example.beautyme.fragments.ShoppingCart_fragment
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
    private val homeFragment = Home_fragment()
    private val favoritesFragment = Favorites_fragment()
    private val accountFragment = Account_fragment()
    private val shoppingCartFragment = ShoppingCart_fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        replaceFragment(homeFragment)

        this.bottom_nav.setOnNavigationItemSelectedListener{
        when(it.itemId){
                R.id.ic_baseline_home_24 -> replaceFragment(homeFragment)
                R.id.ic_baseline_shopping_cart_24 -> replaceFragment(shoppingCartFragment)
                R.id.ic_baseline_favourite_24 -> replaceFragment(favoritesFragment)
                R.id.ic_baseline_perm_identity_24 -> replaceFragment(accountFragment)
        }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}