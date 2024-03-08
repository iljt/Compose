package com.example.compose.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R

@Preview(showBackground = true)
@Composable
fun TitleBar(title: String = "",right:String = "",onClick:() ->Unit,rightClick:() ->Unit) {
    val titleName = remember { mutableStateOf(title) }
    val rightName = remember { mutableStateOf(right) }
    Box(
        modifier = Modifier
            .height(60.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp,0.dp,0.dp,0.dp),
            ) {
            //drawable资源形式的图片加载
            Image(painter = painterResource(id = R.mipmap.left_icon),
                modifier =  Modifier
                    .padding(0.dp,0.dp,0.dp,0.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                    Log.e("TitleBar"," TitleBar Click")
                    onClick.invoke()
                },
                contentDescription = "")
            Text(
                text = titleName.value,
                modifier =  Modifier
                    .padding(0.dp,0.dp,0.dp,0.dp)
                    .align(Alignment.Center),
                color = Color.Black,
                fontSize = 18.sp
            )
            Text(
                text = rightName.value,
                modifier =  Modifier
                    .padding(0.dp,0.dp,20.dp,0.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        Log.e("right"," right click")
                        rightClick.invoke()
                    },
                color = Color.Black,
                fontSize = 18.sp
            )

        }
    }
}