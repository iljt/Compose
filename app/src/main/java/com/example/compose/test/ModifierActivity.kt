package com.example.compose.test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.theme.ComposeTheme
import kotlin.math.roundToInt

class ModifierActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ComposeTheme {
                Surface( modifier = Modifier.fillMaxSize(),
                    color =MaterialTheme.colorScheme.background)
                {
                   // IconImage()
                   // PointerInputEvent()
                   // PointerInputEvent2()
                   // testScroll()
                    // testDragger()
                    //testDragger2()
                    testScaleAndRoration()
                }
            }
        }
    }


}

/**
 * Modifier的作用
 * 1、修改尺寸、布局、行为、样式
 * 2、处理用户的输入
 * 3、使控件可点击、滚动、拖拽
 */
@Composable
fun IconImage(){
    Image(painter = painterResource(
        id = R.drawable.ic_launcher_foreground
    ),
        contentDescription = "",
        modifier = Modifier
            .wrapContentSize(align = Alignment.CenterStart)
            .border(5.dp, Color.Cyan, CircleShape)
            .clip(CircleShape)
            .rotate(180f)
        )
}


@Composable
fun PointerInputEvent(){
    Box(modifier = Modifier
        .requiredSize(200.dp)
        .background(Color.Green)
        .pointerInput(Unit) {
            detectTapGestures {
                Log.e("PointerInputEvent", "Tap")
            }
            // Never reach
        }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                Log.e("PointerInputEvent", "Dragging dragAmount= $dragAmount")
            }
            // Never reach
        }
    )
}

@Composable
fun PointerInputEvent2(){
    val context = LocalContext.current
    Box(modifier = Modifier
        .requiredSize(200.dp)
        .background(Color.Green)
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    Log.e("PointerInputEvent", "event: ${event.type}")
                }
            }
        }
        .clickable {
            Toast
                .makeText(context, "Box is clicked", Toast.LENGTH_SHORT)
                .show()
        }

    )
}


@Composable
fun testScroll(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan)
        .verticalScroll(rememberScrollState())) {
                repeat(100){
                    Text(
                        text = "item $it",
                        color = Color.White,
                        fontSize = 26.sp
                    )
                }
    }
}

//只能左右拖拽移动
@Composable
fun testDragger(){
    var offsetX by remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), 0) }
        .requiredSize(200.dp)
        .background(Color.Red)
        .draggable(orientation = Orientation.Horizontal,
            state = DraggableState { delta ->
                offsetX += delta
            })
      )
}


//上下左右拖拽都能移动
@Composable
fun testDragger2(){
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember {   mutableStateOf(0f) }

    Box(modifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .requiredSize(200.dp)
        .background(Color.Magenta)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        }

    )
}

//双指缩放和平移
@Composable
fun testScaleAndRoration() {
    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }
    var scale by remember {
        mutableStateOf(1f)
    }
    var rotationAngle by remember {
        mutableStateOf(0f)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()) {
            Box(modifier = Modifier
                .size(150.dp)
                .scale(scale)
                .rotate(rotationAngle)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Cyan)//background放大最后，否则大概率没响应
                .pointerInput(null) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        Log.e(
                            "detectTransformGestures",
                            "centroid =${centroid} pan =${pan} zoom =${zoom} rotation =${rotation}"
                        )
                        offsetX += pan.x
                        offsetY += pan.y
                        scale *= zoom
                        rotationAngle += rotation
                    }
                }
            )
    }

}




@Preview(showBackground = true)
@Composable
fun Preview() {
    ComposeTheme {
        testDragger2()
    }
}