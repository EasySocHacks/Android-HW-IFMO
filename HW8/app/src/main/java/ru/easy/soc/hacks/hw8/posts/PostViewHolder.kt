package ru.easy.soc.hacks.hw8.posts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class PostViewHolder(private val root : View,
                     private val onDeleteHandler: (Post) -> Unit) : RecyclerView.ViewHolder(root) {
    fun bind(post : Post) {
        with(root) {
            title.text = post.title
            body.text = post.body
            deletePostButton.setOnClickListener {
                onDeleteHandler(post)
            }
        }
    }
}