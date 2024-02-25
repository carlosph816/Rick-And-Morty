package com.kairos.caperezh.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.locations.DetailLocationView

fun NavGraphBuilder.locationsGraph(){
    navigation(
        route = Graph.LOCATION_GRAPH,
        startDestination = Destinations.DetailLocation.route
    ) {
        composable(
            route = Destinations.DetailLocation.route,
        ) { navBackStackEntry ->
            val gson: Gson = GsonBuilder().create()
            val userJson = navBackStackEntry.arguments?.getString("result")
            val userObject = gson.fromJson(userJson, Result::class.java)
            DetailLocationView(data = userObject)
        }
    }
}