package com.cloudjiashhub.accounts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cloudjiashhub.accounts.model.UserData
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ActivityLogin : AppCompatActivity() {

     lateinit var btn_login : Button
     lateinit var edit_ip : TextInputLayout
     lateinit var edit_username : TextInputLayout
     lateinit var edit_password : TextInputLayout
     val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login = findViewById(R.id.btn_login)
        edit_ip = findViewById(R.id.filledTextField_ip)
        edit_username = findViewById(R.id.filledTextField_username)
        edit_password = findViewById(R.id.filledTextField2)

        btn_login.setOnClickListener {
            var ip_server = edit_ip.editText?.text.toString()
            var username = edit_username.editText?.text.toString()
            var password = edit_password.editText?.text.toString()

            if (ip_server.length<8){
// عدد الأحرف أقل من الحد الأدنى المطلوب
                val snackbar = Snackbar.make(findViewById(android.R.id.content), "عدد الأحرف في IP يجب أن يكون على الأقل 8", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else if (username.length<4){
                // عدد الأحرف أقل من الحد الأدنى المطلوب
                val snackbar = Snackbar.make(findViewById(android.R.id.content), "عدد الأحرف في اسم المستخدم يجب أن يكون على الأقل 4", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else if(password.length<4){
                // عدد الأحرف أقل من الحد الأدنى المطلوب
                val snackbar = Snackbar.make(findViewById(android.R.id.content), "عدد الأحرف في كلمة المرور يجب أن يكون على الأقل 4", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                Login(ip_server,username,password)
            }
        }
    }

    fun Login(ip:String, username:String, password:String) {
        Toast.makeText(this,"جاري تسجيل الدخول",Toast.LENGTH_LONG).show()

        // Define the request body with username and password
        val requestBody = FormBody.Builder()
            .add("login", "true")
            .add("user", "admin")
            .add("pass", "admin")
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.15/aljiashi/development_stage/CloudJiashHub/api/api_login.php") // Replace with your API endpoint
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Request was successful, handle the response here
                    val responseBody = response.body?.string()

                    val json = responseBody // This should be your JSON response as a String
                    // Initialize Gson
                    val gson = Gson()
                    // Parse and process the response as needed
                    // Parse the JSON string into a MyResponse object
                    val myResponse = gson.fromJson(json, UserData::class.java)
                    // Now you can access individual fields
                    val code = myResponse.code
                    val id = myResponse.id
                    val fullname = myResponse.fullname
                    val username = myResponse.username
                    val email = myResponse.email
                    val msg = myResponse.msg
                    val data = myResponse.data
                    val user_key = myResponse.user_key

                    if(code == 1){
                        val sharedPreferences = getSharedPreferences("app_accounts_data", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("user_fullname", fullname)
                        editor.putString("user_username", username)
                        editor.putString("user_email", email)
                        editor.putString("user_data", data.toString())
                        editor.putString("user_key",user_key)
                        editor.putBoolean("is_login",true)
                        editor.apply()
                        val intent = Intent(this@ActivityLogin, MainActivity::class.java);
                        startActivity(intent);
                        finish();
                    }else{

                    }

                    Log.d("msg",responseBody.toString())

                } else {
                    // Request failed, handle the error here
                    val errorBody = response.body?.string()
                    // Handle the error response
                    Log.d("msg","Error"+errorBody.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                // Request failed due to a network error, handle the error here
                e.printStackTrace()
            }
        })
    }

    fun saveData(){

    }

}