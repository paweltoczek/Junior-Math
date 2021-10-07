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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentDescriptionText
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentTitleText
import com.amadev.juniormath.ui.screen.components.titleTexts.SmallOverviewText
import com.amadev.juniormath.ui.screen.components.titleTexts.SmallTitleText
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
        val category = arguments?.getString("category")

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
                    FragmentTitleText(stringResource(id = R.string.category))
                    FragmentDescriptionText(category.toString())
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp, 0.dp)
                ) {
                    SmallTitleText(stringResource(id = R.string.range))
                    Spacer(modifier = Modifier.height(8.dp))
                    SmallOverviewText(stringResource(id = R.string.typeInThe))
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
            onClick = {
                navigateToPracticeFragment()
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
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

    private fun navigateToPracticeFragment() {
        val bundle = Bundle()
        val category = arguments?.getString("category")

        bundle.putString("category", category.toString())
        bundle.putInt("fromRange", rangeFragmentViewModel.fromRangeInput.value.toInt())
        bundle.putInt("toRange", rangeFragmentViewModel.toRangeInput.value.toInt())
        findNavController().navigate(R.id.action_rangeFragment_to_practiceFragment, bundle)
    }
}