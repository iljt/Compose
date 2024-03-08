package com.example.compose.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.bean.DepartmentInfo
import com.example.compose.bean.DepartmentInfoCollection
import com.example.compose.bean.PersonInfo

class DepartmentViewModel : ViewModel() {
    private val _departmentInfo = mutableStateOf(DepartmentInfoCollection())

    var departmentInfo: State<DepartmentInfoCollection> =_departmentInfo

    init {
        _departmentInfo.value =departmentInfo.value.copy(
           departmentList =mDepartmentList
        )
    }
}


private var mPersonList:List<PersonInfo> = listOf(
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","5454","运营"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","7543","运营2"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","9896","运营3"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","1234","运营4"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","6778","运营5"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","3333","运营6"),
)


private val mDepartmentList = listOf(
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某2",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某3",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某4",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某5",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某6",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某7",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某8",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某9",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某10",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某11",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某12",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某13",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某14",mPersonList),
    DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某15",mPersonList),
)

