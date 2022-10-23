package com.example.rttodolist.ui.done

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.rttodolist.R
import com.example.rttodolist.taskLists
import com.example.rttodolist.ui.todo.DeadLines
import com.example.rttodolist.ui.todo.PagerAdapter
import com.example.rttodolist.ui.todo.Task
import com.example.rttodolist.ui.todo.TaskListFragment

class DoneFragment : Fragment() {
    val fragments = ArrayList<TaskListFragment>()


    public fun addTask(task: Task, deadlines: DeadLines) {
        when (deadlines) {
            DeadLines.DAY -> {
                fragments.get(0).tasks.add(task)
            }
            DeadLines.WEEK -> {
                fragments.get(1).tasks.add(task)
            }
            DeadLines.MOUNTH -> {
                fragments.get(2).tasks.add(task)
            }
            DeadLines.QUARTER -> {
                fragments.get(3).tasks.add(task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        for (i in 0..4)
            fragments.add(TaskListFragment())
        val converView = inflater.inflate(R.layout.fragment_to_do, container, false)
        val viewPager = converView.findViewById<ViewPager2>(R.id.mainTaskViewPager)
        val adapter = PagerAdapter(requireActivity(), fragments);
        viewPager.adapter = adapter
        return converView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (task in taskLists.doneTask) {
            if (!task.deadline.equals("null")) {
                if (task.deadline.toLong() - System.currentTimeMillis() < 8.64e+7) {
                    this.addTask(task, DeadLines.DAY)
                    continue
                } else {
                    if (task.deadline.toLong() - System.currentTimeMillis() < 6.048e+8) {
                        this.addTask(task, DeadLines.WEEK)
                        continue
                    } else
                        if (task.deadline.toLong() - System.currentTimeMillis() < 2.628e+9) {
                            this.addTask(task, DeadLines.MOUNTH)
                            continue
                        } else {
                            this.addTask(task, DeadLines.QUARTER)
                        }
                }
            }
        }
    }
}