package com.amadev.juniormath.ui.screen.components.appIcon

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amadev.juniormath.ui.theme.JuniorMathTheme


@Preview
@Composable
fun LogoPreview() {
    AppIcon()
}

@Composable
fun AppIcon(
    canvasSize: Float = 100f,
    colorPrimary: Color = MaterialTheme.colors.primary
) {
    val thinStroke = canvasSize * 0.25f
    JuniorMathTheme {
        Canvas(
            modifier = Modifier
                .size(canvasSize.dp)
                .background(color = MaterialTheme.colors.background)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawLine(
                color = Color.Black,
                start = Offset(x = 0f, y = canvasHeight / 2),
                end = Offset(x = canvasWidth, y = canvasHeight / 2),
                strokeWidth = thinStroke,
                cap = StrokeCap.Round
            )

            drawLine(
                color = Color.Black,
                start = Offset(x = canvasWidth / 2, y = 0f),
                end = Offset(x = canvasWidth / 2, y = canvasHeight),
                strokeWidth = thinStroke,
                cap = StrokeCap.Round
            )

            drawLine(
                color = colorPrimary,
                start = Offset(x = canvasWidth / 2, canvasHeight / 2),
                end = Offset(x = canvasWidth, y = canvasHeight / 2),
                strokeWidth = thinStroke * 1.5f,
                cap = StrokeCap.Round

            )
        }
    }
}