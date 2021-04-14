package com.afzaln.funcompose.animations

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.afzaln.funcompose.buttonanimation.LineButton
import com.afzaln.funcompose.ui.FunComposeTheme

class AnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val lightGray = Color(0xFFD8E6FB)

        super.onCreate(savedInstanceState)
        setContent {
            FunComposeTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box(
                        modifier = Modifier.then(Modifier.fillMaxSize()).then(
                            Modifier.background(
                                color = lightGray
                            )
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        LineButton()
                    }
                }
            }
        }
    }
}
