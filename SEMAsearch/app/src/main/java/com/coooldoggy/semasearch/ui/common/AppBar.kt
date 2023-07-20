package com.coooldoggy.semasearch.ui.common

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.util.onClick

@Composable
fun AppBar(
    leftContent: (@Composable () -> Unit)? = null,
    centerContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    backgroundColor: Color = Color.White,
    clickListener: (() -> Unit) = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = backgroundColor)
            .onClick {
                clickListener.invoke()
            },
        contentAlignment = Alignment.Center,
    ) {
        centerContent?.let {
            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                centerContent()
            }
        }

        leftContent?.let {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leftContent()
            }
        }

        rightContent?.let {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                rightContent()
            }
        }
    }
}

@Composable
fun AppBarDefaultIcon(
    iconResId: Int,
    iconClickListener: (() -> Unit)? = null,
) {
    Image(
        modifier = Modifier
            .padding(start = 4.dp, end = 16.dp)
            .size(26.dp)
            .onClick {
                iconClickListener?.invoke()
            },
        painter = painterResource(id = iconResId),
        contentDescription = "",
    )
}

@Composable
fun AppBarBackIcon(
    iconResId: Int = R.drawable.baseline_arrow_back_24,
    action: () -> Unit,
) {
    AppBarDefaultIcon(
        iconResId = iconResId,
        iconClickListener = {
            action.invoke()
        },
    )
}

@SuppressLint("ResourceType")
@Composable
fun BoxScope.AppBarWithText(@IdRes textId: Int) {
    AppBar(leftContent = {
        Text(
            text = stringResource(id = textId),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp).align(Alignment.TopStart),
        )
    })
}
