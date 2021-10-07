package com.amadev.juniormath.ui.screen.fragments.practiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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


    @Preview
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
                        Digit(text = practiceFragmentViewModel.questionNumberFirst.value.toString())
                        Spacer(modifier = Modifier.width(16.dp))
                        Operator()
                        Spacer(modifier = Modifier.width(16.dp))
                        Digit(text = practiceFragmentViewModel.questionNumberSecond.value.toString())
                    }
                    Row {
                        AnswerInput()
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    NextQuestionButton()
                }

            }
        }
    }

    @Composable
    fun Digit(text: String) {
        Text(
            text = text, style = MaterialTheme.typography.body1,
            fontSize = 32.sp,
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
    fun AnswerInput() {
        val focusManager = LocalFocusManager.current
        val input = practiceFragmentViewModel.userAnswerInput.value
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp, 12.dp),
            value = input,
            onValueChange = { answer ->
                practiceFragmentViewModel
                    .onAnswerInputChanged(answer)
            },
            label = { Text(stringResource(id = R.string.answer)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.onSurface
            ),
            textStyle = MaterialTheme.typography.h1.copy(
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            singleLine = true,
        )
    }

    @Composable
    fun NextQuestionButton() {
        Button(
            onClick = {
                practiceFragmentViewModel.compareUserInputWithCorrectAnswer()

            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(text = stringResource(id = R.string.accept))
        }
    }
}