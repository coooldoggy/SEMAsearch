package com.coooldoggy.semasearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.coooldoggy.semasearch.ui.presentation.SEMASearchApp
import com.coooldoggy.semasearch.ui.theme.SEMAsearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEMAsearchTheme {
                SEMASearchApp()
            }
        }
    }
}