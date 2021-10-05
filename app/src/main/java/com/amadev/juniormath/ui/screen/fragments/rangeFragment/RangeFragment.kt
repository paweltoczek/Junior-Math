package com.amadev.juniormath.ui.screen.fragments.rangeFragment

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
import androidx.compose.ui.text.font.FontFamily
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
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RangeFragment : Fragment() {

    private val rangeFragmentViewModel: RangeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RangeFragmentUI()
            }
        }
    }

    @Preview
    @Composable
    fun RangeFragmentUI() {
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(32.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    FragmentTitleText()
                    SubTitleText()
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp, 0.dp)
                ) {
                    SmallTitleText()
                    Spacer(modifier = Modifier.height(8.dp))
                    SmallOverviewText()
                    Spacer(modifier = Modifier.height(24.dp))
                    FromRangeInputField()
                    Spacer(modifier = Modifier.height(24.dp))
                    ToRangeInputField()
                }
                Column {
                    GoToPracticeFragmentButton()
                }
            }

        }
    }


    @Composable
    fun GoToPracticeFragmentButton() {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
        ) {
            Text(text = stringResource(id = R.string.practice))

        }
    }

    @Composable
    fun FromRangeInputField() {
        val focusManager = LocalFocusManager.current
        val input = rangeFragmentViewModel.fromRangeInput.value

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = input,
            onValueChange = { rangeFromInput ->
                rangeFragmentViewModel
                    .onFromRangeInputChanged(rangeFromInput)
            },
            label = { Text(stringResource(id = R.string.from)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.onSurface
            ),
            textStyle = MaterialTheme.typography.h1.copy(textAlign = TextAlign.Center),

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            singleLine = true
        )
    }

    @Composable
    fun ToRangeInputField() {
        val focusManager = LocalFocusManager.current
        val input = rangeFragmentViewModel.toRangeInput.value

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = input,
            onValueChange = { rangeFromInput ->
                rangeFragmentViewModel
                    .onToRangeInputChanged(rangeFromInput)
            },
            label = { Text(stringResource(id = R.string.to)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.onSurface
            ),
            textStyle = MaterialTheme.typography.h1.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            singleLine = true
        )
    }

    @Composable
    fun SmallOverviewText() {
        Text(
            text = stringResource(id = R.string.typeInThe),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = MaterialTheme.colors.onSurface
        )
    }

    @Composable
    fun SmallTitleText() {
        Text(
            text = stringResource(id = R.string.range),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface
        )
    }

    @Composable
    fun SubTitleText() {
        val category = arguments?.getString("category")
        Text(
            text = category.toString(),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colors.primary
        )
    }

    @Composable
    fun FragmentTitleText() {
        Text(
            text = stringResource(id = R.string.category),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = MaterialTheme.colors.onSurface
        )
    }
}