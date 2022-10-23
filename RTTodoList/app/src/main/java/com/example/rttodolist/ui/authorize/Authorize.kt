package com.example.rttodolist.ui.authorize

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rttodolist.ClientToken
import com.example.rttodolist.MainActivity
import com.example.rttodolist.R
import com.example.rttodolist.ui.todo.Task
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException


class Authorize : AppCompatActivity() {
    val BASE_URL = "http://507215.msk-kvm.ru"
    val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
    val client = OkHttpClient()
    lateinit var alertDialog : AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorize)
        val preferences = this.getSharedPreferences("userPref", MODE_PRIVATE)
        val login : String? = preferences.getString("email",null)
        val password : String? = preferences.getString("pass",null)
        if(login != null && password != null){
            findViewById<EditText>(R.id.editTextEmail).setText(login,TextView.BufferType.EDITABLE)
            findViewById<EditText>(R.id.editTextPassword).setText(password,TextView.BufferType.EDITABLE)
            logIn(null)
        }
    }

    fun showLoadDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView = View.inflate(this,R.layout.load_dialog, null)
        builder.setView(dialogView)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun regIn(view: View?){
        val firstname = findViewById<EditText>(R.id.regFirstName).text.toString()
        val lastname = findViewById<EditText>(R.id.regLastName).text.toString()
        val email = findViewById<EditText>(R.id.regEmail).text.toString()
        val password = findViewById<EditText>(R.id.regPassword).text.toString()
        if(firstname == "" || lastname == "" || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Введены не все значения",Toast.LENGTH_SHORT).show()
            return
        }
        val con = this
        showLoadDialog()
        val regInDate = JSONObject()
        regInDate.put("email", email)
        regInDate.put("password", password)
        regInDate.put("first_name", firstname)
        regInDate.put("last_name", lastname)
        val body = RequestBody.create(JSON,regInDate.toString())
        val request = Request.Builder().url(BASE_URL + "/reg").post(body).build()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code == 200){
                    val editor = con.getSharedPreferences("userPref", MODE_PRIVATE).edit()
                    val builder = GsonBuilder()
                    val gson = builder.create()
                    val resp: resp = gson.fromJson(response.body!!.string(),resp::class.java)
                    ClientToken.id = resp.data.id
                    ClientToken.first_name = resp.data.first_name
                    ClientToken.last_name = resp.data.last_name
                    ClientToken.token = response.headers.get("Authorization").toString()
                    editor.putString("email", email)
                    editor.putString("pass", password)
                    val intent = Intent(con, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Log.println(Log.INFO, "Auth" ,response.message)
                    Log.println(Log.INFO,"Authorize",response.code.toString())
                }
            }
        })
    }


    fun logIn(view: View?){
        //запрос к бд через куротину
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
        if(email.isEmpty() || password == ""){
            Toast.makeText(this,"Введены не все значения",Toast.LENGTH_SHORT).show()
            return
        }
        val con = this
        showLoadDialog()
        val regInDate = JSONObject()
        regInDate.put("email", email)
        regInDate.put("password", password)
        val body = RequestBody.create(JSON,regInDate.toString())
        val request = Request.Builder().url(BASE_URL + "/auth").post(body).build()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code == 200){
                    ClientToken.token = response.headers.get("Authorization").toString()
                    val builder = GsonBuilder()
                    val gson = builder.create()
                    val resp: resp = gson.fromJson(response.body!!.string(),resp::class.java)
                    val editor = con.getSharedPreferences("userPref", MODE_PRIVATE).edit()
                    ClientToken.id = resp.data.id
                    ClientToken.first_name = resp.data.first_name
                    ClientToken.last_name = resp.data.last_name
                    editor.putString("email", email)
                    editor.putString("pass", password)
                    val intent = Intent(con, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val editor = con.getSharedPreferences("userPref", MODE_PRIVATE).edit()
                    editor.remove("email")
                    editor.remove("pass")
                }
            }

        })

    }

    fun viewRegisterClicked(view: View) {
        val linear = findViewById<LinearLayout>(R.id.viewAuthorize)
        linear.removeAllViews()
        linear.addView(LayoutInflater.from(this).inflate(R.layout.fragment_sign_up,null))
    }

    fun viewLoginClicked(view: View){
        val linear = findViewById<LinearLayout>(R.id.viewAuthorize)
        linear.removeAllViews()
        linear.addView(LayoutInflater.from(this).inflate(R.layout.fragment_sign_in,null))
    }
}