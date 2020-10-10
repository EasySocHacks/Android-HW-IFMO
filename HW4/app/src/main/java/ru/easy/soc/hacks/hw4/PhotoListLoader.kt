package ru.easy.soc.hacks.hw4

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class PhotoLoaderService() : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        PhotoListLoader().execute("https://api.unsplash.com/photos/random?count=50&client_id=_-bRC2rJB_5K5Sj0PRh3BCa3oqfSL98Xi5AbboHUicg")

        startForeground(0,
            NotificationCompat.Builder(this, Context.ACTIVITY_SERVICE).
            setContentTitle("Content Title").
            setContentText("Content Text").
            build()
        )

        return START_REDELIVER_INTENT
    }
}

class PhotoListLoader(): AsyncTask<String, Unit, Unit>() {
    override fun doInBackground(vararg urlString: String?): Unit {
        val url = URL(urlString[0])

        val bufferedReader = BufferedReader(InputStreamReader(url.openConnection().getInputStream()))

        val g = JsonParser().parse(bufferedReader).asJsonArray

        for (i in 0 until g.size()) {
            var description = ""
            if (!g[i].asJsonObject["alt_description"].isJsonNull) {
                description = g[i].asJsonObject["alt_description"].asString
            }

            if (!g[i].asJsonObject["description"].isJsonNull) {
                description = g[i].asJsonObject["description"].asString
            }

            var thumbImageUrl = URL(g[i].asJsonObject["urls"].asJsonObject["thumb"].asString)
            val rawImageUrl = URL(g[i].asJsonObject["urls"].asJsonObject["regular"].asString)

            val thumbBitmap = BitmapFactory.decodeStream(thumbImageUrl.openConnection().getInputStream())
            val rawBitmap = BitmapFactory.decodeStream(rawImageUrl.openConnection().getInputStream())

            photoList.add(Photo(BitmapDrawable(thumbBitmap), BitmapDrawable(rawBitmap), description))

            publishProgress()
        }
    }

    override fun onProgressUpdate(vararg values: Unit?) {
        recyclerViewImpl?.adapter?.notifyDataSetChanged()
    }
}