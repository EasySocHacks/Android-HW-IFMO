package ru.easy.soc.hacks.hw4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.easy.soc.hacks.recycler.PhotoAdapter
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private val SAVED_BITMAP_KEY = "CurrentBitmap"
    private var currentOpenPhoto : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }

        if (currentOpenPhoto == -1) {
            showingImage.visibility = View.INVISIBLE
        } else {
            showingImage.setImageDrawable(photoList[currentOpenPhoto].regularDrawable)
            showingImage.visibility = View.VISIBLE
        }

        recyclerViewImpl = WeakReference(recyclerView)

        val viewManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = PhotoAdapter(photoList) {
                showingImage.setImageDrawable(it.regularDrawable)
                showingImage.visibility = View.VISIBLE
                currentOpenPhoto = it.id
            }
        }

        showingImage.setOnClickListener {
            it.visibility = View.INVISIBLE
            currentOpenPhoto = -1
        }

        if (photoList.isEmpty()) {
            intent = Intent(this, PhotoLoaderService :: class.java)
            startService(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_BITMAP_KEY, currentOpenPhoto)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentOpenPhoto = savedInstanceState.getInt(SAVED_BITMAP_KEY)
    }
}

