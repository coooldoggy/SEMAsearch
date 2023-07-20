package com.coooldoggy.semasearch.ui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.coooldoggy.semasearch.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen(onDoneSplash: () -> Unit) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(
            animationSpec = keyframes {
                this.durationMillis = 1000
            },
        ),
        exit = fadeOut(
            animationSpec = keyframes {
                this.durationMillis = 500
            },
        ),
    ) {
        if (this.transition.currentState == this.transition.targetState) {
            onDoneSplash.invoke()
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AsyncImage(model = R.drawable.logo, contentDescription = "서울시립미술관")
        }
    }
}
