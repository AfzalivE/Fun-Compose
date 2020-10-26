package com.afzaln.funcompose.buttonanimation

import androidx.compose.animation.animate
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.afzaln.funcompose.ui.FunComposeTheme
import com.afzaln.funcompose.ui.purple

@Composable
fun LineButton() {
    var press by remember { mutableStateOf(false) }
    val yPos = animate(if (!press) 16.dp else 0.dp)
    val xPos = animate(
        if (!press) 0.dp else (-90).dp, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val lineWidth = animate(if (!press) 64.dp else 54.dp)
    val lineHeight = animate(if (!press) 4.dp else 50.dp)
    val iconOpacity = animate(if (!press) 0f else 1f)
    val cornerRadius = animate(if (!press) 1.dp else 16.dp)

    val lightGray = Color(0xFFD8E6FB)

    val firstColor = animate(if (!press) lightGray else Color.White)
    val secondColor = animate(if (!press) Color.White else lightGray)

    val buttonWidth = 200.dp
    val buttonHeight = 60.dp

    Box(
        alignment = Alignment.Center,
        modifier = Modifier
            .drawShadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .preferredSize(buttonWidth, buttonHeight)
            .drawBehind {
                val outline2 = RoundedCornerShape(16.dp).createOutline(size = size, density = this)
                LinearGradient(
                    colors = listOf(firstColor, secondColor),
                    Offset.Zero.x, Offset.Zero.y, size.width, size.height
                ).let {
                    drawOutline(
                        outline = outline2,
                        brush = it,
                    )
                }
            }
            .toggleable(
                indication = null,
                value = press,
                onValueChange = {
                    press = it
                }
            )
    ) {
        Box(
            alignment = Alignment.Center
        ) {
            Text("Button")
            Box(
                modifier = Modifier
                    .offset(xPos, yPos)
                    .drawShadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp),
                        clip = false
                    )
                    .size(lineWidth, lineHeight)
                    .background(
                        color = purple,
                        shape = RoundedCornerShape(CornerSize(cornerRadius))
                    )
            ) {
                Box(
                    modifier = Modifier
                        .drawOpacity(iconOpacity)
                        .padding(14.dp, 14.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(7.dp))
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
