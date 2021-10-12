package com.amadev.juniormath.ui.screen.fragments.resultsFragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.circleIndicator.CircleIndicator
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentDescriptionText
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentTitleText
import com.amadev.juniormath.ui.screen.components.titleTexts.SmallTitleText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private val resultsFragmentViewModel: ResultsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ResultFragmentUI()
            }
        }
    }

    @Composable
    fun ResultFragmentUI() {

        val category = arguments?.getString("category")
        val userCorrectAnswers = arguments?.getInt("userCorrectAnswers")

        Handler(Looper.myLooper()!!).postDelayed({
            resultsFragmentViewModel.handleUserAnswers(userCorrectAnswers!!.toInt())
        },1000)


        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxSize(), horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        FragmentTitleText(string = stringResource(id = R.string.results))
                        FragmentDescriptionText(string = category.toString())
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircleIndicator(canvasSize = 100.dp ,questionNo = resultsFragmentViewModel.userAnswers.value)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        SmallTitleText(string = stringResource(id = R.string.totalQuestion))
                        Spacer(modifier = Modifier.height(8.dp))
                        FragmentDescriptionText(string = "15")
                        Spacer(modifier = Modifier.height(24.dp))
                        SmallTitleText(string = stringResource(id = R.string.correctAnswers))
                        Spacer(modifier = Modifier.height(8.dp))
                        FragmentDescriptionText(string = "$userCorrectAnswers")
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        GoToHomeFragmentButton()
                    }
                }
            }
        }
    }

    @Composable
    fun GoToHomeFragmentButton() {
        TextButton(
            onClick = { navigateToHomeFragment() },
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

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_resultsFragment_to_homeFragment)
    }
}