package com.example.rttodolist.ui.authorize

data class resp(
    public var success : String,
    public var message : String,
    public var data : data
)

data class data(
    public var id: String,
    public var email: String,
    public var first_name : String,
    public var mid_name : String,
    public var last_name : String
)
