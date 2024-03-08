package com.example.compose.activity

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

class SettingActivity : ComponentActivity() {
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
                    SettingPage(this)
                }
            }
        }
    }
}

@Composable
fun SettingPage(activity: Activity? = null){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white),
    ) {
        var title = stringResource(id = R.string.setting)
        TitleBar(title,"", onClick = {activity?.finish()},rightClick = {})
        SettingBox()
    }
}


@Preview(showBackground = true, backgroundColor = 0x00FFFF)
@Composable
private fun SettingBox() {
    val context = LocalContext.current
    ContentItem(stringResource(id = R.string.account_and_safe),"",true){
        Toast.makeText(context, "right click", Toast.LENGTH_LONG).show()
    }
    ContentItem(stringResource(id = R.string.version),"1.0.0",false,null)
    SureButton()
    UnderLine()
}


@Composable
private fun SureButton(
) {
    val context = LocalContext.current
    Button(
       // shape = RoundedCornerShape(0.dp),
        //border = BorderStroke(width = 0.dp, brush = SolidColor(color_d8d8d8)),
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 0.dp)
            .height(55.dp)
            .fillMaxWidth()
        ,
        colors = ButtonDefaults.buttonColors(containerColor = white),
        enabled = true,
        onClick = {
            Toast.makeText(context, "exit login click", Toast.LENGTH_LONG).show()
        }) {
        Text(
            text = stringResource(id = R.string.exit_login),
            color = Color.Red,
            fontSize = 18.sp
        )
    }
}



@Preview(showBackground = true)
@Composable
fun SettingPreView() {
    ComposeTheme {
        SettingPage()
    }
}