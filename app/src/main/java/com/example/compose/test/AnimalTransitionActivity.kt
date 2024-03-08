package com.example.compose.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.theme.ComposeTheme

class AnimalTransitionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    testTransitionData()
                }
            }
        }
    }
}



/*
Transition 是什么
Transition和animateXxxAsState一样，是Android Compose中的一个动画API。
animateXxxAsState是针对单个目标值的动画，而Transition可以面向多个目标值应用动画并保持它们同步结束。
啥意思呢，就是Transition可以把多个动画整合到一起控制，保持状态一致。
animateXxxAsState是面向具体的值的，而Transition是面向状态的。
Transition的状态可以有好多个 (可以用枚举或各种类型表示)
*/
@Composable
fun TestTransition() {
   //创建当前状态，传入具体的BoxState值
   var currentState by remember {
       mutableStateOf(BoxState.Collapsed)
   }
    // 创建Transition，管理状态
    val transition = updateTransition(targetState = currentState, label = "boxTransition")
    //根据transition来创建动画值
    val size by transition.animateDp(label = "") { state->
        when (state) {
            BoxState.Collapsed -> 0.dp
            BoxState.Expanded -> 100.dp
            BoxState.HalfExpanded -> 50.dp
        }
    }

}


@Composable
fun change(){
    var expand by remember { mutableStateOf(false) }
    val size = if (expand) 100.dp else 50.dp
    val corner = if (expand) 0.dp else 25.dp
    Box(
        Modifier
            .requiredSize(size)
            .clip(RoundedCornerShape(corner))
            .background(Color.Blue)
            .clickable {
                expand = !expand
            }) {
    }
}

/**
 * change方法增加动画过渡效果
 */
@Composable
fun changeAnimal(){
    var expand by remember { mutableStateOf(false) }
    val size by animateDpAsState(targetValue =if (expand) 100.dp else 50.dp, label = "")
    val corner by animateDpAsState(targetValue =if (expand) 0.dp else 25.dp, label = "")
    Box(
        Modifier
            .requiredSize(size)
            .clip(RoundedCornerShape(corner))
            .background(Color.Blue)
            .clickable {
                expand = !expand
            }) {
    }
}


/**
 * change方法使用Transition进行替换,效果和changeAnimal一样
 *
 * 为什么要有Transition
 * 既然animateDpAsState和Transition可以实现同样的效果，那为什么还要有Transition这个API呢 ?
 *
 * 1、Transation对动画状态做统一的管理
 * 原因就在于animateXxxAsState是面向具体的值的，而Transition是面向状态的。
 * Transation对于动画状态做了统一的管理，带来了统一的视野，便于管理。(特别是对于有多个动画多个状态的情况)
 * 而animateDpAsState是只对单个动画状态负责的，并没有统一多个动画的情况下状态的强关系，比较乱。(在多个动画多个状态的情况下不便于管理)
 *
 * 2、Transition支持Compose动画预览
 * Transition和AnimatedVisibility(内部使用Transition实现)支持使用Compose动画预览功能，而animateXxxAsState是不支持Compose动画预览的 (不排除后期会支持)
 *
 * 我们在预览界面点击下面这个图标(Start Animation Preview)，会进入到动画预览模式

 */
@Composable
fun changeTransitionAnimal(){
    var expand by remember { mutableStateOf(false) }
    val expandTranstion = updateTransition(targetState = expand, label = "")
    val size by expandTranstion.animateDp(label = ""){
        if (it) 100.dp else 50.dp
    }
    val corner by  expandTranstion.animateDp(label = ""){
        if (it) 0.dp else 25.dp
    }
    Box(
        Modifier
            .requiredSize(size)
            .clip(RoundedCornerShape(corner))
            .background(Color.Blue)
            .clickable {
                expand = !expand
            }) {
    }
}



//createChildTransition创建子动画
//Transition可以使用createChildTransition创建子动画，子动画的动画数值来自于父动画。
//这样各自都只需要关心自己的状态，能够更好地实现关注点分离，父Transition将会知道子Transition中的所有动画值。

//定义状态BoxState枚举
enum class BoxState{
    Collapsed,//收起
    Expanded,//展开
    HalfExpanded,//半展开
}

// 定义蓝色和红色的Box
@Composable
private fun BoxBlue(
    childExpand1: Transition<Boolean>
) {
    val size by childExpand1.animateDp(label = "BoxBlue-size") {
        if (it) 100.dp else 50.dp
    }
    val corner by childExpand1.animateDp(label = "BoxBlue-corner") {
        if (it) 0.dp else 25.dp
    }
    Box(
        Modifier
            .size(size)
            .clip(RoundedCornerShape(corner))
            .background(Color.Blue)
    )
}


