package com.example.compose.test

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp

data class Blog(
    val name:String,
    val author:String
)

class DummyBlogProvider : PreviewParameterProvider<Blog> {
    override val values =
        sequenceOf(Blog("Learning Compose", "Zhangshan"), Blog("Learning Android", "Lisi"))
    override val count: Int = values.count()
}

@Composable
fun SimpleTextComponent(name: String) {
    Text(
        text = "$name",
        color = Color.Red,
        fontSize = 20.sp
    )
}


@Preview
@Composable
fun BlogInfo(@PreviewParameter(DummyBlogProvider::class) blog: Blog) {
    SimpleTextComponent("${blog.name} by ${blog.author}")
}



