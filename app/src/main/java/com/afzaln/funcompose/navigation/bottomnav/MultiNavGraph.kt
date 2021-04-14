package com.afzaln.funcompose.navigation.bottomnav

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afzaln.funcompose.navigation.PhraseDetail
import com.afzaln.funcompose.navigation.Screen
import com.afzaln.funcompose.navigation.simple.Phrases

@Composable
fun MultiBottomNavApp() {
    BottomNavApp {
        MultiNavTabContent(screen = it)
    }
}

@Composable
fun MultiNavTabContent(screen: Screen) {
    val dashboardNavState = rememberSaveable(saver = NavStateSaver()) { mutableStateOf(Bundle()) }
    val phrasesNavState = rememberSaveable(saver = NavStateSaver()) { mutableStateOf(Bundle()) }
    when (screen) {
        Screen.Profile -> ProfileTab()
        Screen.Dashboard -> DashboardTab(dashboardNavState)
        Screen.Phrases -> NavPhrases(phrasesNavState)
        else -> ProfileTab()
    }
}

@Composable
fun NavPhrases(navState: MutableState<Bundle>) {
    val navController = rememberNavController()

    DisposableEffect(Unit) {
        val callback = NavController.OnDestinationChangedListener { navController, _, _ ->
            navState.value = navController.saveState() ?: Bundle()
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
        startDestination = Screen.Phrases.route
    ) {
        composable(Screen.Phrases.route) { Phrases(navController) }
        composable(Screen.PhraseDetail.route) { PhraseDetail(it.arguments?.get("phrase") as String) }
    }
}
