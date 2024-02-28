package com.kairos.caperezh.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kairos.caperezh.data.response.toDetail
import com.kairos.caperezh.navigation.Destinations
import com.kairos.caperezh.navigation.Graph
import com.kairos.caperezh.navigation.RoutesButtonBar
import com.kairos.caperezh.navigation.charactersGraph
import com.kairos.caperezh.navigation.episodesGraph
import com.kairos.caperezh.navigation.locationsGraph
import com.kairos.caperezh.presentation.characters.CharacterView
import com.kairos.caperezh.presentation.episodes.EpisodesView
import com.kairos.caperezh.presentation.locations.LocationsView

@Composable
fun HomeNavGraph(
    showArrow: ()->Unit,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = RoutesButtonBar.CHARACTER
    ) {
        composable(route = RoutesButtonBar.CHARACTER) {
            CharacterView(
                navigateToDetail = {
                showArrow()
                val gson: Gson = GsonBuilder().create()
                val userJson = gson.toJson(it.toDetail())
                navController.navigate(route = Destinations.DetailCharacter.route.replace(
                    oldValue = "{result}",
                    newValue = userJson
                ))
            })

        }
        composable(route = RoutesButtonBar.LOCATION) {
            LocationsView(navigateToDetail = {
                showArrow()
                val gson: Gson = GsonBuilder().create()
                val userJson = gson.toJson(it.toDetail())
                navController.navigate(route = Destinations.DetailLocation.route.replace(
                    oldValue = "{result}",
                    newValue = userJson
                ))
            })
        }
        composable(route = RoutesButtonBar.EPISODE) {
            EpisodesView(navigateToDetail = {
                showArrow()
                val gson: Gson = GsonBuilder().create()
                val userJson = gson.toJson(it.toDetail())
                navController.navigate(route = Destinations.DetailEpisode.route.replace(
                    oldValue = "{result}",
                    newValue = userJson
                ))
            })
        }
        charactersGraph(navController =  navController)
        locationsGraph(navController =  navController)
        episodesGraph(navController =  navController)
    }
}
