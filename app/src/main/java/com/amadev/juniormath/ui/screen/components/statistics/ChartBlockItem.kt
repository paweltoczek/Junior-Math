import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amadev.juniormath.ui.theme.JuniorMathTheme


@Composable
fun ChartBlockItem(
    startValue: Int = 1,
    maxValue: Int = 15,
    dayName: String = "Mon"
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

    val componentHeight by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(3000)
    )

    JuniorMathTheme {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            BackgroundColumn(componentHeight)
            DayNameText(dayName)
        }
    }

}

@Composable
fun DayNameText(day: String) {
    Text(
        text = day,
        fontSize = 12.sp,
        modifier = Modifier.absolutePadding(0.dp, 8.dp, 0.dp, 8.dp),
        color = MaterialTheme.colors.onSurface
    )
}

@Composable
fun BackgroundColumn(componentHeight: Float) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .width(25.dp)
            .clip(shape = RoundedCornerShape(50))
            .background(MaterialTheme.colors.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        ForegroundColumn(componentHeight)

    }
}

@Composable
fun ForegroundColumn(componentHeight: Float) {
    Column(
        modifier = Modifier
            .height(componentHeight.dp)
            .width(20.dp)
            .clip(shape = RoundedCornerShape(50))
            .background(MaterialTheme.colors.primary)
    ) {

    }
}


@Preview(showBackground = true)
@Composable
fun CustomComponentPreview() {
    ChartBlockItem()
}
