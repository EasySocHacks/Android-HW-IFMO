package ru.easy.soc.hacks.recycler

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

const val requiredPermission = Manifest.permission.READ_CONTACTS
const val requestReadContactsPermissionID = 0
const val requestPhoneCallPermissionID = 1

fun Context.fetchAllContacts(): List<User> {
    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        .use { cursor ->
            if (cursor == null) return emptyList()
            val builder = ArrayList<User>()
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) ?: "N/A"
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ?: "N/A"

                builder.add(User(name, phoneNumber))
            }
            return builder
        }
}

class ContactsLoader(private val mainActivity: MainActivity) {
    fun tryLoadContacts() {
        if (ContextCompat.checkSelfPermission(
                mainActivity, requiredPermission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                mainActivity,
                arrayOf(requiredPermission),
                requestReadContactsPermissionID)
        } else {
            val fetchedUsers = mainActivity.applicationContext.fetchAllContacts()

            userList.clear()

            fetchedUsers.map {
                userList.add(it)
            }

            eventHandler.hideButton()

            mainActivity.recyclerView.adapter?.notifyDataSetChanged()

            Toast.makeText(
                mainActivity,
                mainActivity.resources.getQuantityString(R.plurals.contactsFoundPlural,
                    fetchedUsers.size, fetchedUsers.size),
                Toast.LENGTH_SHORT).show()
        }
    }
}