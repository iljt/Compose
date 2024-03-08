package com.example.compose.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.components.ContentItem
import com.example.compose.components.TitleBar
import com.example.compose.components.UnderLine
import com.example.compose.theme.ComposeTheme
import com.example.compose.theme.white

class RemarkActivity : ComponentActivity() {
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
                    RemarkPage(this)
                }
            }
        }
    }
}

@Composable
fun RemarkPage(activity: Activity? = null){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white),
    ) {
        var title = stringResource(id = R.string.remarks_and_describe)
        TitleBar(title,stringResource(id = R.string.save), onClick = {activity?.finish()},rightClick = {
            Toast.makeText(context, "save", Toast.LENGTH_SHORT).show()
        })
        ContentBox(activity)
    }
}


@Preview(showBackground = true, backgroundColor = 0x00FFFF)
@Composable
private fun ContentBox(activity: Activity? = null) {
    var hasRemark by remember { mutableStateOf(true) }
    UnderLine()
    Item()
    UnderLine()
    RemarksContent(hasRemark,activity)

}



@Composable
fun RemarksContent(hasRemark:Boolean,activity: Activity? = null) {
    if (hasRemark){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(fontSize = 18.sp,
                text = stringResource(id = R.string.describe),
                modifier = Modifier
                    .padding(20.dp, 10.dp, 0.dp, 0.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp, 10.dp, 0.dp, 10.dp),
            ){
                Column( modifier = Modifier
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)) {
                    Text(
                        fontSize = 18.sp,
                        text = stringResource(id = R.string.name,"xxx"),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp))
                    Text(fontSize = 18.sp,
                        text = stringResource(id = R.string.phone,"13834342345"),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp))
                    Text(
                        fontSize = 18.sp,
                        text = "分会计法的反的方法士大夫大师傅打发士反对三十分大夫的撒发达发达省份的打发打发的地方大师傅地方的方法打发打发打发的犯得上对方对方的广泛",
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp))
                }
                Image(painter = painterResource(id = R.mipmap.right_icon),
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 20.dp, 0.dp)
                        .alpha(0f)
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
fun Item() {
    Row( modifier = Modifier
        .fillMaxWidth()
        .height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            text = stringResource(id = R.string.remarks_name),
            modifier = Modifier
                .padding(20.dp, 0.dp, 0.dp, 0.dp),
            color = Color.Black,
            fontSize = 18.sp
        )

        Text(
            text = "这是备注",
            modifier = Modifier
                .padding(40.dp, 0.dp, 0.dp, 0.dp),
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RemarkPagePreView() {
    ComposeTheme {
        RemarkPage()
    }
}