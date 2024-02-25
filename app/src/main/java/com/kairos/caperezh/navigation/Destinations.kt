package com.kairos.caperezh.navigation

sealed class Destinations(val route: String) {
    object SplashScreenDest: Destinations(route = "splash_screen")
    object DetailCharacter: Destinations(route = "detail_screen_character/{result}")
    object DetailLocation: Destinations(route = "detail_screen_location/{result}")
    object DetailEpisode: Destinations(route = "detail_screen_episode/{result}")
}