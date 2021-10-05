package com.amadev.juniormath.ui.screen.fragments.loginFragment


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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
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
import com.amadev.juniormath.ui.screen.components.errorText.ErrorText
import com.amadev.juniormath.ui.screen.dialogs.forgotPasswordDialog.ForgotPasswordDialog
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginScreenViewModel: LoginFragmentViewModel by viewModels()

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

    @Preview
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
                    .background(color = MaterialTheme.colors.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Column {
                        LoginText()
                        SubText()
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ForgotPasswordButton()
                        }

                        LoginButton()
                        SkipButton()


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RegisterText()
                            SignUpButton()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LoginText() {
        Text(
            text = stringResource(id = R.string.login),
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
            text = stringResource(id = R.string.weAreGlad),
            style = MaterialTheme.typography.body2,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface
        )
    }

    @Composable
    fun RegisterText() {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.dontHaveAccount),
            style = MaterialTheme.typography.body1,
            fontSize = 12.sp,
            color = MaterialTheme.colors.onSurface
        )
    }


    @Composable
    fun EmailTextField() {
        val input = loginScreenViewModel.emailInput.value
        val focusManager = LocalFocusManager.current
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
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }),
            singleLine = true
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
    fun LoginButton() {
        var loginBtnState = loginScreenViewModel.loginButtonState.value
        Button(
            onClick = {
                loginBtnState = !loginBtnState
                loginScreenViewModel.validateInput()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
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
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = stringResource(id = R.string.forgotPassword),
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }

    @Composable
    fun SkipButton() {
        TextButton(
            onClick = { navigateToCategoryFragment() },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(0.dp, 8.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = stringResource(id = R.string.skip), color = MaterialTheme.colors.primary)
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
            Text(stringResource(id = R.string.signUp), color = MaterialTheme.colors.primary)
        }
    }


    private fun navigateToSignUpScreen() {
        findNavController().navigate(R.id.action_loginScreenFragment_to_signUpScreen)
    }

    private fun navigateToCategoryFragment() {
        findNavController().navigate(R.id.action_loginScreenFragment_to_homeFragment)
    }

    private fun showForgotPasswordDialog() {
        val forgotPasswordDialog = ForgotPasswordDialog()
        forgotPasswordDialog.show(childFragmentManager, null)
    }


    private fun setUpObservers() {
        loginScreenViewModel.apply {
            popUpMessage.observe(viewLifecycleOwner) {
                showSnackBar(requireView(), it)
            }
            loginAutomatically.observe(viewLifecycleOwner) {
                navigateToCategoryFragment()
            }
        }
    }


    private fun setUpViewModel() {
        loginScreenViewModel.apply {
            loginAutomaticallyIfPossible()
        }
    }
}