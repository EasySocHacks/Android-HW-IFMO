package ru.easy.soc.hacks.recycler

import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class EventHandler {
    private val mainActivity: MainActivity

    constructor(mainActivity: MainActivity, contactsLoader: ContactsLoader) {
        this.mainActivity = mainActivity

        mainActivity.addContactsButton.setOnClickListener {
            contactsLoader.tryLoadContacts()
        }
    }

    fun hideButton() {
        mainActivity.addContactsButton.visibility = View.INVISIBLE
    }
}