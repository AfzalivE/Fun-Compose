package com.afzaln.funcompose.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
                    TabContent(currentTab)
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
                            label = { Text("Scrollable") },
                            selected = currentTab == Screen.Scrollable,
                            onClick = { currentTab = Screen.Scrollable }
                        )
                    }
                })
        }
    }
}

/**
 * Saver to save and restore the current tab across config change and process death.
 */
private fun ScreenSaver(
): Saver<MutableState<Screen>, *> = Saver(
    save = { it.value.saveState() },
    restore = { mutableStateOf(Screen.restoreState(it)) }
)
