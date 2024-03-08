package com.example.compose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.bean.PersonInfo
import com.example.compose.bean.PersonInfoCollection

class SearchViewModel : ViewModel() {
    private val _state = mutableStateOf(PersonInfoCollection())

    var state: State<PersonInfoCollection> =_state

    private val _editText = mutableStateOf("")

    var editText: MutableState<String> =_editText

    init {
        _state.value =state.value.copy(
           personList =personList
        )
    }

    fun onEvent(key:String){
        editText.value = key
        _state.value =state.value.copy(
            personList =personList.filter {
                it.name.contains(key,true)
            }
        )
    }
}

private val personList = listOf(
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","5454","运营"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","7543","运营2"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","9896","运营3"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","1234","运营4"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","6778","运营5"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","3333","运营6"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","5555","运营7"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","6666","运营8"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","7777","运营9"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","8888","运营10"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","1111","运营11"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","2222","运营12"),
    PersonInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","3333","运营13"),
)