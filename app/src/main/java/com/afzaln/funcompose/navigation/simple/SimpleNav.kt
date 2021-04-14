package com.afzaln.funcompose.navigation.simple

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.afzaln.funcompose.navigation.Screen
import com.afzaln.funcompose.navigation.phrases
import com.afzaln.funcompose.ui.FunComposeTheme

@Composable
fun SimpleNavApp() {
    FunComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            SimpleNav()
        }
    }
}

/**
 * The navController is created automatically by
 * the NavHost composable and is only available
 * inside the NavGraph using AmbientNavController.current
 */
@Composable
fun SimpleNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Profile.route) {
        composable(Screen.Profile.route) { Profile(navController) }
        composable(Screen.Dashboard.routeWithArg) { backStackEntry ->
            Dashboard(
                title = backStackEntry.arguments?.getString("arg") ?: ""
            )
        }
    }
}

@Composable
fun Profile(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .then(Modifier.padding(8.dp))) {
        Text(text = Screen.Profile.route)
        Button(
            onClick = { navController.navigate(Screen.Dashboard.withArg("Args from Profile")) },
        ) {
            Text("Open Dashboard")
        }
    }
}

@Composable
fun Dashboard(title: String = Screen.Dashboard.route) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(8.dp)),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = title)
        Button(
            content = { Text("Useless button") },
            onClick = {}
        )
    }
}

@Composable
fun Phrases(navController: NavController? = null) {
    Column(modifier = Modifier
        .fillMaxSize()
        .then(Modifier.padding(8.dp))) {
        LazyColumn {
            items(phrases) {
                if (navController != null) {
                    ListItem(
                        text = { Text(text = it) },
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(
                                Screen.PhraseDetail.routeWithPhrase(it)
                            )
                        })
                    )
                } else {
                    ListItem(text = { Text(text = it) })
                }
            }
        }
    }
}

