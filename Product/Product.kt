package com.example.beautyme.Product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(var id: String, var main: String, var name: String,
                   var price: String, var description: String?, var group: String): Parcelable
