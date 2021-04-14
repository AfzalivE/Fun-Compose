package com.afzaln.funcompose.navigation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.afzaln.funcompose.navigation.bottomnav.MultiBottomNavApp
import com.afzaln.funcompose.navigation.bottomnav.OldMultiBottomNavApp

class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiBottomNavApp()
        }
    }
}
