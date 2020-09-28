package ru.easy.soc.hacks.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class UserViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    fun bind(user: User) {
        with(root) {
            name.text = user.name
            phoneNumber.text = user.phoneNumber
        }
    }
}