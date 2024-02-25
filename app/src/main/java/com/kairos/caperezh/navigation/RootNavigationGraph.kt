package com.kairos.caperezh.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kairos.caperezh.presentation.BaseScreen
import com.kairos.caperezh.presentation.SplashView

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        composable(route = Graph.SPLASH) {
            SplashView(navController)
        }
        composable(route = Graph.HOME) {
            BaseScreen()
        }
    }
}
