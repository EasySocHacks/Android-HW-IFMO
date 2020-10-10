package ru.easy.soc.hacks.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easy.soc.hacks.hw4.Photo
import ru.easy.soc.hacks.hw4.R

class PhotoAdapter(private val photoList: List<Photo>,
                   private val onClickFunction: (Photo) -> Unit) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val holder = PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

        holder.root.setOnClickListener{
            onClickFunction(photoList[holder.adapterPosition])

        }

        return holder
    }

    override fun getItemCount() = photoList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) =
        holder.bind(photoList[position])
}