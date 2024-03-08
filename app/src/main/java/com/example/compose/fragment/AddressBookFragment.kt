package com.example.compose.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.compose.R
import com.example.compose.activity.PersonInfoActivity
import com.example.compose.bean.DepartmentInfo
import com.example.compose.bean.PersonInfo
import com.example.compose.theme.color_999999
import com.example.compose.theme.color_d8d8d8
import com.example.compose.viewmodel.DepartmentViewModel

class AddressBookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                DepartmentList(departments = getSampleData())
            }
        }
    }
}

@Composable
fun DepartmentList(departments: List<DepartmentInfo>) {
    LazyColumn(
    ) {
        items(departments.size) { index ->
            DepartmentItem(department = departments[index])
        }
    }
}


@Composable
fun DepartmentItem(department: DepartmentInfo) {
    val headImg = remember { mutableStateOf(department.imgUrl) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Box(
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isExpanded =!isExpanded
                }
                .padding(5.dp, 10.dp, 0.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder
                        (LocalContext.current).data(data = headImg.value).apply(block = fun ImageRequest.Builder.() {
                        //占位图
                        placeholder(R.mipmap.ic_launcher)
                        //淡出效果
                        //crossfade(true)
                        //圆形效果
                        transformations(CircleCropTransformation())
                    }).build()
                ),
                modifier = Modifier
                    .height(55.dp)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .alpha(1f)
                    .clickable {
                        Log.e("right", " right click")
                    },
                contentDescription = "headImg",
            )

            Column(modifier = Modifier
                .weight(1f),
                verticalArrangement = Arrangement.Center
                ) {
                Text(
                    text = department.departmentName,
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                       ,
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            Image(imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                modifier = Modifier
                    .padding(start = 0.dp, end = 20.dp),
                contentDescription = "")
        }


        Divider(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp)
                .height(0.5.dp)
                .align(Alignment.BottomStart),
            color = color_d8d8d8
        )
        if (isExpanded) {
            ExpandedColumn(department =department)
        }
    }
}

@Composable
fun ExpandedColumn(department: DepartmentInfo) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
      )
     {
         Spacer(modifier = Modifier.height(60.dp))
         Divider(
             modifier = Modifier
                 .padding( 0.dp,10.dp,0.dp,0.dp)
                 .height(0.5.dp),
             color = color_d8d8d8
         )
        department.personList.forEach { member ->
            MemberItem(member)
        }
    }
}

@Composable
fun MemberItem(personInfo: PersonInfo) {
    val headImg = remember { mutableStateOf(personInfo.imgUrl) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .height(70.dp)
    ){

        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    context.startActivity(Intent(context, PersonInfoActivity::class.java).putExtra("personInfo",personInfo))
                }
                .padding(30.dp, 0.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder
                        (LocalContext.current).data(data = headImg.value).apply(block = fun ImageRequest.Builder.() {
                        //占位图
                        placeholder(R.mipmap.ic_launcher)
                        //淡出效果
                        //crossfade(true)
                        //圆形效果
                        transformations(CircleCropTransformation())
                    }).build()
                ),
                modifier = Modifier
                    .height(55.dp)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .alpha(1f)
                    .clickable {
                        Log.e("right", " right click")
                    },
                contentDescription = "headImg",
            )
            Column {
                Text(
                    text = personInfo.name,
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    color = Color.Black,
                    fontSize = 16.sp
                )

                Text(
                    text = personInfo.post,
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    color = color_999999,
                    fontSize = 16.sp
                )
            }
        }

        Divider(
            modifier = Modifier
                .padding(start = 30.dp, end = 0.dp)
                .height(0.5.dp)
                .align(Alignment.BottomStart),
            color = color_d8d8d8
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDepartmentList() {
    DepartmentList(departments = getSampleData())
}

@Composable
fun getSampleData(viewModel: DepartmentViewModel = viewModel()): List<DepartmentInfo> {
    val departmentList = viewModel.departmentInfo.value.departmentList
    return departmentList

}