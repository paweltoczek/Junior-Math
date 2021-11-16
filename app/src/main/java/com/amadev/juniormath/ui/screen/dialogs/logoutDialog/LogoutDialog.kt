package com.amadev.juniormath.ui.screen.dialogs.logoutDialog

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentTitleText
import com.amadev.juniormath.ui.screen.components.titleTexts.SmallTitleText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog : DialogFragment() {

    private val logoutDialogViewModel: LogoutDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LogoutDialog()
            }
        }
    }

    private fun logOut() {
        logoutDialogViewModel.apply {
            logout()
            navigateToWelcomeScreen()
        }
    }

    private fun navigateToWelcomeScreen() {
        findNavController().navigate(R.id.action_homeFragment_to_welcomeScreen)
    }

    @Preview
    @Composable
    fun LogoutDialog() {
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.background)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                FragmentTitleText(string = stringResource(id = R.string.logout))
                Spacer(modifier = Modifier.height(8.dp))
                SmallTitleText(string = stringResource(id = R.string.sureWantToLogOut))
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { dialog?.dismiss() }) {
                        Text(text = stringResource(id = R.string.no))
                    }
                    TextButton(onClick = { logOut() }) {
                        Text(text = stringResource(id = R.string.yes))
                    }
                }
            }
        }
    }
}