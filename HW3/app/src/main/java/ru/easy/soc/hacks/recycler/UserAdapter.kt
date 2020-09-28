package ru.easy.soc.hacks.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList: List<User>,
                  private val onClickFunction: (User) -> Unit) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val holder = UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

        holder.root.setOnClickListener{
            onClickFunction(userList[holder.adapterPosition])

        }

        return holder
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(userList[position])
}