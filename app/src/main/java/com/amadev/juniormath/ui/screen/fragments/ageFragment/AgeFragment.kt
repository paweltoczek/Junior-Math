package com.amadev.juniormath.ui.screen.fragments.ageFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.appIcon.AppIcon
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgeFragment : Fragment() {

    private val ageFragmentViewModel: AgeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AgeFragmentUI()
                setUpObservers()
            }
        }
    }

    private fun setUpObservers() {
        ageFragmentViewModel.apply {
            popUpMessage.observe(viewLifecycleOwner) {
                showSnackBar(requireView(), it.toString())
            }
        }
    }

    @Composable
    fun AgeFragmentUI() {
        val focusManager = LocalFocusManager.current
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AppIcon(canvasSize = 30f)

                }
                Column(
                    modifier = Modifier
                        .weight(15f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(200.dp)
                            .focusable(true),
                        value = ageFragmentViewModel.ageInput.value,
                        onValueChange = { input ->
                            ageFragmentViewModel.ageInput.value = input
                        },
                        label = { stringResource(id = R.string.whatsYourAge) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.primary,
                            unfocusedBorderColor = MaterialTheme.colors.secondary,
                            textColor = MaterialTheme.colors.onSurface
                        ),
                        textStyle = MaterialTheme.typography.h1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.moveFocus(
                                focusDirection = FocusDirection.Down
                            )
                        }),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { ageFragmentViewModel.validateAgeInput() }) {
                        Text(text = stringResource(id = R.string.accept))
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun UIPreview() {
        AgeFragmentUI()
    }
}