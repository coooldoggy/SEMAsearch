package com.coooldoggy.semasearch.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coooldoggy.imagesearchcompose.ui.common.ErrorData
import com.coooldoggy.imagesearchcompose.ui.common.NoItemData
import com.coooldoggy.semasearch.base.ResultData

@SuppressLint("ResourceType")
@Composable
fun NoItem(
    data: NoItemData,
) {
    val configuration = LocalConfiguration.current
    val rootHeight = if (data.changeHeight != 0) {
        data.changeHeight.dp
    } else {
        configuration.screenHeightDp.dp / 2
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(rootHeight),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = if (data.marginTop != 0) data.marginTop.dp else 110.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (data.image != 0) {
                Image(
                    painter = painterResource(id = data.image),
                    contentDescription = "",
                )
            }

            Text(
                modifier = Modifier
                    .padding(
                        top = if (data.image != 0) 16.dp else 0.dp,
                    )
                    .height(48.dp),
                lineHeight = 20.sp,
                text = data.title,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
            )

            if (data.buttonText.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(
                            top = 22.dp,
                        )
                        .widthIn(min = 200.dp)
                        .height(48.dp)
                        .background(
                            color = Color.Black,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = data.buttonText,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ErrorItem(
    errorItemData: ErrorData,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 110.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (errorItemData.image != 0) {
                Image(
                    painter = painterResource(id = errorItemData.image),
                    contentDescription = "",
                )
            }

            Text(
                modifier = Modifier
                    .padding(
                        top = if (errorItemData.image != 0) 16.dp else 0.dp,
                    )
                    .heightIn(min = 48.dp),
                lineHeight = 20.sp,
                text = errorItemData.title,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
            )

            if (errorItemData.buttonText.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(
                            top = 22.dp,
                        )
                        .widthIn(min = 200.dp)
                        .height(48.dp)
                        .background(
                            color = Color.Black,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = errorItemData.buttonText,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun ResultBox(modifier: Modifier = Modifier, loadingState: Boolean, errorState: ResultData.Error? = null, content: (@Composable () -> Unit)? = null) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        if (loadingState) {
            ProgressBarItem()
            if (content != null) {
                content()
            }
        } else if (errorState != null) {
            ErrorItem(
                errorItemData = ErrorData(
                    title = errorState.getMessage(),
                ),
            )
        } else {
            if (content != null) {
                content()
            }
        }
    }
}

@Composable
fun ProgressBarItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 30.dp,
                bottom = 30.dp,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            color = Color.Black,
        )
    }
}


@Composable
fun AddNoItem(noItemData: NoItemData) {
    NoItem(
        data = noItemData,
    )
}

@Composable
fun AddErrorItem(
    errorData: ErrorData,
) {
    ErrorItem(
        errorItemData = errorData,
    )
}
