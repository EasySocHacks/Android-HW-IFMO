package ru.easy.soc.hacks.hw4

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class PhotoLoaderService() : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("ChannelID",
                    "Foreground notification",
                    NotificationManager.IMPORTANCE_DEFAULT)
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).
            createNotificationChannel(notificationChannel)
        }

        val intent1 = Intent(this, MainActivity().javaClass)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent1,  0)

        val notification = NotificationCompat.Builder(this, "ChannelID")
            .setContentTitle("An unsplash.com photo list")
            .setContentText("downloading photos")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent).build()

        startForeground(1, notification)

        PhotoListLoader().execute(
            "https://api.unsplash.com/photos/random?count=30&client_id=_-bRC2rJB_5K5Sj0PRh3BCa3oqfSL98Xi5AbboHUicg")

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        stopForeground(true)
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
            val regularImageUrl = URL(g[i].asJsonObject["urls"].asJsonObject["regular"].asString)

            val thumbBitmap = BitmapFactory.decodeStream(thumbImageUrl.openConnection().getInputStream())
            val regularBitmap = BitmapFactory.decodeStream(regularImageUrl.openConnection().getInputStream())

            photoList.add(Photo(i, BitmapDrawable(thumbBitmap), BitmapDrawable(regularBitmap), description))

            publishProgress()
        }
    }

    override fun onProgressUpdate(vararg values: Unit?) {
        recyclerViewImpl?.get()?.adapter?.notifyDataSetChanged()
    }
}