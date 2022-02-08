package com.example.beautyme.fragments

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.beautyme.R


class Interior_fragment : Fragment() {
    private var images: ArrayList<Int> = arrayListOf(R.drawable.a1, R.drawable.a2, R.drawable.a3,
                                                    R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,
                                                    R.drawable.a8,R.drawable.a9, R.drawable.a10,R.drawable.a11,R.drawable.a12,
                                                    R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,
                                                    R.drawable.a18,R.drawable.a19,R.drawable.a20,R.drawable.a21,R.drawable.a22,
                                                    R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,
                                                    R.drawable.a28,R.drawable.a29,R.drawable.a30,R.drawable.a31,R.drawable.a32,
                                                    R.drawable.a33,R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,
                                                    R.drawable.a38,R.drawable.a39,R.drawable.a40,R.drawable.a41,R.drawable.a42,
                                                    R.drawable.a43,R.drawable.a44,R.drawable.a45,R.drawable.a46,R.drawable.a47,
                                                    R.drawable.a48,R.drawable.a49,R.drawable.a50,R.drawable.a51,R.drawable.a52,
                                                    R.drawable.a53,R.drawable.a54,R.drawable.a55,R.drawable.a56,R.drawable.a57,
                                                    R.drawable.a58,R.drawable.a59,R.drawable.a60,R.drawable.a61,R.drawable.a62,
                                                    R.drawable.a63)
    private lateinit var wardrobeButton: Button
    private lateinit var bookshelfButton: Button
    private lateinit var tvButton: Button
    private lateinit var hangingButton: Button
    private lateinit var img: ImageView
    private lateinit var previ: ImageView
    private lateinit var nexti: ImageView

    @SuppressLint("ResourceType", "InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_interior, container, false)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        wardrobeButton = view.findViewById(R.id.wardrobe_button)
        bookshelfButton = view.findViewById(R.id.bookshelf_button)
        tvButton = view.findViewById(R.id.tv_button)
        hangingButton = view.findViewById(R.id.hanging_button)

        wardrobeButton.visibility = View.INVISIBLE
        bookshelfButton.visibility = View.INVISIBLE
        tvButton.visibility = View.INVISIBLE
        hangingButton.visibility = View.INVISIBLE

        img = view.findViewById(R.id.img_viewer)
        nexti = view.findViewById(R.id.next)
        previ = view.findViewById(R.id.prev)

        val curr = R.drawable.a1
        var position = 0
        img.setImageResource(curr)

        previ.setOnClickListener{
            if(position == 0){
                position = images.size - 1
                img.setImageResource(images[position])
            }
            else{
                position -= 1
                img.setImageResource(images[position])
            }
            checkPosition(position)
        }
        nexti.setOnClickListener{
            if(position == images.size - 1){
                position = 0
                img.setImageResource(images[position])
            }
            else{
                position += 1
                img.setImageResource(images[position])
            }
            checkPosition(position)
        }

        wardrobeButton.setOnClickListener{
            val popup = PopupWindow(wardrobeButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val popupView = layoutInflater.inflate(R.layout.popup_wardrobe, null)
            popup.contentView = popupView
            popup.isOutsideTouchable = true
            popup.showAsDropDown(wardrobeButton)
        }
        bookshelfButton.setOnClickListener{
            val popup = PopupWindow(bookshelfButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val popupView = layoutInflater.inflate(R.layout.popup_bookshelf, null)
            popup.contentView = popupView
            popup.isOutsideTouchable = true
            popup.showAsDropDown(bookshelfButton)
        }
        tvButton.setOnClickListener{
            val popup = PopupWindow(tvButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val popupView = layoutInflater.inflate(R.layout.popup_tv, null)
            popup.contentView = popupView
            popup.isOutsideTouchable = true
            popup.showAsDropDown(tvButton)
        }
        hangingButton.setOnClickListener{
            val popup = PopupWindow(hangingButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val popupView = layoutInflater.inflate(R.layout.popup_hanging, null)
            popup.contentView = popupView
            popup.isOutsideTouchable = true
            popup.showAsDropDown(hangingButton)
        }

        return view
    }
    private fun checkPosition(position: Int){
        if(position == 13){
            wardrobeButton.visibility = View.VISIBLE
        }
        else{
            wardrobeButton.visibility = View.INVISIBLE
        }
        if(position == 28){
            bookshelfButton.visibility = View.VISIBLE
        }
        else{
            bookshelfButton.visibility = View.INVISIBLE
        }
        if(position == 48){
            tvButton.visibility = View.VISIBLE
        }
        else{
            tvButton.visibility = View.INVISIBLE
        }
        if(position == 55){
            hangingButton.visibility = View.VISIBLE
        }
        else{
            hangingButton.visibility = View.INVISIBLE
        }
    }
}


