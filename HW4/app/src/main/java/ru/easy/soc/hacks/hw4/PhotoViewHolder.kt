package ru.easy.soc.hacks.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import ru.easy.soc.hacks.hw4.Photo
import ru.easy.soc.hacks.hw4.R

class PhotoViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    fun bind(photo: Photo) {
        with(root) {
            image.setImageDrawable(photo.thumbDrawable)
            description.text = photo.description
        }
    }
}