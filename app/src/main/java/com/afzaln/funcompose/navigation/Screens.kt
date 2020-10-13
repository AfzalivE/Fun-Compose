package com.afzaln.funcompose.navigation

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.ListItem
import androidx.compose.navigation.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*

sealed class Screen(val title: String) {
    object Profile : Screen("Profile")
    object Dashboard : Screen("Dashboard")
    object Scrollable : Screen("Scrollable")
    object PhraseDetail : Screen("PhraseDetail")
    object DashboardDetail : Screen("DashboardDetail")

    /** hack to generate the same Destination ID that the Compose Navigation lib generates **/
    val id: Int
        get() {
            return title.hashCode() + 0x00010000
        }

}

fun NavDestination.toScreen(): Screen {
    return when (id) {
        Screen.Profile.id -> Screen.Profile
        Screen.Dashboard.id -> Screen.Dashboard
        Screen.Scrollable.id -> Screen.Scrollable
        Screen.PhraseDetail.id -> Screen.PhraseDetail
        else -> Screen.Profile
    }
}

@Composable
fun TabContent(screen: Screen) {
    when (screen) {
        Screen.Profile -> Profile()
        Screen.Dashboard -> NavDashboard()
        Screen.Scrollable -> NoClickScrollable()
        else -> Profile()
    }
}

@Composable
fun NavDashboard() {
    NavHost(
        startDestination = "Dashboard"
    ) {
        composable(Screen.Dashboard.title) {
            Dashboard()
        }
        composable(Screen.DashboardDetail.title) {
            Text("Some Dashboard detail")
        }
    }
}

@Composable
fun Profile() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Profile.title)
    }
}

@Composable
fun Dashboard() {
    val navController = AmbientNavController.current
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Dashboard.title)
        Button(
            content = { Text("Open Dashboard Detail") },
            onClick = {
                navController.navigate(Screen.DashboardDetail.title)
            }
        )
    }
}

/**
 * An example of how to add a dynamic destination to
 * an existing NavGraph but doing this will cause a crash
 * on process restore. Possibly because this destination
 * doesn't exist in the NavGraph.
 */
@Composable
fun NoClickScrollable() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        LazyColumnFor(items = phrases) {
            ListItem(
                text = { Text(text = it) },
            )
        }
    }
}

/**
 * An example of how to add a dynamic destination to
 * an existing NavGraph
 */
@Composable
fun Scrollable(navGraphBuilder: NavGraphBuilder) {
    val navController = AmbientNavController.current
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        navController.graph.addDestination(
            ComposeNavigator.Destination(
                navGraphBuilder.provider[ComposeNavigator::class]
            ) {
                PhraseDetail()
            }.apply { id = Screen.PhraseDetail.id })

        LazyColumnFor(items = phrases) {
            ListItem(
                text = { Text(text = it) },
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screen.PhraseDetail.title)
                })
            )
        }
    }
}

@Composable
fun PhraseDetail() {
    Text(text = "This will have better data when NavArgs support is added soon!")
}

private val phrases = listOf(
    "Easy As Pie",
    "Wouldn't Harm a Fly",
    "No-Brainer",
    "Keep On Truckin'",
    "An Arm and a Leg",
    "Down To Earth",
    "Under the Weather",
    "Up In Arms",
    "Cup Of Joe",
    "Not the Sharpest Tool in the Shed",
    "Ring Any Bells?",
    "Son of a Gun",
    "Hard Pill to Swallow",
    "Close But No Cigar",
    "Beating a Dead Horse",
    "If You Can't Stand the Heat, Get Out of the Kitchen",
    "Cut To The Chase",
    "Heads Up",
    "Goody Two-Shoes",
    "Fish Out Of Water",
    "Cry Over Spilt Milk",
    "Elephant in the Room",
    "There's No I in Team",
    "Poke Fun At",
    "Talk the Talk",
    "Know the Ropes",
    "Fool's Gold",
    "It's Not Brain Surgery",
    "Fight Fire With Fire",
    "Go For Broke"
)
