package com.example.compose.bean

import java.io.Serializable

data class PersonInfo(val imgUrl: String,val name: String,val post:String):Serializable

data class PersonInfoCollection(val personList:List<PersonInfo> = emptyList())