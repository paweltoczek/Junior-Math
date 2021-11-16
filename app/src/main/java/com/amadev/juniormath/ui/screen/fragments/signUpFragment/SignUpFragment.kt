package com.amadev.juniormath.ui.screen.fragments.signUpFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.emailSent.EmailSent
import com.amadev.juniormath.ui.screen.components.errorText.ErrorText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpScreenViewModel: SignUpScreenViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setUpObservers()
            setContent {
                SignUpScreen()
            }
        }
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_signUpScreen_to_loginScreenFragment)
    }

    private fun setUpObservers() {
        signUpScreenViewModel.popUpMessage.observe(viewLifecycleOwner) {
            showSnackBar(requireView(), it)
        }
    }

    @Preview
    @Composable
    fun SignUpScreen() {
        val verificationEmailSentState = signUpScreenViewModel.verificationEmailSentState.value
        val emailInputErrorTextState = signUpScreenViewModel.emailInputErrorTextState.value
        val emailInputErrorTextValue = signUpScreenViewModel.emailInputErrorTextValue.value
        val passwordInputErrorTextState = signUpScreenViewModel.passwordInputErrorTextState.value
        val passwordInputErrorTextValue = signUpScreenViewModel.passwordInputErrorTextValue.value
        val repeatPasswordInputErrorTextState =
            signUpScreenViewModel.repeatPasswordInputErrorTextState.value
        val repeatPasswordInputErrorTextValue =
            signUpScreenViewModel.repeatPasswordInputErrorTextValue.value

        JuniorMathTheme {

            if (!verificationEmailSentState) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Column {
                            SignUpText()
                            SubText()
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            EmailTextField()
                            if (emailInputErrorTextState) {
                                ErrorText(emailInputErrorTextValue)
                            }
                            PasswordTextField()
                            if (passwordInputErrorTextState) {
                                ErrorText(passwordInputErrorTextValue)
                            }
                            RepeatPasswordTextField()
                            if (repeatPasswordInputErrorTextState) {
                                ErrorText(repeatPasswordInputErrorTextValue)
                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            SignUpButton()
                        }
                    }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    EmailSent()
                    GoToLoginFragment()
                }
            }
        }
    }

    @Composable
    fun GoToLoginFragment() {
        TextButton(
            onClick = { navigateToLoginFragment() },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(0.dp, 24.dp),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = stringResource(id = R.string.goToLoginPage),
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }

    @Composable
    fun SignUpText() {
        Text(
            text = stringResource(id = R.string.signUp),
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.body1,
            fontSize = 24.sp,
            color = MaterialTheme.colors.onSurface
        )
    }

    @Composable
    fun SubText() {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .width(250.dp),
            text = stringResource(id = R.string.thankYou),
            style = MaterialTheme.typography.body2,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface
        )
    }


    @Composable
    fun EmailTextField() {
        val input = signUpScreenViewModel.emailInput.value
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .focusable(true),
            value = input,
            onValueChange = { email ->
                signUpScreenViewModel.onEmailInputChanged(email)
            },
            label = { Text("Email") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
    }

    @Composable
    fun PasswordTextField() {
        val input = signUpScreenViewModel.passwordInput.value
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .focusable(true),
            value = input,
            onValueChange = { password ->
                signUpScreenViewModel.onPasswordInputChanged(password)
            },
            label = { Text("Password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
    }

    @Composable
    fun RepeatPasswordTextField() {
        val input = signUpScreenViewModel.repeatPasswordInput.value
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .focusable(true),
            value = input,
            onValueChange = { password ->
                signUpScreenViewModel.onRepeatPasswordInputChanged(password)
            },
            label = { Text("Repeat password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
    }

    @Composable
    fun SignUpButton() {
        var signUpBtnState = signUpScreenViewModel.signUpButtonState.value
        Button(
            onClick = {
                signUpBtnState = !signUpBtnState
                signUpScreenViewModel.validateInput()
            },
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        300,
                        easing = LinearOutSlowInEasing,

                        )
                ),
            shape = RoundedCornerShape(5.dp),
        ) {
            if (!signUpBtnState) Text(text = stringResource(id = R.string.signUp)) else
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