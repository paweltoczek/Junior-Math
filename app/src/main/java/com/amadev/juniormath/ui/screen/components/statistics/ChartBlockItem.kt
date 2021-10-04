import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ChartBlockItem(
    startValue: Int = 10,
    maxValue: Int = 100,
    foregroundBlockColor: Color = MaterialTheme.colors.primary,
) {

    var allowedBlockValue by remember {
        mutableStateOf(maxValue)
    }

    allowedBlockValue = if (startValue <= maxValue) {
        startValue
    } else {
        maxValue
    }

    var animatedBlockValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedBlockValue) {
        animatedBlockValue = allowedBlockValue.toFloat()
    }

    val percentage =
        (animatedBlockValue / maxValue) * 100

    val lineHeight by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(3000)
    )

    Column(
        modifier = Modifier
            .width(30.dp)
            .height(80.dp)
            .rotate(180f)
            .drawBehind {
                backgroundChartBlock(
                    size.width,
                    size.height,
                    strokeWidth = 50f
                )
                foregroundChartBlock(
                    size.width,
                    lineHeight,
                    strokeWidth = 50f,
                    color = foregroundBlockColor
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    }
}

fun DrawScope.backgroundChartBlock(
    componentWidth: Float,
    componentHeight: Float,
    strokeWidth: Float
) {
    drawLine(
        color = Color.Gray,
        start = Offset(x = componentWidth / 2, y = componentHeight),
        end = Offset(x = componentWidth / 2, y = 0f),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

fun DrawScope.foregroundChartBlock(
    componentWidth: Float,
    componentHeight: Float,
    strokeWidth: Float,
    color: Color
) {
    drawLine(
        color = color,
        start = Offset(x = componentWidth / 2, y = componentHeight),
        end = Offset(x = componentWidth / 2, y = 0f),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

@Composable
@Preview(showBackground = true)
fun CustomComponentPreview() {
    ChartBlockItem()
}