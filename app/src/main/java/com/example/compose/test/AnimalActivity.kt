package com.example.compose.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.theme.ComposeTheme
import kotlinx.coroutines.delay

class AnimalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestAnimatedContent2()
                }
            }
        }
    }
}

//设置点击事件改变size大小
@Composable
fun ClickBig(){
    var size by remember{ mutableStateOf(100.dp) }
    Column(
        Modifier
            .requiredSize(size)
            .background(Color.Blue)
            .clickable {
                size = if (size == 50.dp) 100.dp else 50.dp
            }) {

    }
}

//实现动画效果
@Composable
fun BigAnimal(){
    var big by remember { mutableStateOf(false) }
    val size by animateDpAsState(if (big) 100.dp else 50.dp, label = "")
    val color by animateColorAsState(targetValue = if (big) Color.Red else Color.Blue, label = "")
    Box(
        Modifier
            .requiredSize(size)
            .background(color)
            .clickable {
                big = !big
            }) {
    }
}


@Composable
fun AnimatedVisibilityPage(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
       var visiable by remember { mutableStateOf(true) }
        //单个动画
       /* AnimatedVisibility(visible = visiable) {
            MyImage()
        }*/
        //多种动画效果结合
        val density = LocalDensity.current
        AnimatedVisibility(visible = visiable
        , enter = slideInVertically {
                //从顶部-300dp的位置开始滑入
            with(density){-300.dp.roundToPx()   }
            }+ expandHorizontally(expandFrom = Alignment.End)
            + fadeIn(
                //从初始透明度0.3f开始淡入
                initialAlpha = 0.2f)
        , exit = slideOutHorizontally()+ shrinkHorizontally()+ fadeOut()
        ) {
            MyImage()
        }
        //使用不了AnimatedVisibility
      /*  Column(Modifier.fillMaxSize()) {
           Box(Modifier.fillMaxSize()){
               //报错'fun ColumnScope.AnimatedVisibility(visible: Boolean, modifier: Modifier = ..., enter: EnterTransition = ..., exit: ExitTransition = ..., label: String = ..., content:
               //作用域不对因为AnimatedVisibility有好几种作用域，ColumnScope和全局的作用域，IDE不知道该引用哪个了。
               //我们可以显示添加this@Column.，这样，就会引用Column的那个AnimatedVisibility了
               this@Column.AnimatedVisibility(visible = visiable) {
                   
               }

           }
        }*/
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { visiable = !visiable}) {
            Text(text = "显示/隐藏")
        }
    }

}


//AnimatedVisibility 各种入场和出场动画效果
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityPage2(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var visiable by remember { mutableStateOf(true) }
        var buttonText by remember { mutableStateOf("隐藏") }
        //fadeIn / fadeOut是淡出淡出的效果
        //scaleIn / scaleOut是缩放的效果
        //slideInVertically / slideOutVertically是垂直方向滑动进入/退出
        //slideInHorizontally / slideOutHorizontally是从横向方向滑动进入/退出
        //expandIn / shrinkOut是展开/收缩的效果
        //expandVertically / shrinkVertically是从垂直方向展开/收缩
        //expandHorizontally / shrinkHorizontally是从横向方向展开/收缩
        //slideIn / SlideOut是滑动的效果，这里进入的初始位置和退出的目标位置都设置为了(300,-150)，所以会从右上角进入/退出
        AnimatedVisibility(visible = visiable
            , enter= expandHorizontally()
            , exit = shrinkHorizontally()
        ) {
            MyImage()
        }
       /*AnimatedVisibility(visible = visiable
            , enter= slideIn(initialOffset = { IntOffset(300,-150) })
            , exit = slideOut(targetOffset = { IntOffset(300,150) })
        ) {
            MyImage()
        }*/
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
            if(visiable) buttonText="显示" else buttonText="隐藏"
            visiable = !visiable
        }) {
            Text(text = buttonText)
        }
    }

}

