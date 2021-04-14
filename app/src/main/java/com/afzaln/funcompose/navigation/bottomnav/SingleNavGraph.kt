package com.afzaln.funcompose.navigation.bottomnav

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
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
    var currentTab by rememberSaveable(saver = ScreenSaver()) { mutableStateOf(Screen.Profile) }

    FunComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(currentTab.route)
                        }
                    )
                },
                content = {
                    bodyContent(currentTab)
                },
                bottomBar = {
                    BottomNavigation {
                        BottomNavigationItem(
                            icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
                            label = { Text("Profile") },
                            selected = currentTab == Screen.Profile,
                            onClick = { currentTab = Screen.Profile }
                        )
                        BottomNavigationItem(
                            icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null) },
                            label = { Text("Dashboard") },
                            selected = currentTab == Screen.Dashboard,
                            onClick = { currentTab = Screen.Dashboard }
                        )
                        BottomNavigationItem(
                            icon = { Icon(imageVector = Icons.Default.List, contentDescription = null) },
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
    val dashboardNavState = rememberSaveable(saver = NavStateSaver()) { mutableStateOf(Bundle()) }

    when (screen) {
        Screen.Profile -> ProfileTab()
        Screen.Dashboard -> DashboardTab(dashboardNavState)
        Screen.Phrases -> Phrases()
        else -> ProfileTab()
    }
}

@Composable
fun ProfileTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(8.dp))
    ) {
        Text(text = Screen.Profile.route)
    }
}

@Composable
fun DashboardTab(navState: MutableState<Bundle>) {
    val navController = rememberNavController()

    DisposableEffect(Unit) {
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
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) { Dashboard(navController) }
        composable(Screen.DashboardDetail.route) {
            Text("Some Dashboard detail")
        }
    }
}

@Composable
fun Dashboard(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(8.dp))
    ) {
        Text(text = Screen.Dashboard.route)
        Button(
            content = { Text("Open Dashboard Detail") },
            onClick = {
                navController.navigate(Screen.DashboardDetail.route)
            }
        )
    }
}

// /**
// * Saver to save and restore the current tab across config change and process death.
// */
fun ScreenSaver(): Saver<MutableState<Screen>, *> = Saver(
    save = { it.value.saveState() },
    restore = { mutableStateOf(Screen.restoreState(it)) }
)
//
fun NavStateSaver(): Saver<MutableState<Bundle>, out Any> = Saver(
    save = { it.value },
    restore = { mutableStateOf(it) }
)
