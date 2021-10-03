package com.amadev.juniormath.ui.screen.fragments.welcomeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private val welcomeScreenViewModel: WelcomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setUpViewModel()
            setUpObservers()
            setContent {
                JuniorMathTheme {
                    WelcomeScreen()
                    navigateToLoginScreen()
                }
            }
        }
    }

    private fun setUpObservers() {
        welcomeScreenViewModel.apply {
            loginAutomaticallyIfPossible.observe(viewLifecycleOwner) {
                navigateToDestination(it)
            }
            popUpMessage.observe(viewLifecycleOwner) {
                showSnackBar(requireView(), it)
            }
        }
    }

    private fun setUpViewModel() {
        welcomeScreenViewModel.apply {
            loginAutomaticallyIfPossible()
        }
    }


    private fun navigateToDestination(loginAutomatically: Boolean) {
//        Handler(Looper.myLooper()!!).postDelayed({
            if (loginAutomatically) navigateToCategoryFragment() else navigateToLoginScreen()
//        }, 2000)


    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(R.id.action_welcomeScreen_to_loginScreenFragment)
    }

    private fun navigateToCategoryFragment() {
        findNavController().navigate(R.id.action_welcomeScreen_to_homeFragment)
    }

    @Preview
    @Composable
    fun WelcomeScreen() {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }



}