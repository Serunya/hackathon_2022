package com.example.rttodolist.ui.todotask

import androidx.lifecycle.ViewModel

class TaskVM : ViewModel() {
    private var ID: Int = -1
    public var title: String = "Название"
    public var description: String = "Описание"
    public var date: String = "Дата"
    public var executor: String = "Исполнитель"
    public var tasks: ArrayList<taskStatus> = ArrayList();
    private class ClientModel



}