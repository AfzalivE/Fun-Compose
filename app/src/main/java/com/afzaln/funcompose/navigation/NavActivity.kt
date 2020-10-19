package com.afzaln.funcompose.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.afzaln.funcompose.navigation.simple.SimpleNavApp

class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNavApp()
        }
    }
}
