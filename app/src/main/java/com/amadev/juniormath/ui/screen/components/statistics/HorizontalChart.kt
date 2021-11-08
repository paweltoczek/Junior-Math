import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amadev.juniormath.R


@Composable
fun HorizontalChart(
    correctAnswers: Int = 0,
    totalQuestions: Int = 0,
    canvasWidth: Dp = 300.dp
) {

    var allowedBlockValue by remember {
        mutableStateOf(totalQuestions)
    }

    allowedBlockValue = if (correctAnswers <= totalQuestions) {
        correctAnswers
    } else {
        canvasWidth.value.toInt()
    }

    var animatedForegroundBlockValue by remember { mutableStateOf(0.dp) }

    LaunchedEffect(key1 = allowedBlockValue) {
        animatedForegroundBlockValue = allowedBlockValue.dp
    }

    val blockPointValue = canvasWidth / totalQuestions

    val percentage = animatedForegroundBlockValue * blockPointValue.value

    val componentWidth by animateDpAsState(
        targetValue = percentage,
        animationSpec = tween(1500, delayMillis = 100, easing = FastOutLinearInEasing)
    )

    Column(
        modifier = Modifier
            .width(canvasWidth)
            .wrapContentHeight()
            .padding(0.dp, 12.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        TotalQuestionsText(totalQuestions = totalQuestions)
        Spacer(modifier = Modifier.height(8.dp))
        BackgroundColumn(canvasWidth)
        Spacer(modifier = Modifier.height(16.dp))
        TotalCorrectAnswersText(correctAnswers = correctAnswers)
        Spacer(modifier = Modifier.height(8.dp))
        ForegroundColumn(componentWidth)
        Spacer(modifier = Modifier.height(12.dp))

    }


}

@Composable
fun BackgroundColumn(componentWidth: Dp) {
    Column(
        modifier = Modifier
            .height(20.dp)
            .width(componentWidth)
            .clip(shape = RoundedCornerShape(50))
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {}
}

@Composable
fun ForegroundColumn(componentWidth: Dp) {
    Column(
        modifier = Modifier
            .height(20.dp)
            .width(componentWidth)
            .clip(shape = RoundedCornerShape(50))
            .background(MaterialTheme.colors.primary)
    ) {}
}

@Composable
fun TotalQuestionsText(totalQuestions: Int = 0) {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                ) {
                    append(stringResource(id = R.string.totalQuestions))
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(" ")
                    append("$totalQuestions")
                }
            }
        }
    )
}

@Composable
fun TotalCorrectAnswersText(correctAnswers: Int = 0) {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                ) {
                    append(stringResource(id = R.string.totalCorrectAnswers))
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(" ")
                    append("$correctAnswers")
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun CustomComponentPreview() {
    HorizontalChart()
}
