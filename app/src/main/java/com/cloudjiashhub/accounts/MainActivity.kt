package com.cloudjiashhub.accounts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cloudjiashhub.accounts.adapter.CustomAdapter
import com.cloudjiashhub.accounts.model.AccountData
import com.cloudjiashhub.accounts.model.ItemsViewModel
import com.cloudjiashhub.accounts.model.gson
import com.cloudjiashhub.accounts.ui.ActivityAddAccount
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var user_login = false
    private var open_first = false
    lateinit var progressbar : ProgressBar
    lateinit var recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences_login = getSharedPreferences("app_accounts_data", MODE_PRIVATE)
        val preferences = getSharedPreferences("app_accounts_data", MODE_PRIVATE)

        user_login = preferences_login.getBoolean("is_login", false)
        open_first = preferences.getBoolean("open_first", false)

        val buttonAddNote: FloatingActionButton =
            findViewById<FloatingActionButton>(R.id.button_add_note)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this, ActivityAddAccount::class.java)
            startActivity(intent)
        }

        runOnUiThread {
            if(!user_login){
                val intent = Intent(this@MainActivity, ActivityLogin::class.java);
                startActivity(intent);
                finish();
            }else {
                var user_key = preferences_login.getString("user_username","")
                intView()
            }

        }

    }

    private fun intView() {
        // getting the recyclerview by its id
        recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        progressbar = findViewById(R.id.progressBar)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
        // ArrayList of class ItemsViewModel

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = fetchData()
                // يمكنك التعامل مع البيانات هنا، مثل تحديث واجهة المستخدم
                // يجب استخدام withContext للعودة إلى الواجهة الرسومية إذا كنت بحاجة إلى تحديث الواجهة
                withContext(Dispatchers.Main) {
                    // قم بتحديث واجهة المستخدم هنا
                }
            } catch (e: IOException) {
                // التعامل مع الأخطاء هنا
            }
        }
    }

    private suspend fun fetchData() {
        val data = ArrayList<ItemsViewModel>()
        val client = OkHttpClient()
        val url = "http://192.168.0.15/aljiashi/development_stage/CloudJiashHub/api/accounts.php?get_accounts" // Replace with your API URL

        val request = Request.Builder()
            .url(url)
            .build()

        try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val itemType = object : TypeToken<List<AccountData>>() {}.type
                val accountDataList: List<AccountData> = gson.fromJson(responseBody, itemType)
                // Create a list of ItemsViewModel objects from the AccountData list
                val data = mutableListOf<ItemsViewModel>()
                for (accountData in accountDataList) {
                    data.add(ItemsViewModel(R.drawable.ic_baseline_account_circle_24,accountData.title,accountData.id,accountData.username
                        ,accountData.email,accountData.pass,accountData.return_email,accountData.phone,accountData.info
                        ,accountData.details,accountData.date_create,accountData.date_update,accountData.icon_account,accountData.url_account))
                }

                // This will pass the ArrayList to our Adapter
                val adapter = CustomAdapter(this,data)
                progressbar.visibility = View.INVISIBLE
                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
                // Handle the response here
            } else {
                // Handle the error
                Log.d("msg","eror 434")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("msg","eror 555 "+e.toString())
        }
    }

}