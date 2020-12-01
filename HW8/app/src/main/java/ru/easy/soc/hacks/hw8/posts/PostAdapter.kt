package ru.easy.soc.hacks.hw8.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easy.soc.hacks.hw8.R

class PostAdapter(private val postList: List<Post>,
                  private val onDeleteHandler: (Post) -> Unit) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false),
            onDeleteHandler)
    }

    override fun getItemCount() = postList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(postList[position])
}