package com.cloudjiashhub.accounts.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cloudjiashhub.accounts.R
import com.cloudjiashhub.accounts.adapter.AdapterViewData
import com.cloudjiashhub.accounts.model.ItemsDataView
import com.cloudjiashhub.accounts.model.ItemsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class ActivityViewAccount : AppCompatActivity() {

    lateinit var progressbar : ProgressBar
    lateinit var recyclerview : RecyclerView
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_account)

        runOnUiThread {
            intView()
        }
    }

    private fun intView() {
        recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        progressbar = findViewById(R.id.progressBar)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemsDataView>()

        val receivedItem = intent.getSerializableExtra("data") as? ItemsViewModel

        if (receivedItem != null) {
            // Use receivedItem as needed
            data.add(ItemsDataView(R.drawable.facebook,"Account Name",receivedItem.title))
            data.add(ItemsDataView(R.drawable.facebook,"Id",receivedItem.id))
            data.add(ItemsDataView(R.drawable.facebook,"Username",receivedItem.username))
            data.add(ItemsDataView(R.drawable.facebook,"Email",receivedItem.email))
            data.add(ItemsDataView(R.drawable.facebook,"Password",receivedItem.pass))
            data.add(ItemsDataView(R.drawable.facebook,"Recovery Email",receivedItem.return_email))
            data.add(ItemsDataView(R.drawable.facebook,"Phone",receivedItem.phone))
            data.add(ItemsDataView(R.drawable.facebook,"Link Account",receivedItem.link_account))
            data.add(ItemsDataView(R.drawable.facebook,"Details",receivedItem.details))
            data.add(ItemsDataView(R.drawable.facebook,"Note",receivedItem.note))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = AdapterViewData(this,data)
        progressbar.visibility = View.INVISIBLE
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        val buttonAddNote: FloatingActionButton =
            findViewById<FloatingActionButton>(R.id.button_add_note)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this, ActivityEditAccount::class.java)
            intent.putExtra("data",receivedItem)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.del_account -> {
                // Handle item 1 click
                showConfirmationDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        // Set the dialog title and message
        alertDialogBuilder.setTitle("Delete Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to delete this item?")
        // Set "Yes" button
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the "Yes" button click here
            deleteItem() // You can call your delete function here
            dialog.dismiss()
        }
        // Set "No" button
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Handle the "No" button click here (dismiss the dialog)
            dialog.dismiss()
        }
        // Create and show the AlertDialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun deleteItem() {
        val requestBody = FormBody.Builder()
            .add("del_account", "delete")
            .add("id", "25")
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.15/aljiashi/development_stage/CloudJiashHub/api/accounts.php") // Replace with your API endpoint
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Request was successful, handle the response here
                    val responseBody = response.body?.string()
                    val json = responseBody // This should be your JSON response as a String
                    Log.d("msg",responseBody.toString())
                    val jsonObject = JSONObject(json)
                    val code = jsonObject.getInt("code")


                    if(code == 1){
                        val snackbar = Snackbar.make(findViewById(android.R.id.content), "Your data has been updated successfully", Snackbar.LENGTH_SHORT)
                        snackbar.show()
                        progressbar.visibility = View.INVISIBLE
                        //Go to Activity View
                        finish()
                    }else{
                        val snackbar = Snackbar.make(findViewById(android.R.id.content), "Failed to update your data", Snackbar.LENGTH_SHORT)
                        snackbar.show()
                        progressbar.visibility = View.INVISIBLE
                    }

                } else {
                    // Request failed, handle the error here
                    val errorBody = response.body?.string()
                    // Handle the error response
                    progressbar.visibility = View.INVISIBLE
                    Log.d("msg","Error"+errorBody.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                // Request failed due to a network error, handle the error here
                e.printStackTrace()
            }
        })
    }

}