package com.afzaln.funcompose.buttonanimation

import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.animate
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonConstants
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.afzaln.funcompose.ui.FunComposeTheme
import com.afzaln.funcompose.ui.purple

val yPos = DpPropKey()
val xPos = DpPropKey()
val lineWidth = DpPropKey()
val lineHeight = DpPropKey()
val iconOpacity = FloatPropKey()
val cornerRadius = DpPropKey()

val topLeftColor = ColorPropKey()
val bottomRightColor = ColorPropKey()

val lightGray = Color(0xFFD8E6FB)

enum class ButtonState {
    INITIAL, NORMAL, PRESSED
}

fun defineButtonTransitions(): TransitionDefinition<ButtonState> {
    return transitionDefinition {
        state(ButtonState.INITIAL) {
            this[yPos] = 16.dp
            this[xPos] = 0.dp
            this[lineWidth] = 64.dp
            this[lineHeight] = 4.dp
            this[iconOpacity] = 0f
            this[cornerRadius] = 1.dp
            this[topLeftColor] = lightGray
            this[bottomRightColor] = Color.White
        }
        state(ButtonState.NORMAL) {
            this[yPos] = 16.dp
            this[xPos] = 0.dp
            this[lineWidth] = 64.dp
            this[lineHeight] = 4.dp
            this[iconOpacity] = 0f
            this[cornerRadius] = 1.dp
            this[topLeftColor] = lightGray
            this[bottomRightColor] = Color.White
        }
        state(ButtonState.PRESSED) {
            this[yPos] = 0.dp
            this[xPos] = (-90).dp
            this[lineWidth] = 54.dp
            this[lineHeight] = 50.dp
            this[iconOpacity] = 1f
            this[cornerRadius] = 16.dp
            this[topLeftColor] = Color.White
            this[bottomRightColor] = lightGray
        }

        transition(ButtonState.NORMAL to ButtonState.PRESSED) {
            yPos using spring(dampingRatio = Spring.DampingRatioLowBouncy)
        }
        transition(ButtonState.PRESSED to ButtonState.NORMAL) {
            yPos using spring(dampingRatio = Spring.DampingRatioLowBouncy)
        }
    }
}

@Composable
fun LineButton() {
    var press by remember { mutableStateOf(false) }
//    val yPos = animate(if (!press) 16.dp else 0.dp)
//    val xPos = animate(
//        if (!press) 0.dp else (-90).dp, spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//    val width = animate(if (!press) 64.dp else 54.dp)
//    val height = animate(if (!press) 4.dp else 50.dp)
//    val iconOpacity = animate(if (!press) 0f else 1f)
//    val cornerRadius = animate(if (!press) 1.dp else 16.dp)
//
//    val firstColor = animate(if (!press) lightGray else Color.White)
//    val secondColor = animate(if (!press) Color.White else lightGray)

    val otherGray = animate(if (!press) Color.White else Color(0x22B6C9E7))

    val bigWidth = 200.dp
    val bigHeight = 60.dp

    val buttonState = remember { mutableStateOf(ButtonState.INITIAL) }
    val transitionDef = defineButtonTransitions()

    val toState = if (buttonState.value == ButtonState.NORMAL) {
        ButtonState.PRESSED
    } else {
        ButtonState.NORMAL
    }

    val transitionState = transition(
        definition = transitionDef,
        initState = buttonState.value,
        toState = toState
    )

    Canvas(
        modifier = Modifier.preferredSize(200.dp, 60.dp)
    ) {

        val outline1 = RoundedCornerShape(16.dp).createOutline(size = size, density = this)
        SolidColor(
            value = otherGray,
        ).let {
            drawOutline(
                outline = outline1,
                brush = it,
                blendMode = BlendMode.SrcOver
            )
        }

        val outline2 = RoundedCornerShape(16.dp).createOutline(size = size, density = this)
        LinearGradient(
            colors = listOf(transitionState[topLeftColor], transitionState[bottomRightColor]),
            Offset.Zero.x, Offset.Zero.y, size.width, size.height
        ).let {
            drawOutline(
                outline = outline2,
                brush = it,
//                blendMode = BlendMode.DstOver
            )
        }
    }

    Box(
        modifier = Modifier
            .preferredSize(bigWidth, bigHeight)
//                Modifier.background(
//                    brush = LinearGradient(
//                        colors = listOf(firstColor, secondColor),
//                        Offset.Zero.x, Offset.Zero.y, bigWidth.value, bigHeight.value
//                    ),
//                    shape = RoundedCornerShape(16.dp)
//                )
            .toggleable(
                indication = null,
                value = press,
                onValueChange = {
                    press = it
                    buttonState.value = if (it) ButtonState.NORMAL else ButtonState.PRESSED
                }
            )
            .padding(ButtonConstants.DefaultContentPadding),
        alignment = Alignment.Center
    ) {
        Box(
            alignment = Alignment.Center
        ) {
            Text("Button")
            Box(
                alignment = Alignment.Center,
                modifier = Modifier.offset(
                    transitionState[xPos], transitionState[yPos]
                ).then(
                    Modifier.drawShadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                ).then(
                    Modifier.size(transitionState[lineWidth], transitionState[lineHeight])
                ).then(
                    Modifier.background(
                        color = purple,
                        shape = RoundedCornerShape(CornerSize(transitionState[cornerRadius]))
                    )
                )
            ) {
                Box(
                    modifier =
                    Modifier
                        .then(Modifier.drawOpacity(transitionState[iconOpacity]))
                        .then(Modifier.padding(14.dp, 14.dp))
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
