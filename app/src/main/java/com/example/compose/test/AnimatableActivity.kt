package com.example.compose.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.theme.ComposeTheme
import kotlinx.coroutines.delay

/**
 *  高可定制性的动画 Animatable
 *  Animatable和animateDpAsState的区别是什么
 * Animatable是Android Compose动画的底层API，如果我们查看源码，可以发现animateDpAsState内部是调用的animateValueAsState，而animateValueAsState内部调用的是Animatable
 *
 * animateDpAsState比Animatable更便捷易用，但屏蔽了部分功能，animateDpAsState抛弃了设置初始值的功能。
 * animateDpAsState是对于动画具体场景化的一种实现，它针对状态切换这一种动画的场景，设置了专门的扩展，而状态切换是不需要初始值的。
 *
 * 如果想要更高的可定制性(比如设置初始值)，那么就使用Animatable
 */

class AnimatableActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestAnimateDecay()
                }
            }
        }
    }
}

//animateDpAsState实现动画
@Composable
fun TestAnimateDpAsState(){
    var big by remember {
        mutableStateOf(false)
    }
    val animal = animateDpAsState(targetValue = if (big) 100.dp else 50.dp, label = "")
    Box(modifier = Modifier
        .requiredSize(animal.value)
        .background(Color.Blue)
        .clickable { big = !big })
}
//使用Animatable实现上述动画
@Composable
fun TestAnimatable(){
    var big by remember {
        mutableStateOf(false)
    }
    val size = remember(big) {
        if (big) 100.dp else 50.dp
    }
    val anim1 = remember { Animatable(size, Dp.VectorConverter) }
    //Dp.VectorConverter : 是TwoWayConverter<T, V> 类型，用来进行数值转换
    // Animatable中默认的TwoWayConverter是Float.VectorConverter
    //Animatable不仅支持Float，还支持多种转换
    ////Float (默认不传就是Float类型)
    //val animatableFloat1 = remember { Animatable(100F) }
    //val animatableFloat2 = remember { Animatable(100F,Float.VectorConverter) }
    ////DP
    //val animatableDp = remember{ Animatable(100.dp,Dp.VectorConverter) }
    ////Int
    //val animatableInt = remember { Animatable(100, Int.VectorConverter) }
    ////Rect
    //val animatableRect =
    //    remember { Animatable(Rect(100F, 100F, 100F, 100F), Rect.VectorConverter) }
    ////Offset
    //val animatableOffset = remember {
    //    Animatable(Offset(100F, 100F), Offset.VectorConverter)
    //}
    ////IntOffset
    //val animatableIntOffset = remember {
    //    Animatable(IntOffset(100, 100), IntOffset.VectorConverter)
    //}
    ////Size
    //val animatableSize = remember {
    //    Animatable(Size(100F, 100F), Size.VectorConverter)
    //}
    ////IntSize
    //val animatableIntSize = remember {
    //    Animatable(IntSize(100, 100), IntSize.VectorConverter)
    //}
    //
    //val blackColor = Color(0xFF000000)
    //val converter = remember(blackColor.colorSpace) {
    //    (Color.VectorConverter)(blackColor.colorSpace)
    //}
    ////Color
    //val animatableColor = remember {
    //    Animatable(blackColor, converter)
    //}
    //LaunchedEffect : 是Compose中的协程，animateTo是一个挂起函数，需要在协程中使用
    LaunchedEffect(key1 = big, block = {
        anim1.animateTo(size)
    })
    Box(modifier = Modifier
        .size(anim1.value)
        .background(Color.Blue)
        .clickable { big = !big })
}



//LaunchedEffect
/*但是在Compose里面，不能这么写，因为这种用法，没有针对Compose做优化，在Compose重组过程中，会重复进行调用。
所以Compose提供了一个专门在Compose中启动协程的API : LaunchedEffect
LaunchedEffect(key1 = 123, block = {
    //代码块
    anim1.animateTo(100.dp)
})
key不变的话可以这么写 : LaunchedEffect(Unit, block = {})
这里，只要key没有发生变化，那么block代码块就不会再次执行。这里也可以有多个key
LaunchedEffect(key1 = 123,key2=456,key3=789, block = {})
如果有让LaunchedEffect多次执行的场景，那么key1参数就需要可以变化
到这里，我们也就能看懂上面Animatable实现上述动画 中的代码了*/



//使用Animatable进行流程定制
@Composable
fun TestDiy(){
    var big by remember {
        mutableStateOf(false)
    }
    val size = remember(big) {
        if (big) 100.dp else 50.dp
    }
    val anim1 = remember { Animatable(size, Dp.VectorConverter) }
    LaunchedEffect(key1 = big, block = {
        //流程定制
       // 比如这里会先动画执行到10.dp，然后再执行到200.dp，最后再执行目标的size
        anim1.animateTo(10.dp)
        anim1.animateTo(200.dp)
        anim1.animateTo(size)
    })

    Box(modifier = Modifier
        .requiredSize(anim1.value)
        .background(Color.Blue)
        .clickable { big = !big })

}

//snapTo 瞬间完成动画
//snapTo会瞬间完成动画，就和没有动画的效果进行变化是一样的。
//那这个方法有啥用呢 ?
//可以在开始动画前先瞬间设置好初始动画状态，从而达到设置动画初始值的效果。
@Composable
fun TestsnapTo(){
    var big by remember {
        mutableStateOf(false)
    }
    val size = remember(big) {
        if (big) 100.dp else 50.dp
    }
    val anim1 = remember { Animatable(size, Dp.VectorConverter) }
    LaunchedEffect(key1 = big, block = {
        //代码块
        anim1.snapTo(size) //瞬间完成 ---> 设置初始值
    })

    Box(modifier = Modifier
        .requiredSize(anim1.value)
        .background(Color.Blue)
        .clickable { big = !big })

}

/*animateDecay 惯性衰减动画
animateDecay是惯性衰减动画，比如惯性滑动操作，可以实现和Android自带的惯性滑动一致的效果。
那和animateTo有啥区别呢 ?
最大的区别在于 animateTo 是需要设置目标值的，也就是动画结束的那一刻 某个view属性的值 必须明确指定，
而惯性衰减动画 animateDecay 则不需要指定。

animateDecay： 从初始速度慢慢停下来，例如松手之后的惯性滑动
animateTo： 指定结束的属性值

两种decay的区别
exponentialDecay和rememberSplineBasedDecay都可以实现惯性衰减动画，但它们两个有什么区别呢 ?
splineBasedDecay : 一般情况下只有在使用像素的情况下，会使用这个，它不会做针对像素做修正的，多个设备滑动效果会不一致
exponentialDecay :其他情况下一般都是用这个，不会根据像素密度变化而变化，比如DP，颜色，角度之类的


*/

@Composable
fun TestAnimateDecay(){
    val anim = remember {
        Animatable(0.dp, Dp.VectorConverter)
    }
    val decay = remember {
        exponentialDecay<Dp>()
    }
    LaunchedEffect(key1 = Unit, block = {
        delay(1000L)
        anim.animateDecay(2500.dp, decay) {
            //动画监听，可以获取动画当前的值 this.value 我们可以通过这个block回调，来让其他view 响应这个动画的变化。
          Log.e("AnimateDecay value= ",""+this.value)
        }
    })
    Box(
        modifier = Modifier
            .padding(0.dp, anim.value, 0.dp, 0.dp)
            .requiredSize(50.dp)
            .background(Color.Red)
    )

}


@Preview(showBackground = true)
@Composable
fun previewAnimatable() {
    ComposeTheme {
        TestAnimateDecay()
    }
}