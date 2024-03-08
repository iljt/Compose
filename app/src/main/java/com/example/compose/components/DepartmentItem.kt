package com.example.compose.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.compose.R
import com.example.compose.bean.DepartmentInfo
import com.example.compose.theme.color_d8d8d8

@Composable
fun DepartmentItem(info: DepartmentInfo, onItemClick: ((DepartmentInfo,Boolean) -> Unit)?) {
    val headImg = remember { mutableStateOf(info.imgUrl) }
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .height(70.dp).clickable {
                isExpanded =!isExpanded
                onItemClick?.invoke(info,isExpanded)
            }
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp, 0.dp, 0.dp, 0.dp),
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
                    text = info.departmentName,
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }

        Image(imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            modifier = Modifier
                .padding(start = 0.dp, end = 20.dp)
                .align(Alignment.CenterEnd),
            contentDescription = "")


        Divider(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp)
                .height(0.5.dp)
                .align(Alignment.BottomStart),
            color = color_d8d8d8
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DepartmentItemPreVIew() {
    DepartmentItem(DepartmentInfo("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg","某某某"),null)
}