package com.amadev.juniormath.ui.screen.signUpScreen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.errorText.ErrorText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.ui.theme.VerticalGradientBrush
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpScreen : Fragment() {

    private val signUpScreenViewModel: SignUpScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                setUpObservers()
                SignUpScreen()
            }
        }
    }

    private fun setUpObservers() {
        signUpScreenViewModel.popUpMessage.observe(viewLifecycleOwner) {
            showSnackBar(requireView(), it)
        }
    }


    @Preview
    @Composable
    fun SignUpScreen() {

        val emailInputErrorTextState = signUpScreenViewModel.emailInputErrorTextState.value
        val emailInputErrorTextValue = signUpScreenViewModel.emailInputErrorTextValue.value

        val passwordInputErrorTextState = signUpScreenViewModel.passwordInputErrorTextState.value
        val passwordInputErrorTextValue = signUpScreenViewModel.passwordInputErrorTextValue.value

        val repeatPasswordInputErrorTextState =
            signUpScreenViewModel.repeatPasswordInputErrorTextState.value
        val repeatPasswordInputErrorTextValue =
            signUpScreenViewModel.repeatPasswordInputErrorTextValue.value

        JuniorMathTheme {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(brush = VerticalGradientBrush)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AppNameText(text = stringResource(id = R.string.app_name))


                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TitleText(text = stringResource(id = R.string.createAccount))
                        }

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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            SignUpButton()
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    }
                }
            }
        }
    }

    @Composable
    fun AppNameText(text: String) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

    @Composable
    fun TitleText(text: String) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
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
                unfocusedBorderColor = MaterialTheme.colors.secondary
            ),
            textStyle = MaterialTheme.typography.body1,
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
                unfocusedBorderColor = MaterialTheme.colors.secondary
            ),
            textStyle = MaterialTheme.typography.body1,
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
                unfocusedBorderColor = MaterialTheme.colors.secondary
            ),
            textStyle = MaterialTheme.typography.body1,
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
                .animateContentSize(
                    animationSpec = tween(
                        300,
                        easing = LinearOutSlowInEasing
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