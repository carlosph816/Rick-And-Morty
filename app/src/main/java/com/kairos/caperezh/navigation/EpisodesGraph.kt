package com.kairos.caperezh.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.episodes.DetailEpisodeView

fun NavGraphBuilder.episodesGraph(
    navController: NavHostController
){
    navigation(
        route = Graph.EPISODE_GRAPH,
        startDestination = Destinations.DetailEpisode.route
    ) {
        composable(
            route = Destinations.DetailEpisode.route
        ) { navBackStackEntry ->
            val gson: Gson = GsonBuilder().create()
            val userJson = navBackStackEntry.arguments?.getString("result")
            val userObject = gson.fromJson(userJson, Result::class.java)
            DetailEpisodeView(
                navController = navController,
                data = userObject
            )
        }
    }
}
