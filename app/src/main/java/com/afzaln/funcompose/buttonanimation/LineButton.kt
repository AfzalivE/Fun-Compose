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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.afzaln.funcompose.ui.FunComposeTheme
import com.afzaln.funcompose.ui.purple

@Composable
fun LineButton() {
    var press by remember { mutableStateOf(false) }
    val yPos = animate(
        if (!press) 16.dp else 0.dp, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val xPos = animate(
        if (!press) 0.dp else (-90).dp, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val lineWidth = animate(
        if (!press) 64.dp else 54.dp, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // LowBouncy because of https://issuetracker.google.com/issues/171727052
    val lineHeight = animate(
        if (!press) 4.dp else 50.dp, spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val iconOpacity = animate(if (!press) 0f else 1f)
    val cornerRadius = animate(if (!press) 4.dp else 16.dp)

    val lightGray = Color(0xFFD8E6FB)
    val otherGray = Color(red = 0.7152195573f, green = 0.7906422615f, blue = 0.908705771f, alpha = 0.3f)

    val firstColor = animate(
        if (!press) lightGray else Color.White, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val secondColor = animate(
        if (!press) Color.White else lightGray, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // NoBouncy because of https://issuetracker.google.com/issues/171727052
    val secondOutlineColor = animate(
        if (!press) Color.White else otherGray, spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // NoBouncy because of https://issuetracker.google.com/issues/171727052
    val firstOutlineColor = animate(
        if (!press) otherGray else Color.White, spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val buttonWidth = 200.dp
    val buttonHeight = 60.dp

    val shadow = mutableStateOf(
        animate(
            if (!press) -30f else 20f, spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    )

    val shadowElevation = animate(
        if (!press) 10.dp else 0.dp, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = Modifier
            .preferredSize(buttonWidth, buttonHeight)
            .offsetPx(x = shadow, y = shadow)
            .drawShadow(
                elevation = 25.dp,
                shape = RoundedCornerShape(20.dp),
            )
    )

    Box(
        alignment = Alignment.Center,
        modifier = Modifier
            .drawShadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .preferredSize(buttonWidth, buttonHeight)
            .gradientWithOutline(
                firstColor,
                secondColor,
                firstOutlineColor,
                secondOutlineColor
            )
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
            Text(
                "Button", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            IconBox(xPos, yPos, lineWidth, lineHeight, cornerRadius, iconOpacity)
        }
    }
}

@Composable
private fun IconBox(
    xPos: Dp,
    yPos: Dp,
    lineWidth: Dp,
    lineHeight: Dp,
    cornerRadius: Dp,
    iconOpacity: Float
) {
    Box(
        modifier = Modifier
            .offset(xPos, yPos)
            .drawShadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
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

private fun Modifier.gradientWithOutline(
    topLeftColor: Color,
    bottomRightColor: Color,
    outlineTopLeftColor: Color,
    outlineBottomRightColor: Color
): Modifier {
    return drawBehind {
        val outline2 = RoundedCornerShape(16.dp).createOutline(size = size, density = this)
        LinearGradient(
            colors = listOf(topLeftColor, bottomRightColor),
            Offset.Zero.x, Offset.Zero.y, size.width, size.height
        ).let {
            drawOutline(
                outline = outline2,
                brush = it
            )
        }

        val outline1 = RoundedCornerShape(16.dp).createOutline(size = size, density = this)
        LinearGradient(
            colors = listOf(outlineTopLeftColor, outlineBottomRightColor),
            Offset.Zero.x, Offset.Zero.y, size.width, size.height
        ).let {
            drawOutline(
                outline = outline1,
                brush = it,
                style = Stroke(
                    width = 8.dp.value
                )
            )
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
