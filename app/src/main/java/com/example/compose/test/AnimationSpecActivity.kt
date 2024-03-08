package com.example.compose.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
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

/**
AnimationSpec是什么
Android Compose中的AnimationSpec用来自定义动画规格。
其实就是类似于传统View体系中的差值器Interpolator，但是比起差值器，又提供了更多的功能。
根据其不同的子类，可以来控制动画执行时长、设置动画执行多少时间到动画的某个位置、实现仿弹簧的动画效果、指定动画执行次数等等功能。
 */

class AnimationSpecActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestRepeatableSpec()
                }
            }
        }
    }
}

/**
 * SpringSpec
 * SpringSpec实现了AnimationSpec接口，提供了一些列基于弹簧的物理动画效果。
 * 很多动画内部AnimationSpec使用的默认值都是spring，比如animateXXXAsState以及updateTransition等。
 * 因为spring的动画效果基于物理原理，使动画更真实自然。
 * 同时，因为spring是基于物理规律的，所以无法指定动画执行时长，而是会基于物理规律来确定动画执行时长。
 *
 * class SpringSpec<T>(
 *     val dampingRatio: Float = Spring.DampingRatioNoBouncy,
 *     val stiffness: Float = Spring.StiffnessMedium,
 *     val visibilityThreshold: T? = null
 * ) : FiniteAnimationSpec<T>
 * spring有三个传参，dampingRatio、stiffness和visibilityThreshold，前两个参数主要用来控制弹跳动画的动画效果。

 * dampingRatio
 * dampingRation表示弹簧的阻尼比。这个值越大，阻尼越大。
 * 不同的dampingRatio有不同的效果，如下图所示
 * 如果不额外指定，默认会使用DampingRationNoBouncy，是不带弹簧效果的。
 * 需要注意dampingRation不能小于0
 *
 * stiffness
 * stiffness定义弹簧的刚度值，弹簧有多想变回去，这个值越大，回弹的越快。
 * Compose为stiffness定义的常量如下，默认值是StiffnessMedium
 * const val StiffnessHigh = 10_000f
 * const val StiffnessMedium = 1500f
 * const val StiffnessMediumLow = 400f
 * const val StiffnessLow = 200f
 * const val StiffnessVeryLow = 50f
 * 需要注意stiffness必须大于0
 *
 * visibilityThreshold
 * visibilityThreshold用来指定一个阈值，当动画到达这个阈值时，动画会立即停止。
 *
 *  TweenSpec
 * 这里的Tween不是老的View体系动画中的补间动画，而是和Interpolator(差值器)是一个作用的东西。
 * tween动画必须在规定时间内完成，它不能像SpringSpec那样完全基于物理规律进行动画，
 * class TweenSpec<T>(
 *     val durationMillis: Int = DefaultDurationMillis,
 *     val delay: Int = 0,
 *     val easing: Easing = FastOutSlowInEasing
 * )
 * tween有3个参数
 *
 * durationMillis : 动画执行时间 (ms)
 * delayMillis : 延迟多久开始执行
 * Easing : 衰减曲线动画效果，用来配置怎么进行渐变的
 */
