package com.kairos.caperezh.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kairos.caperezh.R
import com.kairos.caperezh.navigation.BottomBar
import com.kairos.caperezh.ui.theme.KairosTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    navController: NavHostController = rememberNavController()
) {
    val showNavigationIcon = remember { mutableStateOf(false) }
    val dp8 = KairosTestTheme.sizes.smallerSize
    val dp32 = KairosTestTheme.sizes.mediumSize
    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                HomeNavGraph(
                    showArrow = {
                        showNavigationIcon.value = true
                    },
                    navController = navController
                )
            }
        },
        /*topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    if (showNavigationIcon.value) {
                        IconButton(onClick = {
                            val navStack = navController.previousBackStackEntry != null
                            if (navStack){
                                showNavigationIcon.value = false
                            }
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                }
            )
        }, */
        bottomBar = { BottomBarComponent(navController = navController) }

    )

}

@Composable
fun BottomBarComponent(navController: NavHostController) {
    val screens = listOf(
        BottomBar.CHARACTER,
        BottomBar.LOCATION,
        BottomBar.EPISODE,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
            screens.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = stringResource(id = item.title),
                            tint = Color.White
                        )
                    },
                    label = { Text(stringResource(id = item.title), color = Color.White) },
                    selected = currentDestination?.hierarchy?.any {
                        it.route == screens[index].route
                    } == true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(item.route)
                    }
                )
            }
        }
    }
}
