package com.example.compose.test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.theme.ComposeTheme

class BaseControlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AlertDialogComponent()
                }
            }
        }
    }
}


@Composable
fun SimpleCheckboxComponent() {
    val checkedState = remember { mutableStateOf(true) }
    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(16.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Checkbox Example", modifier = Modifier.padding(16.dp))
    }
}


@Composable
fun SimpleBoxComponent() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Image(modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(18.dp)),
            contentScale = ContentScale.Fit,painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "bot")
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "I am a text over Image",
            fontSize = 16.sp,
            color = Color.Red
        )
    }
}


@Composable
fun SimpleCardComponent() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Simple Card",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun SimpleButtonComponent() {
    val context = LocalContext.current
    Button(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 3.dp, brush = SolidColor(Color.Green)),
        onClick = {
            Toast.makeText(context, "Thanks for clicking!", Toast.LENGTH_LONG).show()
        },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text("Click Me")
    }
}


@Composable
fun AlertDialogComponent() {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Alert Dialog") },
            text = { Text("Hello! I am an Alert Dialog") },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        /* Do some other action */
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        /* Do some other action */
                    }
                ) {
                    Text("Dismiss")
                }
            },
            containerColor = Color.Black,
            textContentColor = Color.White
        )
    }
}

@Composable
fun CustomViewComponent() {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        drawRect(
            color = Color.Red,
            // topLeft is the coordinate of top-left point
            topLeft = Offset(0f, 0f),
            size = Size(800f, 400f)
        )
        drawArc(
            Color.Gray,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            size = Size(600f, 600f),
            topLeft = Offset(100f, 200f)
        )
    }
}

@Composable
fun CrossFadeAnimation(){
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Gray)
    var currentColor by remember {
        mutableStateOf(colors[0])
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Crossfade(targetState = currentColor, label = "") {
         
        }
    }
}


@Preview(showBackground = true)
@Composable
fun preview() {
    ComposeTheme {
        CustomViewComponent()
    }
}