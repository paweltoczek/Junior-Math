package com.amadev.juniormath.ui.screen.loginScreen


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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.errorText.ErrorText
import com.amadev.juniormath.ui.screen.dialogs.forgotPasswordDialog.ForgotPasswordDialog
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.ui.theme.VerticalGradientBrush
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreenFragment : Fragment() {

    private val loginScreenViewModel: LoginScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setUpViewModel()
            setUpObservers()

            setContent {
                LoginScreen()
            }
        }
    }

    private fun navigateToSignUpScreen() {
        findNavController().navigate(R.id.action_loginScreenFragment_to_signUpScreen)
    }

    private fun setUpObservers() {
        loginScreenViewModel.apply {
            popUpMessage.observe(viewLifecycleOwner) {
                showSnackBar(requireView(), it)
            }
            loginAutomatically.observe(viewLifecycleOwner) {

            }
        }
    }

    private fun setUpViewModel() {
        loginScreenViewModel.apply {
            loginAutomaticallyIfPossible()
        }
    }

    @Composable
    fun LoginScreen() {

        val emailInputErrorTextState = loginScreenViewModel.emailInputErrorTextState.value
        val emailInputErrorTextValue = loginScreenViewModel.emailInputErrorTextValue.value
        val passwordInputErrorTextState = loginScreenViewModel.passwordInputErrorTextState.value
        val passwordInputErrorTextValue = loginScreenViewModel.passwordInputErrorTextValue.value

        JuniorMathTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = VerticalGradientBrush)
            ) {
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
                            TitleText(text = stringResource(id = R.string.login))
                        }

                        EmailTextField()
                        if (emailInputErrorTextState) {
                            ErrorText(emailInputErrorTextValue)
                        }
                        PasswordTextField()
                        if (passwordInputErrorTextState) {
                            ErrorText(passwordInputErrorTextValue)
                        }
                        ForgotPasswordButton()

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SkipButton()
                            LoginButton()
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SignUpButton()
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
        val input = loginScreenViewModel.emailInput.value
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .focusable(true),
            value = input,
            onValueChange = { email ->
                loginScreenViewModel.onEmailInputChanged(email)
            },
            label = { Text("Email") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary
            ),
            textStyle = MaterialTheme.typography.body1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,


            )
    }

    @Composable
    fun PasswordTextField() {
        val input = loginScreenViewModel.passwordInput.value
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .focusable(true),
            value = input,
            onValueChange = { password ->
                loginScreenViewModel.onPasswordInputChanged(password)
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
    fun LoginButton() {
        var loginBtnState = loginScreenViewModel.loginButtonState.value
        Button(
            onClick = {
                loginBtnState = !loginBtnState
                loginScreenViewModel.validateInput()
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
            if (!loginBtnState) Text(text = stringResource(id = R.string.login)) else
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

    @Composable
    fun ForgotPasswordButton() {

        TextButton(
            onClick = { showForgotPasswordDialog() },
            modifier = Modifier.background(Color.Transparent),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = stringResource(id = R.string.forgotPassword), fontSize = 12.sp)
        }
    }

    @Composable
    fun SkipButton() {
        TextButton(
            onClick = {},
            modifier = Modifier
                .background(Color.Transparent),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = stringResource(id = R.string.skip))
        }
    }

    @Composable
    fun SignUpButton() {
        TextButton(
            onClick = { navigateToSignUpScreen() },
            modifier = Modifier
                .background(Color.Transparent),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(stringResource(id = R.string.signUp))
        }
    }


    fun showForgotPasswordDialog() {
        val forgotPasswordDialog = ForgotPasswordDialog()
        forgotPasswordDialog.show(childFragmentManager, null)
    }

}