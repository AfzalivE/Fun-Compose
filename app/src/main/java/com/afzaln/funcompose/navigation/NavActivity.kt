package com.afzaln.funcompose.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.navigation.currentBackStackEntryAsState
import androidx.compose.navigation.navigate
import androidx.compose.navigation.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import com.afzaln.funcompose.ui.FunComposeTheme

class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunComposeApp()
        }
    }
}

@Composable
private fun FunComposeApp() {
    val navController = rememberNavController()
    val current by navController.currentBackStackEntryAsState()

    FunComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(current?.destination?.toScreen()?.title ?: "")
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.Dashboard.title)
                                }, icon = {
                                    Icon(asset = Icons.Default.Settings)
                                }
                            )
                        }
                    )
                }, bodyContent = {
                    BasicNav(navController)
                })
        }
    }
}
