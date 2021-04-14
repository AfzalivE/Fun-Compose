package com.afzaln.funcompose.navigation.simple

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.afzaln.funcompose.navigation.Screen
import com.afzaln.funcompose.navigation.getRoute
import com.afzaln.funcompose.ui.FunComposeTheme

@Composable
fun TopBarNavApp() {
    val navController = rememberNavController()
    val current by navController.currentBackStackEntryAsState()

    FunComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(current?.getRoute() ?: "")
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.Dashboard.route)
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                            }
                        }
                    )
                }, content = {
                    BasicNav(navController)
                }
            )
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
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Profile.route
    ) {
        composable(Screen.Profile.route) { Profile(navController) }
        composable(Screen.Dashboard.route) { Dashboard() }
    }
}
