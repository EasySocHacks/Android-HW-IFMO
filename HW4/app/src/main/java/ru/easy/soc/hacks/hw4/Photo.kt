package ru.easy.soc.hacks.hw4

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

data class Photo(val thumbDrawable : Drawable, val rawDrawable : Drawable, val description : String)

var photoList = ArrayList<Photo>()
var recyclerViewImpl: RecyclerView? = null