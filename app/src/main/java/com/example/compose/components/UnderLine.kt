package com.example.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.theme.color_d8d8d8

@Preview(showBackground = true)
@Composable
fun UnderLine(thickness: Dp = 0.5.dp,color:Color = color_d8d8d8) {
    val mThickness = remember { mutableStateOf(thickness) }
    val mColor = remember { mutableStateOf(color) }
    Divider(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 0.dp),
        //设置分割线的高度
        thickness = mThickness.value,
        //设置分割线的颜色
        color = mColor.value,
    )

}