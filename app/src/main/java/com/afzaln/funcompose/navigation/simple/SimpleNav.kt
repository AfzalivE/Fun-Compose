package com.afzaln.funcompose.navigation.simple

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.compose.AmbientNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
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
    NavHost(startDestination = Screen.Profile) {
        composable(Screen.Profile) { Profile() }
        composable(Screen.Dashboard) { backStackEntry ->
            Dashboard(
                title = backStackEntry.arguments?.get("title") as String
            )
        }
    }
}

@Composable
fun Profile() {
    val navController = AmbientNavController.current
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Profile.title)
        Button(
            onClick = { navController.navigate(Screen.Dashboard, bundleOf("title" to "Args from Profile")) },
        ) {
            Text("Open Dashboard")
        }
    }
}

@Composable
fun Dashboard(title: String = Screen.Dashboard.title) {
    Column(
        modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp)),
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
fun Phrases(clickable: Boolean = true) {
    val navController = AmbientNavController.current
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        LazyColumnFor(items = phrases) {
            if (clickable) {
                ListItem(
                    text = { Text(text = it) },
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate(
                            Screen.PhraseDetail,
                            bundleOf("phrase" to it)
                        )
                    })
                )
            } else {
                ListItem(text = { Text(text = it) })
            }
        }
    }
}

