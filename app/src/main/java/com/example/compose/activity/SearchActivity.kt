package com.example.compose.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.R
import com.example.compose.components.PersonItem
import com.example.compose.theme.ComposeTheme
import com.example.compose.theme.Shapes
import com.example.compose.theme.black
import com.example.compose.theme.color_75B4FF
import com.example.compose.theme.color_f6f9ff
import com.example.compose.theme.white
import com.example.compose.viewmodel.SearchViewModel

class SearchActivity : ComponentActivity() {
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   SearchPage(this)
                }
            }
        }
    }
}

@Composable
fun SearchPage(activity: Activity? = null,viewModel: SearchViewModel = viewModel()){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white),
    ) {
        SearchBar(activity,viewModel)
        SearchList(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(activity: Activity? = null,viewModel: SearchViewModel) {
    val keyword = remember { mutableStateOf(viewModel.editText.value) }
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .padding(0.dp, 0.dp,0.dp,0.dp)
    ) {
        OutlinedTextField(
            value = keyword.value,
            onValueChange = {
                viewModel.onEvent(it)
                keyword.value = it
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(10.dp, 0.dp, 50.dp, 0.dp),
            maxLines = 1,
            placeholder = { Text(text = stringResource(id = R.string.input_code)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            },
            shape = Shapes.small,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = black,
                //背景颜色
                containerColor = color_f6f9ff,
                focusedBorderColor = color_f6f9ff,
                unfocusedBorderColor = color_f6f9ff
            ),
            textStyle = TextStyle(fontSize = 16.sp, color = black),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )
        Text(
            text = stringResource(id = R.string.cancel),
            modifier = Modifier
                .padding(10.dp, 0.dp, 10.dp, 10.dp)
                .align(Alignment.CenterEnd)
                .clickable { activity?.finish() },
            color = color_75B4FF,
            fontSize = 16.sp
        )
    }
}


@Composable
private fun SearchList(viewModel: SearchViewModel){
    LazyColumn(
        //间距
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ){
        var personList =viewModel.state.value.personList
        items(personList.size){
            PersonItem(personList[it])
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchPagePreView() {
    ComposeTheme {
        SearchPage()
    }
}