package com.afzaln.funcompose.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.navigation.AmbientNavController
import androidx.compose.navigation.NavHost
import androidx.compose.navigation.composable
import androidx.compose.navigation.rememberNavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * The navController is created automatically by
 * the NavHost composable and is only available
 * inside the NavGraph using AmbientNavController.current
 */
@Composable
fun VeryBasicNav() {
    NavHost(startDestination = "Profile") {
        composable("Profile") {
            Profile()
        }
        composable("Dashboard") {
            Dashboard()
        }
        composable("Scrollable") {
            // check the Scrollable code to see why
            // we're passing the navGraphBuilder to it!
            Scrollable(this)
        }
    }
}

/**
 * An existing navController can be passed to the NavHost.
 * This functionality can be used to control and observe
 * the backstack from outside of the NavGraph.
 */
@Composable
fun BasicNav(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "Profile"
    ) {
        composable("Profile") {
            val current = AmbientNavController.current.currentDestination
            Crossfade(current = current) {
                Profile()
            }
        }
        composable("Dashboard") {
            val current = AmbientNavController.current.currentDestination
            Crossfade(current = current) {
                Dashboard()
            }
        }
        composable("Scrollable") {
            val current = AmbientNavController.current.currentDestination
            Crossfade(current = current) {
                Scrollable(this)
            }
        }
    }
}
