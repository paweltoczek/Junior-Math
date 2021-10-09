package com.amadev.juniormath.ui.screen.fragments.practiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.circleIndicator.CircleIndicator
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentDescriptionText
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentTitleText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PracticeFragment : Fragment() {

    private val practiceFragmentViewModel: PracticeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setUpViewModel()
            setUpObservers()
            setContent {
                PracticeScreenUi()
            }
        }
    }

    private fun setUpObservers() {
        practiceFragmentViewModel.popUpMessage.observe(viewLifecycleOwner) {
            showSnackBar(requireView(), it)
        }
    }

    private fun setUpViewModel() {
        val category = arguments?.getString("category")
        val fromRange = arguments?.getInt("fromRange")
        val toRange = arguments?.getInt("toRange")

        practiceFragmentViewModel.handleRanges(fromRange, toRange)
        practiceFragmentViewModel.setUpCategory(category.toString())
        practiceFragmentViewModel.setUpQuestionNumbers()
    }

    @Composable
    fun PracticeScreenUi() {
        val category = arguments?.getString("category")
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .background(MaterialTheme.colors.surface),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.Start

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            FragmentTitleText(stringResource(id = R.string.practice))
                            FragmentDescriptionText(category.toString())
                        }
                        CircleIndicator(
                            questionNo =
                            practiceFragmentViewModel.currentQuestion.value
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        QuestionDigit(text = practiceFragmentViewModel.questionNumberFirst.value)
                        Spacer(modifier = Modifier.width(16.dp))
                        Operator()
                        Spacer(modifier = Modifier.width(16.dp))
                        QuestionDigit(text = practiceFragmentViewModel.questionNumberSecond.value)
                    }
                    EqualOperator()
                    Spacer(modifier = Modifier.height(10.dp))
                    Answers()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    AcceptAnswerButton()
                }

            }
        }
    }

    @Composable
    fun EqualOperator() {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
            Text(text = "=",
                style = MaterialTheme.typography.body1,
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.primary
            )
        }
    }

    @Composable
    fun QuestionDigit(text: Int) {
        val receivedValue by animateIntAsState(
            targetValue = text,
            animationSpec = tween(1000)
        )

        Text(text = "$receivedValue",
            style = MaterialTheme.typography.body1,
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }

    @Composable
    fun Operator() {
        Text(
            text = practiceFragmentViewModel.operator.value,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary,
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold
        )

    }

    @Composable
    fun AcceptAnswerButton() {
        Button(
            onClick = {
                practiceFragmentViewModel.validateUserInput()
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(text = stringResource(id = R.string.accept))
        }
    }

    @Composable
    fun Answers() {
        val spaceBetweenButtons = 25.dp
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnswerButton(
                    practiceFragmentViewModel.button1State.value,
                    practiceFragmentViewModel.button1State.toString(),
                    practiceFragmentViewModel.button1Text.value
                )
                Spacer(modifier = Modifier.width(spaceBetweenButtons))
                AnswerButton(
                    practiceFragmentViewModel.button2State.value,
                    practiceFragmentViewModel.button2State.toString(),
                    practiceFragmentViewModel.button2Text.value
                )
            }
            Spacer(modifier = Modifier.height(spaceBetweenButtons))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnswerButton(
                    practiceFragmentViewModel.button3State.value,
                    practiceFragmentViewModel.button3State.toString(),
                    practiceFragmentViewModel.button3Text.value
                )
                Spacer(modifier = Modifier.width(spaceBetweenButtons))
                AnswerButton(
                    practiceFragmentViewModel.button4State.value,
                    practiceFragmentViewModel.button4State.toString(),
                    practiceFragmentViewModel.button4Text.value
                )
            }
        }
    }

    @Composable
    fun AnswerButton(state: Boolean, button: String, text: String) {
        var buttonColor = MaterialTheme.colors.secondary
        var textColor = Color.Black

        if (state) {
            buttonColor = MaterialTheme.colors.primary
            textColor = Color.White
        }

        val textAnimatedColor by animateColorAsState(
            targetValue =
            textColor, animationSpec = tween(
                durationMillis = 500
            )
        )

        val buttonAnimatedColor by animateColorAsState(
            targetValue = buttonColor, animationSpec = tween(
                500
            )
        )

        Button(
            onClick = {
                practiceFragmentViewModel.onButtonStateChanged(button)
                practiceFragmentViewModel.userAnswerInput.value = text
            },
            modifier = Modifier
                .size(100.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            colors = ButtonDefaults.buttonColors(buttonAnimatedColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    color = textAnimatedColor,
                    style = MaterialTheme.typography.body1,
                    fontSize = 24.sp
                )
            }
        }
    }
}