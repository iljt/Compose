package com.example.compose.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
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
import com.example.compose.activity.SettingActivity
import com.example.compose.components.ContentItem
import com.example.compose.components.UnderLine
import com.example.compose.theme.color_d8d8d8
import com.example.compose.theme.white

class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                My()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun My() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white),
    ) {
        headContent()
        Spacer(modifier = Modifier.height(10.dp))
        UnderLine()
        ContentItem(stringResource(id = R.string.setting),"",true){
            context.startActivity(Intent(context, SettingActivity::class.java))
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun headContent(){
    val context = LocalContext.current
    val headImg = remember { mutableStateOf("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg") }
    Box(
        modifier = Modifier
            .height(120.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 0.dp),
        ) {
            Text(
                text = stringResource(id = R.string.head),
                modifier = Modifier
                    .padding(20.dp, 30.dp, 0.dp, 0.dp)
                    .align(Alignment.TopStart),
                color = Color.Black,
                fontSize = 18.sp
            )
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
                    .height(60.dp)
                    .padding(0.dp, 0.dp, 20.dp,0.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {

                    },
                contentDescription = "headImg",
            )

            Text(
                text = "运营",
                modifier = Modifier
                    .padding(20.dp, 0.dp, 0.dp, 20.dp)
                    .align(Alignment.BottomStart),
                color = color_d8d8d8,
                fontSize = 18.sp
            )
        }
    }
}