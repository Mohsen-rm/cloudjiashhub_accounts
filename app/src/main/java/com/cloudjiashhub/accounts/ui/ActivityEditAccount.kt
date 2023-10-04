package com.cloudjiashhub.accounts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cloudjiashhub.accounts.R
import com.cloudjiashhub.accounts.model.ItemsDataView
import com.cloudjiashhub.accounts.model.ItemsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ActivityEditAccount : AppCompatActivity() {

    private lateinit var user_fullname : String
    private lateinit var user_id : String
    private lateinit var user_name : String
    private lateinit var user_email : String
    private lateinit var user_password : String
    private lateinit var user_recovery_email : String
    private lateinit var user_phone : String
    private lateinit var user_account_link : String
    private lateinit var user_details : String
    private lateinit var user_note : String
    private lateinit var user_account_type : String
    private lateinit var user_url_icon : String

    //edit Text
    lateinit var edit_user_fullname : TextInputLayout
    lateinit var edit_user_id : TextInputLayout
    lateinit var edit_user_name : TextInputLayout
    lateinit var edit_user_email : TextInputLayout
    lateinit var edit_user_password : TextInputLayout
    lateinit var edit_user_email_recovery : TextInputLayout
    lateinit var edit_user_phone : TextInputLayout
    lateinit var edit_user_account_link : TextInputLayout
    lateinit var edit_user_details : TextInputLayout
    lateinit var edit_user_note : TextInputLayout

    //  Button
    lateinit var btn_update_account : Button
    lateinit var progressbar : ProgressBar
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        btn_update_account = findViewById(R.id.add_account)
        edit_user_fullname = findViewById(R.id.edit_account_name)
        edit_user_id = findViewById(R.id.edit_id_account)
        edit_user_name = findViewById(R.id.edit_username)
        edit_user_email = findViewById(R.id.edit_email)
        edit_user_email_recovery = findViewById(R.id.edit_recovery_email)
        edit_user_password = findViewById(R.id.edit_password)
        edit_user_phone = findViewById(R.id.edit_phone)
        edit_user_account_link = findViewById(R.id.edit_account_link)
        edit_user_details = findViewById(R.id.edit_account_link)
        edit_user_note = findViewById(R.id.edit_note)
        progressbar = findViewById(R.id.progressBar)

        runOnUiThread {
            val receivedItem = intent.getSerializableExtra("data") as? ItemsViewModel

            if (receivedItem != null) {
                // Use receivedItem as needed
                edit_user_fullname.editText?.setText(receivedItem.title)
                edit_user_id.editText?.setText(receivedItem.id)
                edit_user_name.editText?.setText(receivedItem.username)
                edit_user_email.editText?.setText(receivedItem.email)
                edit_user_password.editText?.setText(receivedItem.pass)
                edit_user_email_recovery.editText?.setText(receivedItem.return_email)
                edit_user_phone.editText?.setText(receivedItem.phone)
                edit_user_account_link.editText?.setText(receivedItem.link_account)
                edit_user_details.editText?.setText(receivedItem.details)
                edit_user_note.editText?.setText(receivedItem.note)
                initView()
            }
        }

    }

    private fun initView() {
        btn_update_account.setOnClickListener {
            user_fullname = edit_user_fullname.editText?.text.toString()
            user_id = edit_user_id.editText?.text.toString()
            user_name = edit_user_name.editText?.text.toString()
            user_email = edit_user_email.editText?.text.toString()
            user_recovery_email = edit_user_email_recovery.editText?.text.toString()
            user_password = edit_user_password.editText?.text.toString()
            user_phone = edit_user_phone.editText?.text.toString()
            user_account_link = edit_user_account_link.editText?.text.toString()
            user_details = edit_user_details.editText?.text.toString()
            user_note = edit_user_note.editText?.text.toString()

            if(!user_fullname.trim().isEmpty() || !user_name.trim().isEmpty() || !user_email.trim().isEmpty()){
                progressbar.visibility = View.VISIBLE
                add_account()
            }else{
                val snackbar = Snackbar.make(findViewById(android.R.id.content), "Please enter at least the account name, email, or username", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

    private fun add_account() {
        val requestBody = FormBody.Builder()
            .add("update_account", "update")
            .add("fullname", user_fullname)
            .add("user_id", user_id)
            .add("user_name", user_name)
            .add("user_email", user_email)
            .add("user_password", user_password)
            .add("user_return_email", user_recovery_email)
            .add("user_phone", user_phone)
            .add("user_note", user_note)
            .add("user_details", user_details)
            .add("user_icon_account", "user_url_icon")
            .add("user_url_account", user_account_link)
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
            .add("id", "24")
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