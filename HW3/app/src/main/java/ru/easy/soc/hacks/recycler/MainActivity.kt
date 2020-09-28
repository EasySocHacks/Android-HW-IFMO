package ru.easy.soc.hacks.recycler

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

lateinit var eventHandler: EventHandler
lateinit var contactsLoader: ContactsLoader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList.clear()

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
}