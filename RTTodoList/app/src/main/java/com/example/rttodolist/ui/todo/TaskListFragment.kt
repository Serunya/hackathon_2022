package com.example.rttodolist.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import com.example.rttodolist.R
import com.example.rttodolist.ui.add.adapter
import com.example.rttodolist.ui.todotask.taskStatus
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskListFragment : Fragment() {
    public val tasks = ArrayList<Task>();
    init {
        val p = ArrayList<taskStatus>()
        p.add(taskStatus("0","Починить сервер","0","0"))
        p.add(taskStatus("0","Разобраться с ошибкой","0","0"))
        p.add(taskStatus("0","Наказать виновных","0","0"))
        tasks.add(Task("0","Почините Сервер",
            "Сервер выйдаёт код 503, нужно починить",
        "0",
        "Матвей ",
        "0",
        "23.10.2022","23.10.2022","To do",p))
    }
    public lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmen
        val converView = inflater.inflate(R.layout.fragment_task_list, container, false)
        adapter = TaskAdapter(requireContext(),tasks)
        converView.findViewById<ListView>(R.id.list).adapter = adapter
        return converView
    }
}