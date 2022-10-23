package com.example.rttodolist.ui.add

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rttodolist.ClientToken
import com.example.rttodolist.MainActivity
import com.example.rttodolist.R
import com.example.rttodolist.ui.add.executors.executor_id
import com.example.rttodolist.ui.add.executors.takeExecutorFragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class addFragment : Fragment() {
    val BASE_URL = "http://507215.msk-kvm.ru"
    val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
    val client = OkHttpClient()
    private lateinit var vm: addVM;
    private lateinit var listItems : ArrayList<String>;
    private lateinit var adapter: adapter;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(this).get(addVM::class.java)
        listItems = vm.listItems
        val convertView = inflater.inflate(R.layout.fragment_add, container, false)
        adapter = adapter(requireContext(),listItems)
        val listTodo: ListView = convertView.findViewById(R.id.toDoList)
        listTodo.setDivider(null);
        val addButton = inflater.inflate(R.layout.add_point,null)
        addButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                vm.listItems.add("")
                adapter.notifyDataSetChanged()
            }

        })
        listTodo.addFooterView(addButton)
        listTodo.adapter = adapter
        // Переделать по человечески
        val c = Calendar.getInstance()
        val d = c.get(Calendar.DAY_OF_MONTH)
        val m = c.get(Calendar.MONTH)
        val y = c.get(Calendar.YEAR)
        val date = convertView.findViewById<TextView>(R.id.datePicker)
        date.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val dpd = DatePickerDialog(context!!,DatePickerDialog.OnDateSetListener {
                        view, year, month, dayOfMonth -> convertView.findViewById<TextView>(R.id.datePicker).text =
                    dayOfMonth.toString() + "." + month + "." + year},y,m,d)
                dpd.show()
            }
        })
        val s = this // костыль
        convertView.findViewById<TextView>(R.id.addExecutor).setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                parentFragmentManager.beginTransaction().replace(R.id.addFragmentContriner,takeExecutorFragment()).commit()
            }
        })
        convertView.findViewById<Button>(R.id.saveButtonFromAdd).setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val title = convertView.findViewById<EditText>(R.id.addTitle).text.toString()
                val description = convertView.findViewById<EditText>(R.id.addDescription).text.toString()
                if(title.isEmpty() || description.isEmpty() || date.equals("Выберите Дату")){
                    Toast.makeText(context,"Заполните шапку",Toast.LENGTH_SHORT).show()
                    return
                }
                val task = JSONObject()
                task.put("title", title)
                task.put("description", description)
                task.put("executor_id", ClientToken.id.toInt())
                task.put("priority", 0)
                task.put("deadline", 1669295109)
                Log.println(Log.INFO,"Main",task.toString())
                val body = RequestBody.create(JSON,task.toString())
                val request = Request.Builder().url(BASE_URL + "/task")
                    .post(body)
                    .addHeader("authorization",ClientToken.token.toString())
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if(response.code == 200){
                            startActivity(Intent(context,MainActivity::class.java))
                        }
                        else{
                            Log.println(Log.INFO,"Main",response.code.toString())
                         }
                    }

                })
            }
        })
        return convertView;
    }
}