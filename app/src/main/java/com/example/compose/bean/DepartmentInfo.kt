package com.example.compose.bean

data class DepartmentInfo(val imgUrl: String ="",val departmentName: String ="",val personList:List<PersonInfo> = emptyList())

data class DepartmentInfoCollection(val departmentList:List<DepartmentInfo> = emptyList())