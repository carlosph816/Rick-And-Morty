package com.kairos.caperezh.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kairos.caperezh.R
import com.kairos.caperezh.navigation.Destinations
import com.kairos.caperezh.navigation.Graph
import kotlinx.coroutines.delay

@Composable
fun SplashView(navController: NavController){
    val scaleAnimation: Animatable<Float, AnimationVector1D> = remember { Animatable(initialValue = 0f) }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        navController = navController,
        durationMillisAnimation = 4500,
        delayScreen = 0L
    )

    DesignSplashScreen(
        imagePainter = painterResource(id = R.drawable.rickylogo),
        scaleAnimation = scaleAnimation
    )
}

@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    navController: NavController,
    durationMillisAnimation: Int,
    delayScreen: Long
) {

    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = 0.5F,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(3F).getInterpolation(it)
                }
            )
        )
        delay(timeMillis = delayScreen)
        navController.navigate(route = Graph.HOME) {
            popUpTo(route = Destinations.SplashScreenDest.route) {
                inclusive = true
            }
        }
    }
}

@Composable
fun DesignSplashScreen(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    scaleAnimation: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(255, 255, 255, 255),
                        Color(255, 255, 255, 255),
                        Color(255, 255, 255, 255),
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = imagePainter,
                contentDescription = "Splash Screen",
                modifier = modifier
                    .size(400.dp)
                    .scale(scale = scaleAnimation.value),
            )

            /*Text(
                text = stringResource(R.string.app_name),
                color = Color.Blue,
                fontSize = 45.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                modifier = modifier.scale(scale = scaleAnimation.value)
            ) */
        }
    }
}