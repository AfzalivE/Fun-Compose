package com.afzaln.funcompose.navigation.bottomnav

import android.os.Bundle
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.afzaln.funcompose.navigation.Screen
import com.afzaln.funcompose.navigation.simple.Phrases
import com.afzaln.funcompose.ui.FunComposeTheme

@Composable
fun SingleBottomNavApp() {
    BottomNavApp {
        SingleNavTabContent(screen = it)
    }
}

@Composable
fun BottomNavApp(
    bodyContent: @Composable (Screen) -> Unit
) {
    var currentTab by rememberSavedInstanceState(saver = ScreenSaver()) { mutableStateOf(Screen.Profile) }

    FunComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(currentTab.title)
                        }
                    )
                }, bodyContent = {
                    bodyContent(currentTab)
                },
                bottomBar = {
                    BottomNavigation {
                        BottomNavigationItem(
                            icon = { Icon(asset = Icons.Default.AccountCircle) },
                            label = { Text("Profile") },
                            selected = currentTab == Screen.Profile,
                            onClick = { currentTab = Screen.Profile }
                        )
                        BottomNavigationItem(
                            icon = { Icon(asset = Icons.Default.ShoppingCart) },
                            label = { Text("Dashboard") },
                            selected = currentTab == Screen.Dashboard,
                            onClick = { currentTab = Screen.Dashboard }
                        )
                        BottomNavigationItem(
                            icon = { Icon(asset = Icons.Default.List) },
                            label = { Text("Phrases") },
                            selected = currentTab == Screen.Phrases,
                            onClick = { currentTab = Screen.Phrases }
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun SingleNavTabContent(screen: Screen) {
    val dashboardNavState = rememberSavedInstanceState(saver = NavStateSaver()) { mutableStateOf(Bundle()) }

    when (screen) {
        Screen.Profile -> ProfileTab()
        Screen.Dashboard -> DashboardTab(dashboardNavState)
        Screen.Phrases -> Phrases()
        else -> ProfileTab()
    }
}

@Composable
fun ProfileTab() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Profile.title)
    }
}

@Composable
fun DashboardTab(navState: MutableState<Bundle>) {
    val navController = rememberNavController()

    onCommit {
        val callback = NavController.OnDestinationChangedListener { controller, _, _ ->
            navState.value = controller.saveState() ?: Bundle()
        }
        navController.addOnDestinationChangedListener(callback)
        navController.restoreState(navState.value)

        onDispose {
            navController.removeOnDestinationChangedListener(callback)
            // workaround for issue where back press is intercepted
            // outside this tab, even after this Composable is disposed
            navController.enableOnBackPressed(false)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard
    ) {
        composable(Screen.Dashboard) { Dashboard(navController) }
        composable(Screen.DashboardDetail) {
            Text("Some Dashboard detail")
        }
    }
}

@Composable
fun Dashboard(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Dashboard.title)
        Button(
            content = { Text("Open Dashboard Detail") },
            onClick = {
                navController.navigate(Screen.DashboardDetail)
            }
        )
    }
}

/**
 * Saver to save and restore the current tab across config change and process death.
 */
fun ScreenSaver(
): Saver<MutableState<Screen>, *> = Saver(
    save = { it.value.saveState() },
    restore = { mutableStateOf(Screen.restoreState(it)) }
)

fun NavStateSaver(): Saver<MutableState<Bundle>, out Any> = Saver(
    save = { it.value },
    restore = { mutableStateOf(it) }
)
