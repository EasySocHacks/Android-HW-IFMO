package ru.easy.soc.hacks.hw4

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

data class Photo(val id : Int, val thumbDrawable : Drawable, val regularDrawable : Drawable, val description : String)

var photoList = ArrayList<Photo>()
var recyclerViewImpl: RecyclerView? = null