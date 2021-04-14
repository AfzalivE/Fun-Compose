package com.afzaln.funcompose.navigation

import android.os.Bundle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.KEY_ROUTE

fun NavBackStackEntry.getRoute(): String {
    return arguments?.getString(KEY_ROUTE) ?: ""
}

sealed class Screen(val route: String) {
    object Profile : Screen("Profile")
    object Dashboard : Screen("Dashboard") {
        val routeWithArg: String = "$route?arg={arg}"
        fun withArg(arg: String): String = routeWithArg.replace("{arg}", arg)
    }
    object Phrases : Screen("Phrases")
    object PhraseDetail : Screen("PhraseDetail?phrase={phrase}") {
        fun routeWithPhrase(phrase: String): String = route.replace("{phrase}", phrase)
    }
    object DashboardDetail : Screen("DashboardDetail")

//
    fun saveState(): Bundle {
        return bundleOf(KEY_SCREEN to route)
    }

    companion object {
        fun restoreState(bundle: Bundle): Screen {
            val title = bundle.getString(KEY_SCREEN, Profile.route)
            return when (title) {
                Profile.route -> Profile
                Dashboard.route -> Dashboard
                DashboardDetail.route -> DashboardDetail
                Phrases.route -> Phrases
                PhraseDetail.route -> PhraseDetail
                else -> Profile
            }
        }

        const val KEY_SCREEN = "route"
    }
}

@Composable
fun PhraseDetail(phrase: String = "no phrase") {
    Text(text = phrase)
}

internal val phrases = listOf(
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