//AnimatedVisibility、animateXxxAsState、animateContentSize、Crossfade、AnimatedContent
//注意：animateContentSize 在修饰符链中的位置顺序很重要。为了确保流畅的动画，请务必将其放置在任何大小修饰符（如 size 或 defaultMinSize）前面，以确保 animateContentSize 会将带动画效果的值的变化报告给布局。
@Composable
fun AnimateContentSize(){
    var message by remember {
        mutableStateOf("Hello")
    }
    Column {
        Button(onClick = { message += "Hello" }) {
           Text(text = "Change text")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .background(Color.Green)
            .padding(10.dp)
            .animateContentSize(spring(Spring.DampingRatioHighBouncy)),//回弹动画效果
           // .animateContentSize(),
            contentAlignment = Alignment.Center
        ){
            Text(text = message, color = Color.White)
        }

    }
}



//animateEnterExit需要使用在AnimatedVisibility里面（直接或间接子项）。
//我们可以使用Modifier.animateEnterExit为每个子项指定不同的动画效果。
//比如这里添加的slide动画会覆盖AnimatedVisibility设置的fade动画。
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateEnterExit(){
    var visiable by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(visible = visiable,
        enter = fadeIn(tween(durationMillis = 1000)),
        exit = fadeOut(tween(durationMillis = 1000))
    ) {
       Box(modifier = Modifier
           .fillMaxSize()
           .background(Color.Gray)
       ){
           Box(modifier = Modifier
               .align(Alignment.Center)
               .animateEnterExit(
                   enter = slideInVertically(tween(durationMillis = 1000)),
                   exit = slideOutVertically(tween(durationMillis = 1000))
               )
               .sizeIn(minHeight = 64.dp, minWidth = 64.dp)
               .background(Color.Green)
           ){

           }
       }
    }

    LaunchedEffect(key1 = Unit, block = {
        delay(1500)
        visiable = true
    })

}


/*Crossfade
Crossfade用来将页面上显示的内容进行切换。
两个交替出现的组件进行渐变的切换，一个消失，而另一个显示出来。
动画效果是淡入淡出，并对尺寸进行处理 (瞬间改变)。
Crossfade只支持这一种动画效果，是对于AnimatedContent (下面会用到) 的简化版本。*/
@Composable
fun Crossfade(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var showPicture by remember { mutableStateOf(false) }
        Crossfade(targetState = showPicture, label = "") {
            if (it) {
                Image(
                    painter = painterResource(id = R.mipmap.init_top_ad),
                    modifier = Modifier.width(300.dp),
                    contentDescription = null
                )
            } else {
                Box(
                    Modifier
                       .height(300.dp * 9 / 16)
                        .width(300.dp)
                       /* .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))*/
                        .background(Color.Blue)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { showPicture = !showPicture }) {
            Text(text = "切换")
        }
    }
}

/*AnimatedContent
AnimatedContent用来控制多个组件的入场和出场，同时还能对入场和出场效果做定制
相当于是AnimatedVisibility和Crossfade的结合，AnimatedContent出入场动画效果的尺寸是渐变的，这个是区别于Crossfade的一个点。
AnimatedContent 显示是试验性质的，将来可能会发生变化，也可能会被完全移除。*/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TestAnimatedContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var showPicture by remember { mutableStateOf(false) }
        AnimatedContent(showPicture, label = "") {
            if (it) {
                Image(
                    painter = painterResource(id = R.mipmap.init_top_ad),
                    modifier = Modifier.width(300.dp),
                    contentDescription = null
                )
            } else {
                Box(
                    Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.Blue)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { showPicture = !showPicture }) {
            Text(text = "切换")
        }
    }
}

/*
实现数字增加的效果
使用AnimatedContent，设置自定义的动画效果，来实现数字增加的效果
*/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TestAnimatedContent2() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }, Modifier.width(100.dp)) {
            Text("增加")
        }
        Button(onClick = { count-- }, Modifier.width(100.dp)) {
            Text("减少")
        }
        Box(
            Modifier
                .size(100.dp)
                .border(1.dp, Color.LightGray)
                .padding(5.dp)
                .background(Color(0xFFF5F5F5)), contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = count,
                label = "",
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }) { targetCount ->
                Text(text = "$targetCount", fontSize = 20.sp)
            }
        }



    }
}



@Composable
private fun MyImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier.width(300.dp),
        contentDescription = null
    )
}



@Preview(showBackground = true)
@Composable
fun previewAnimal() {
    ComposeTheme {
        TestAnimatedContent2()
    }
}