@Composable
private fun BoxRed(
    childExpand1: Transition<Boolean>
) {
    val size by childExpand1.animateDp(label = "BoxRed-size") {
        if (it) 60.dp else 30.dp
    }
    val corner by childExpand1.animateDp(label = "BoxRed-corner") {
        if (it) 0.dp else 15.dp
    }
    Box(
        Modifier
            .size(size)
            .clip(RoundedCornerShape(corner))
            .background(Color.Red)
    )
}

//创建Transition和ChildTransition
@OptIn(ExperimentalTransitionApi::class)
@Composable
private fun TransitionAndChildTransition(){
   var expand by remember {
       mutableStateOf(BoxState.Expanded)
   }
   val expandTransition = updateTransition(targetState = expand, label = "expandTransition")
   val childExpand1 = expandTransition.createChildTransition("child-expand-1") {
       it == BoxState.Expanded || it == BoxState.HalfExpanded
   }
    val childExpand2 = expandTransition.createChildTransition("child-expand-2") {
        it == BoxState.Expanded
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        BoxBlue(childExpand1 = childExpand1)
        BoxRed(childExpand1 = childExpand2)
        Button(onClick = { expand = BoxState.Collapsed }, Modifier.width(100.dp)) {
            Text(text = "收起")
        }
        Button(onClick = { expand = BoxState.HalfExpanded }, Modifier.width(100.dp)) {
            Text(text = "半展开")
        }
        Button(onClick = { expand = BoxState.Expanded }, Modifier.width(100.dp)) {
            Text(text = "全展开")
        }
        //对于BoxBlue和BoxRed，它们只关心对应的childTransition就可以了，而对于expandTransition却能够知道子Transition中的所有动画值。
        val stateParent = expandTransition.currentState
        val stateChild1 =childExpand1.currentState
        val stateChild2 =childExpand2.currentState
        Log.e("TransitionState= ","stateParent:$stateParent stateChild1:$stateChild1 stateChild2:$stateChild2")

    }
}



//与AnimatedVisibility和AnimatedContent配合使用
//AnimatedVisibility 和 AnimatedContent 可用作 Transition 的扩展函数，这样AnimatedVisibility 和 AnimatedContent就不用额外传参了。
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Mix(){
    var state by remember { mutableStateOf(true) }
    val transition = updateTransition(
        targetState = state,
        label = "myTransition"
    )
    transition.AnimatedVisibility(visible = { targetSelected ->
        targetSelected
    }) {
        Box(
            Modifier
                .requiredSize(100.dp)
                .background(Color.Blue)
                .clickable {
                    state = !state
                }) {
        }
    }
    transition.AnimatedContent {targetState ->
        if (targetState) {
            //Image1() //当targetState==true，显示组件1
        } else {
            //Image2() //当targetState==false，显示组件2
        }
    }

}


/*rememberInfiniteTransition
InfiniteTransition 是 Transition 的无限循环版本，一进入Compose阶段就开始运行，除非被移除，否则不会停止。
使用 rememberInfiniteTransition 创建 InfiniteTransition 实例。然后用animateColor、animatedFloat 或 animatedValue 添加子动画。
还需要通过 infiniteRepeatable 来设置 AnimationSpec，从而确定动画的时长、动画的重复模式等。*/
@Composable
fun RememberInfiniteTransition(){
    var infiniteTransition  = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Blue,
        targetValue = Color.Red,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ),
        label = ""
    )
    Box(
        Modifier
            .requiredSize(100.dp)
            .background(color)) {
    }
}


//封装并复用Transition动画
//对于简单的动画，直接在界面里写Transition是一种比较高效的方案。
//但是，在处理具有大量动画值的复杂组件时，可以将动画的实现和Compose界面分开，从而让代码更优雅，并使Transition动画可以被复用。
//1、定义Bean对象 用作封装的函数的返回值
class TransitionData(
    size:State<Dp>,
    corner:State<Dp>
){
    val size by size
    val corner by corner
}
//2、抽取并封装Transition动画
@Composable
fun updateTransitionData(expand:Boolean): TransitionData {
     val expandTransition = updateTransition(targetState = expand, label = "expandTransition")
     val size =expandTransition.animateDp(label = "size") {
            if(it) 150.dp else 50.dp
     }
    val corner =expandTransition.animateDp(label = "corner") {
        if(it) 0.dp else 10.dp
    }
   return remember {
       TransitionData(size,corner)
   }
}

@Composable
fun testTransitionData(){
    var expand by remember { mutableStateOf(false) }
    val transitionData = updateTransitionData(expand)
    Box(
        Modifier
            .requiredSize(transitionData.size)
            .clip(RoundedCornerShape(transitionData.corner))
            .background(Color.Red)
            .clickable {
                expand = !expand
            }) {
    }

}


@Preview(showBackground = true)
@Composable
fun previewTransitionAnimal() {
    ComposeTheme {
        testTransitionData()
    }
}