package ru.easy.soc.hacks.hw8.database

import android.os.AsyncTask
import kotlinx.android.synthetic.main.activity_main.*
import ru.easy.soc.hacks.hw8.MainActivity

class DatabaseWorker : AsyncTask<() -> Unit, Unit, Unit>() {
    override fun doInBackground(vararg functionToExecute: (() -> Unit)?) {
        functionToExecute.forEach {
            it!!.invoke()
        }

        publishProgress()
    }

    override fun onProgressUpdate(vararg values: Unit?) {
        MainActivity.instance.recyclerView.adapter?.notifyDataSetChanged()
    }
}