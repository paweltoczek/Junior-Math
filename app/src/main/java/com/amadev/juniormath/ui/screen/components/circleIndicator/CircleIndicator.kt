package com.amadev.juniormath.ui.screen.components.circleIndicator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amadev.juniormath.ui.theme.JuniorMathTheme


@Composable
fun CircleIndicator(
    canvasSize: Dp = 75.dp,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    foregroundColor: Color = MaterialTheme.colors.primary,
    questionNo: Int = 1
) {

    val sweepAngle by animateFloatAsState(
        targetValue = questionNo.toFloat() * 24,
        animationSpec = tween(1000)
    )

    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size * 0.8f
                backgroundIndicator(
                    componentSize = componentSize,
                    backgroundColor
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    foregroundColor
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmbeddedElements(questionNo, 15)
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    color: Color
) {
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 180f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = 15f,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    color: Color
) {
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 180f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = 20f,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
fun EmbeddedElements(
    questionNo: Int = 1,
    totalQuestions: Int = 15,
) {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(questionNo.toString())
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold, fontSize = 12.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                ) {
                    append("/")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                ) {
                    append(totalQuestions.toString())
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun CustomComponentPreview() {
    JuniorMathTheme {
        CircleIndicator()
    }
}