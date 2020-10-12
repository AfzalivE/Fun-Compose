package com.afzaln.funcompose.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.navigation.currentBackStackEntryAsState
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
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text(current?.destination?.toScreen()?.title ?: "")
                })
            }, bodyContent = {
                BasicNav(navController)
            })
        }
    }
}
