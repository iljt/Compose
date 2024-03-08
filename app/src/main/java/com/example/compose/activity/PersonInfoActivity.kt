package com.example.compose.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.compose.theme.color_75B4FF
import com.example.compose.theme.color_999999
import com.example.compose.theme.color_d8d8d8
import com.example.compose.theme.white

class PersonInfoActivity : ComponentActivity() {
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
                    PersonInfoPage(this)
                }
            }
        }
    }
}

@Composable
fun PersonInfoPage(activity: Activity? = null){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white),
    ) {
        var title = stringResource(id = R.string.personinfo)
        TitleBar(title,"", onClick = {activity?.finish()},rightClick = {})
        ContentBox(activity)
    }
}


@Preview(showBackground = true, backgroundColor = 0x00FFFF)
@Composable
private fun ContentBox(activity: Activity? = null) {
    var hasRemark by remember { mutableStateOf(true) }
    headContent(hasRemark)
    UnderLine()
    remarkContent(hasRemark,activity)
    DepartmentItem()
    SendButton(activity)
}




@Composable
private fun headContent(hasRemark:Boolean){
    val headImg = remember { mutableStateOf("http://img.zcool.cn/community/01a7f7590cd5a3a8012145509a8335.jpg@2o.jpg") }
    Box(
        modifier = Modifier
            .height(90.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberAsyncImagePainter(ImageRequest.Builder
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
                    .height(80.dp)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .alpha(1f)
                    .clickable {
                        Log.e("right", " right click")
                    },
                contentDescription = "headImg",
            )
            Column {
                Text(
                    text = "remark",
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    color = Color.Black,
                    fontSize = 17.sp
                )
                Text(
                    text = "5678",
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                        .alpha(if (hasRemark) 1f else 0f),
                    color = Color.Black,
                    fontSize = 17.sp
                )
                Text(
                    text = "运营高手",
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    color = color_999999,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Composable
fun remarkContent(hasRemark:Boolean,activity: Activity? = null) {
    if (hasRemark){
        Row(
            modifier = Modifier
                .fillMaxWidth().clickable {
                    activity?.startActivity(Intent(activity, RemarkActivity::class.java))
                }
        ) {
            Text(text = stringResource(id = R.string.describe),
                modifier = Modifier
                    .padding(20.dp, 10.dp, 0.dp, 0.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp, 10.dp, 0.dp, 10.dp),
            ){
                Column( modifier = Modifier
                    .padding(0.dp, 0.dp, 30.dp, 0.dp)) {
                    Text(text = stringResource(id = R.string.name,"xxx"),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp))
                    Text(text = stringResource(id = R.string.phone,"13834342345"),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp))
                    Text(maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        text = "分会计法的反的方法士大夫大师傅打发士反对三十分大夫的撒发达发达省份的打发打发的地方大师傅地方的方法打发打发打发的犯得上对方对方的广泛",
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp))
                }
                Image(painter = painterResource(id = R.mipmap.right_icon),
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 20.dp, 0.dp)
                        .align(Alignment.CenterEnd)
                       ,
                    contentDescription = "")
            }

        }
        UnderLine()
    }else{
        ContentItem(stringResource(id = R.string.set_remarks),"",true){
            activity?.startActivity(Intent(activity, HeadInfoActivity::class.java))
        }
    }

}

@Composable
fun DepartmentItem() {
    Row( modifier = Modifier
        .fillMaxWidth()
        .height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            text = stringResource(id = R.string.department),
            modifier = Modifier
                .padding(20.dp, 0.dp, 0.dp, 0.dp),
            color = Color.Black,
            fontSize = 18.sp
        )

        Text(
            text = "运营部",
            modifier = Modifier
                .padding(40.dp, 0.dp, 0.dp, 0.dp),
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}


@Composable
fun SendButton(activity: Activity? = null) {
    Box(modifier = Modifier
        .fillMaxHeight()) {
            Button(
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(width = 0.dp, brush = SolidColor(color_d8d8d8)),
                modifier = Modifier
                    .padding(30.dp, 0.dp, 30.dp, 60.dp)
                    .align(Alignment.BottomCenter)
                    .height(55.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = color_75B4FF),
                // enabled = mEnabled,
                onClick = {
                    activity?.startActivity(Intent(activity, RemarkActivity::class.java))
                    // Toast.makeText(context, "Login click", Toast.LENGTH_SHORT).show()
                }) {
                Text(
                    text = stringResource(id = R.string.sendmsg),
                    color = white,
                    fontSize = 18.sp
                )
            }
    }
}


@Preview(showBackground = true)
@Composable
fun PersonInfoPreView() {
    ComposeTheme {
        PersonInfoPage()
    }
}