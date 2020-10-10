package ru.easy.soc.hacks.hw4

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ru.easy.soc.hacks.recycler.PhotoAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showingImage.visibility = View.INVISIBLE

        recyclerViewImpl = recyclerView

        val viewManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = PhotoAdapter(photoList) {
                showingImage.setImageDrawable(it.rawDrawable)
                showingImage.visibility = View.VISIBLE
            }
        }

        showingImage.setOnClickListener {
            it.visibility = View.INVISIBLE
        }

        if (photoList.isEmpty()) {
            val intent = Intent(this, PhotoLoaderService :: class.java)
            startService(intent)
        }
    }
}

