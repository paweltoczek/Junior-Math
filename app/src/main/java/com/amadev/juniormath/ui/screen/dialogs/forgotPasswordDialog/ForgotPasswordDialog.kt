package com.amadev.juniormath.ui.screen.dialogs.forgotPasswordDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.errorText.ErrorText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordDialog : DialogFragment() {

    private val forgotPasswordDialogViewModel: ForgotPasswordDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setUpObservers()
            setContent {
                ForgotPasswordDialogScreen()
            }
        }
    }

    private fun closeDialog() {
        dialog?.dismiss()
    }

    private fun setUpObservers() {
        forgotPasswordDialogViewModel.apply {
            popUpMessage.observe(viewLifecycleOwner) {
                showSnackBar(requireView(), it)
                closeDialog()
            }
        }
    }


    @Preview
    @Composable
    fun ForgotPasswordDialogScreen() {

        val emailInputErrorTextState = forgotPasswordDialogViewModel.emailInputErrorTextState.value
        val emailInputErrorTextValue = forgotPasswordDialogViewModel.emailInputErrorTextValue.value

        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.background)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TitleText()
                EmailTextField()
                if (emailInputErrorTextState) {
                    ErrorText(emailInputErrorTextValue)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SendEmailButton()
                }
            }
        }
    }

    @Composable
    fun TitleText() {
        Text(
            text = stringResource(id = R.string.forgotPassword),
            modifier = Modifier.padding(12.dp),
            fontSize = 18.sp
        )
    }

    @Composable
    fun EmailTextField() {
        val input = forgotPasswordDialogViewModel.emailInput.value
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .focusable(true),
            value = input,
            onValueChange = { email ->
                forgotPasswordDialogViewModel.onEmailInputChanged(email)
            },
            label = { Text(stringResource(R.string.email)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary
            ),
            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true)
    }

    @Composable
    fun SendEmailButton() {
        var signUpBtnState = forgotPasswordDialogViewModel.sendEmailButtonState.value
        Button(
            onClick = {
                signUpBtnState = !signUpBtnState
                forgotPasswordDialogViewModel.validateInput()
            },
            modifier = Modifier
                .animateContentSize(
                    animationSpec = tween(
                        300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = RoundedCornerShape(5.dp),
        ) {
            if (!signUpBtnState) Text(text = stringResource(id = R.string.sendEmail)) else
                Column(
                    horizontalAlignment =
                    Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        color = MaterialTheme.colors.secondary,
                        strokeWidth = 2.dp
                    )
                }
        }
    }
}