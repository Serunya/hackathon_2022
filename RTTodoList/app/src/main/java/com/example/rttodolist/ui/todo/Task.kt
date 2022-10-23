package com.example.rttodolist.ui.todo

import com.example.rttodolist.ui.todotask.taskStatus

data class Task(
    public var id: String,
    public var title: String,
    public var description: String,
    public var creator_id: String,
    public var executor_id: String,
    public var priority: String,
    public var date_created: String,
    public var deadline: String,
    public var column_title: String,
    public var points : ArrayList<taskStatus> )