@Composable
fun TestAnimationSpec(){
    var change by remember {
        mutableStateOf(false)
    }
    val offsetX = remember(change) {
        if (change) 350.dp else 50.dp
    }
    val offsetXAnim = remember {
        Animatable(offsetX, Dp.VectorConverter)
    }
    LaunchedEffect(key1 = change, block = {
        offsetXAnim.animateTo(
            offsetX, spring(Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
        )
    })

    Box(modifier = Modifier
        .requiredSize(100.dp)
        .padding( offsetXAnim.value,0.dp, 0.dp, 0.dp)
        .background(Color.Blue)
        .clickable { change = !change })

}


/**
 * Easing
 * Compose中预置的Easing 有这些
 *
 * //先加速后减速(默认)，相当于差值器中的FastOutSlowInInterpolator
 * //和AccelerateDecelerateInterpolator类似 (区别和FastOutSlowInInterpolator在于曲线幅度稍有不同，下面几个同理)
 * val FastOutSlowInEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
 * //入场速度快，慢慢减速到0，相当于差值器中的LinearOutSlowInInterpolator，和DecelerateInterpolator类似
 * val LinearOutSlowInEasing: Easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
 * //从0慢慢加速到很快，相当于差值器中的FastOutLinearInInterpolator，和AccelerateInterpolator类似
 * val FastOutLinearInEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)
 * //线性
 * val LinearEasing: Easing = Easing { fraction -> fraction }
 */
@Composable
fun Testtween(){
    var change by remember {
        mutableStateOf(false)
    }
    val offsetX = remember(change) {
        if (change) 300.dp else 0.dp
    }
    val offsetXAnim = remember {
        Animatable(offsetX, Dp.VectorConverter)
    }
    LaunchedEffect(key1 = change, block = {
        //offsetXAnim.animateTo(offsetX, TweenSpec(easing = LinearEasing))
        //tween是TweenSpec的简便写法，这两种都是可以用的
        offsetXAnim.animateTo(
            offsetX, tween(durationMillis = 1500, delayMillis = 0, easing = FastOutSlowInEasing)
        )
    })

    Box(modifier = Modifier
        .padding(offsetXAnim.value, 0.dp, 0.dp, 0.dp)
        .requiredSize(100.dp)
        .background(Color.Red)
        .clickable { change = !change })
}


/*SnapSpec
即时生效动画规格，和anim.snapTo的效果是一样的
anim.snapTo()
anim.animateTo(offset,SnapSpec(delayMillis=100*//*可以设置延迟*//*))
anim.animateTo(offset,snap(delayMillis=100*//*可以设置延迟*//*))*/
@Composable
fun TestSnapSpec(){
    var change by remember {
        mutableStateOf(false)
    }
    val offsetX = remember(change) {
        if (change) 300.dp else 0.dp
    }
    val offsetXAnim = remember {
        Animatable(offsetX, Dp.VectorConverter)
    }
    LaunchedEffect(key1 = change, block = {
        offsetXAnim.animateTo(
            offsetX, snap(delayMillis = 100)
        )
    })

    Box(modifier = Modifier
        .padding(offsetXAnim.value, 0.dp, 0.dp, 0.dp)
        .requiredSize(100.dp)
        .background(Color.Green)
        .clickable { change = !change })

}


/*
KeyframesSpec
关键帧动画规格，相当于可以分段的tweenSpec，但是动画的复用性会降低
anim.animateTo(size,keyframes{
    //infix function 中缀函数
    250.dp at 0 with LinearEasing //作用于 0毫秒-150毫秒
    500.dp at 150 with FastOutSlowInEasing//作用于150毫秒及之后  //默认是LinearEasing
    durationMillis = 450 //执行时长
    delayMillis = 50 //延迟多久执行
})
*/
@Composable
fun TestKeyframesSpec(){
    var change by remember {
        mutableStateOf(false)
    }
    val offsetX = remember(change) {
        if (change) 300.dp else 0.dp
    }
    val offsetXAnim = remember {
        Animatable(offsetX, Dp.VectorConverter)
    }
    LaunchedEffect(key1 = change, block = {
        offsetXAnim.animateTo(offsetX, keyframes {
            80.dp at 250 with FastOutSlowInEasing //0至250毫秒
            durationMillis = 1000 //动画持续多久
            delayMillis = 50 //延迟多久执行
        })
    })

    Box(modifier = Modifier
        .padding(offsetXAnim.value, 0.dp, 0.dp, 0.dp)
        .requiredSize(100.dp)
        .background(Color.Blue)
        .clickable { change = !change })


}

/**
 * RepeatableSpec
 * 循环动画规格，SpringSpec不能放进RepeatableSpec里面，必须是DurationBasedAnimationSpec的子类，SpringSpec属于物理弹簧效果，真实环境下肯定是无法持续循环的。
 * fun <T> repeatable(
 *     iterations: Int, //重复次数
 *     animation: DurationBasedAnimationSpec<T>,
 *     repeatMode: RepeatMode = RepeatMode.Restart, //重复模式 : 重新开始 / 翻转
 *     initialStartOffset: StartOffset = StartOffset(0) //初始偏移(时间偏移)
 * )
 */
@Composable
fun TestRepeatableSpec(){
    var change by remember {
        mutableStateOf(false)
    }
    val offsetX = remember(change) {
        if (change) 300.dp else 0.dp
    }
    val offsetXAnim = remember {
        Animatable(offsetX, Dp.VectorConverter)
    }
    //重复10次
   /* LaunchedEffect(key1 = change, block = {
        RepeatMode.Restart
        offsetXAnim.animateTo(offsetX, repeatable(10,tween(),RepeatMode.Reverse))
    })*/

    LaunchedEffect(key1 = change, block = {
        RepeatMode.Restart
      //  offsetXAnim.animateTo(offsetX, repeatable(10,tween(),RepeatMode.Reverse))
        //InfiniteRepeatableSpec是无限动画规格，会无限执行。
        //和RepeatableSpec本质上没有区别，只有一个区别，无限和有限。
        offsetXAnim.animateTo(offsetX, infiniteRepeatable(tween(),RepeatMode.Reverse))
    })
    Box(modifier = Modifier
        .padding(offsetXAnim.value, 0.dp, 0.dp, 0.dp)
        .requiredSize(100.dp)
        .background(Color.Blue)
        .clickable { change = !change })

}


@Preview(showBackground = true)
@Composable
fun previewAnimationSpec() {
    ComposeTheme {
        TestRepeatableSpec()
    }
}