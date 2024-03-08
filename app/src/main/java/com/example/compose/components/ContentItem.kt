package com.example.compose.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
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
import com.example.compose.theme.color_d8d8d8

@Preview(showBackground = true)
@Composable
fun ContentItem(
    left: String = "",
    right:String = "",
    showRightIcon:Boolean= true,
    rightClick: (() -> Unit)?
) {
    val leftName = remember { mutableStateOf(left) }
    val rightName = remember { mutableStateOf(right) }
    val showRightIcon = remember { mutableStateOf(showRightIcon) }
    Box(
        modifier = Modifier
            .height(55.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .clickable {
                    Log.e("right", " right click")
                    rightClick?.invoke()
                },
            ) {
            Text(
                text = leftName.value,
                modifier = Modifier
                    .padding(20.dp, 0.dp, 0.dp, 0.dp)
                    .align(Alignment.CenterStart),
                color = Color.Black,
                fontSize = 18.sp
            )
            AnimatedVisibility(visible = !showRightIcon.value,
                 modifier = Modifier
                     .padding(0.dp, 0.dp, 20.dp, 0.dp)
                     .align(Alignment.CenterEnd)
                     .clickable {
                         //Log.e("right"," right click")
                          rightClick?.invoke()
                     },) {
                    Text(
                        text = rightName.value,
                        color = color_d8d8d8,
                        fontSize = 16.sp
                    )
            }
            AnimatedVisibility(visible = showRightIcon.value,
                modifier = Modifier
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        Log.e("right", " right click")
                        rightClick?.invoke()
                    },) {
                Image(painter = painterResource(id = R.mipmap.right_icon),
                    contentDescription = "")
               }
        }
    }
    UnderLine()
}