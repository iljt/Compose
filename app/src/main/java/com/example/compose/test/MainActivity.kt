package com.example.compose.test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose.R
import com.example.compose.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestBox("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestColumn(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.SpaceEvenly) {
        val context = LocalContext.current
        Text(
            text = "Hello $name!",
            modifier = Modifier.align(Alignment.End),
            color = Color.Blue,
            fontSize = 20.sp
        )

        Button(onClick = {
            Toast.makeText(context, "This is Toast", Toast.LENGTH_SHORT).show()
        }) {
            Text(
                text = "this is Button",
                modifier = modifier,
                color = Color.Red,
                fontSize = 15.sp
            )
        }

        TextField(value = "",
            onValueChange ={},
            placeholder = { Text(text = "please input text")},
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Green
            )
        )
        //drawable资源形式的图片要如何加载
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "bot")
        //bitmap形式的图片要如何加载
      /*  val bitmap: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.ic_launcher_foreground)
        Image(bitmap = bitmap, contentDescription = "")*/

        AsyncImage(
            model = "https://www.nmcp.net/wp-content/themes/azzxx/img/random/6.jpg",
            contentDescription = "First line of code"
        )

        CircularProgressIndicator(
            color = Color.Green,
            strokeWidth = 5.dp
        )
        LinearProgressIndicator(
            color = Color.Red,
            trackColor = Color.Gray
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestRow(name: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        val context = LocalContext.current
        Text(
            text = "Hello $name!",
            modifier = modifier,
            color = Color.Blue,
            fontSize = 20.sp
        )

        Button(onClick = {
            Toast.makeText(context, "This is Toast", Toast.LENGTH_SHORT).show()
        }) {
            Text(
                text = "this is Button",
                modifier = modifier,
                color = Color.Red,
                fontSize = 15.sp
            )
        }

        TextField(value = "",
            onValueChange ={},
            placeholder = { Text(text = "please input text")},
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Green
            )
        )
        //drawable资源形式的图片要如何加载
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "bot")
        //bitmap形式的图片要如何加载
        /*  val bitmap: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.ic_launcher_foreground)
          Image(bitmap = bitmap, contentDescription = "")*/

        AsyncImage(
            model = "https://www.nmcp.net/wp-content/themes/azzxx/img/random/6.jpg",
            contentDescription = "First line of code"
        )

        CircularProgressIndicator(
            color = Color.Green,
            strokeWidth = 5.dp
        )
        LinearProgressIndicator(
            color = Color.Red,
            trackColor = Color.Gray
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestBox(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current

        Text(
            text = "Hello $name!",
            modifier = Modifier.align(Alignment.TopStart).clickable(onClick = {
                Toast.makeText(context, "Thanks for clicking! I am Text", Toast.LENGTH_SHORT).show()
            }
            )
        )

        Button( modifier = Modifier.align(Alignment.TopEnd),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(width = 3.dp, brush = SolidColor(Color.Green)),
            onClick = {
               Toast.makeText(context, "This is Toast onClick", Toast.LENGTH_SHORT).show()
            }) {
            Text(
                text = "this is Button",
                color = Color.Red,
                fontSize = 15.sp
            )
        }
        var textValue by remember { mutableStateOf("") }
        TextField(value = textValue,
            onValueChange ={
                Log.e("onValueChange= ", "it= $it")
                textValue = it
            },
            modifier = Modifier.align(Alignment.CenterStart),
            placeholder = { Text(text = "please input password")},
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Green
            ),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Red),
            label = { Text(text = "密码")},
           // label = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            //设置只能输入数字 imeAction 可以设置键盘按钮类型，这里设置为搜索
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,imeAction = ImeAction.Search)
        )
        //drawable资源形式的图片要如何加载
        Image( modifier = Modifier.align(Alignment.CenterEnd),painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "bot")
        //bitmap形式的图片要如何加载
        /*  val bitmap: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.ic_launcher_foreground)
          Image(bitmap = bitmap, contentDescription = "")*/

        AsyncImage(
            modifier = Modifier.align(Alignment.TopCenter),
            model = "https://www.nmcp.net/wp-content/themes/azzxx/img/random/6.jpg",
            contentDescription = "First line of code"
        )

        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.BottomStart),
            color = Color.Green,
            strokeWidth = 5.dp
        )
        LinearProgressIndicator(
            modifier = Modifier.align(Alignment.BottomEnd),
            color = Color.DarkGray,
            trackColor = Color.Gray
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        TestBox("Android")
    }
}