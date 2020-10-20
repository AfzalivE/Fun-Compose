package com.afzaln.funcompose.buttonanimation

import androidx.compose.animation.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.afzaln.funcompose.ui.FunComposeTheme


@Composable
fun LineButton() {
    var press by remember { mutableStateOf(false) }
    val yPos = animate(if (!press) 10.dp else 0.dp)
    val xPos = animate(if (!press) 0.dp else (-40).dp)
    val width = animate(if (!press) 48.dp else 20.dp)
    val height = animate(if (!press) 2.dp else 20.dp)
    val iconOpacity = animate(if (!press) 0f else 1f)
    val cornerRadius = animate(if (!press) 1.dp else 6.dp)

    Button(
        backgroundColor = Color.LightGray,
        onClick = {
            press = !press
        }) {
        Box(
            alignment = Alignment.Center
        ) {
            Text("Button")
            Box(
                alignment = Alignment.Center,
                modifier = Modifier.offset(
                    xPos, yPos
                ).then(Modifier.size(width, height))
//                        .then(Modifier.offset(70.dp, 16.dp))
                    .then(
                        Modifier.background(
                            color = Color.Magenta,
                            shape = RoundedCornerShape(CornerSize(cornerRadius))
                        )
                    )
            ) {
                Box(
                    modifier =
                    Modifier
                        .then(Modifier.drawOpacity(iconOpacity))
                        .then(Modifier.size(14.dp, 14.dp))
                        .then(Modifier.border(1.dp, Color.White, RoundedCornerShape(7.dp)))
                ) {
                    Image(
                        asset = Icons.Default.Person,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FunComposeTheme {
        LineButton()
    }
}
