package com.kairos.caperezh.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.kairos.caperezh.R

sealed class BottomBar(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object CHARACTER : BottomBar(
        route = RoutesButtonBar.CHARACTER,
        title = R.string.label_character,
        icon = Icons.Outlined.Person
    )

    object LOCATION : BottomBar(
        route = RoutesButtonBar.LOCATION,
        title = R.string.label_location,
        icon = Icons.Outlined.LocationOn
    )

    object EPISODE : BottomBar(
        route = RoutesButtonBar.EPISODE,
        title = R.string.label_episode,
        icon = Icons.Outlined.List
    )
}