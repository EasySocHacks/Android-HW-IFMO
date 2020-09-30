package ru.easy.soc.hacks.recycler

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

lateinit var eventHandler: EventHandler
lateinit var contactsLoader: ContactsLoader

private val USER_SAVING_INSTANCE_STRING = "userList"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!userList.isEmpty()) {
            addContactsButton.visibility = View.INVISIBLE
        }

        val viewManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = UserAdapter(userList) {
                val phoneNumberUri = Uri.parse("tel:${it.phoneNumber}")
                val preCallIntent = Intent(Intent.ACTION_DIAL, phoneNumberUri)
                startActivity(preCallIntent)
            }
        }

        contactsLoader = ContactsLoader(this)
        eventHandler = EventHandler(this, contactsLoader)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {

        when (requestCode) {
            requestReadContactsPermissionID -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    contactsLoader.tryLoadContacts()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Please 'Apply' :(",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(USER_SAVING_INSTANCE_STRING, userList)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        userList = savedInstanceState.getSerializable(USER_SAVING_INSTANCE_STRING) as ArrayList<User>
    }
}