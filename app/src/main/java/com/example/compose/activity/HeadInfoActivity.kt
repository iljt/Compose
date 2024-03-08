package com.example.compose.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.compose.R
import com.example.compose.components.ContentItem
import com.example.compose.components.TitleBar
import com.example.compose.components.UnderLine
import com.example.compose.theme.ComposeTheme
import com.example.compose.theme.white

class HeadInfoActivity : ComponentActivity() {
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
                   HeadInfoPage(this)
                }
            }
        }
    }
}

@Composable
fun HeadInfoPage(activity: Activity? = null){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white),
    ) {
        var title = stringResource(id = R.string.personinfo)
        TitleBar(title,"", onClick = {activity?.finish()},rightClick = {})
        ContentBox()
    }
}


@Preview(showBackground = true, backgroundColor = 0x00FFFF)
@Composable
private fun ContentBox() {
    head()
    UnderLine()
    ContentItem(stringResource(id = R.string.number),"6564",false,null)
    ContentItem(stringResource(id = R.string.sex),"男",false,null)
}


@Composable
private fun head(){
    val headImg = remember { mutableStateOf("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg") }
    Box(
        modifier = Modifier
            .height(70.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp,0.dp,0.dp,0.dp),
        ) {
            Text(
                text = stringResource(id = R.string.head),
                modifier = Modifier
                    .padding(20.dp, 0.dp, 0.dp, 0.dp)
                    .align(Alignment.CenterStart),
                color = Color.Black,
                fontSize = 18.sp
            )
            Image(
                painter = rememberAsyncImagePainter(ImageRequest.Builder
                    (LocalContext.current).data(data = headImg.value).apply(block = fun ImageRequest.Builder.() {
                    //占位图
                  //  placeholder(R.mipmap.head_icon)
                    //淡出效果
                    //crossfade(true)
                    //圆形效果
                    transformations(CircleCropTransformation())
                 }).build()
                ),
                modifier = Modifier
                    .height(60.dp)
                    .padding(0.dp, 0.dp, 40.dp, 0.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        Log.e("right", " right click")
                    },
                contentDescription = "headImg",
            )

            Image(painter = painterResource(id = R.mipmap.right_icon),
                modifier = Modifier
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        Log.e("right", " right click")
                    },
                contentDescription = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeadInfoPagePreView() {
    ComposeTheme {
        HeadInfoPage()
    }
}