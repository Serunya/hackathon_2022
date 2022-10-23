package com.example.rttodolist

import com.example.rttodolist.ui.done.DoneFragment
import com.example.rttodolist.ui.inprogress.InProgressFragmnet
import com.example.rttodolist.ui.todo.Task
import com.example.rttodolist.ui.todo.ToDoFragment

object taskLists {
    public val todoTask = ArrayList<Task>()
    public val inProgressTask = ArrayList<Task>()
    public val doneTask = ArrayList<Task>()
}