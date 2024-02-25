package com.kairos.caperezh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.kairos.caperezh.navigation.RootNavigationGraph
import com.kairos.caperezh.ui.theme.KairosTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KairosTestTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}