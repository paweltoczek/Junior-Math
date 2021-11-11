package com.amadev.juniormath.ui.screen.fragments.statisticsFragment

import HorizontalChart
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amadev.juniormath.R
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentDescriptionText
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentTitleText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val statisticsFragmentViewModel: StatisticsFragmentViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setUpViewModel()
            setUpObservers()
            setContent {
                StatisticsFragmentUI()
            }
        }
    }

    private fun setUpObservers() {
        statisticsFragmentViewModel.apply {
            popUpMessage.observe(viewLifecycleOwner) {
                showSnackBar(requireView(), it)
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun setUpViewModel() {
        statisticsFragmentViewModel.apply {
            getUserScoreData()
        }
    }


    @ExperimentalCoroutinesApi
    @Preview
    @Composable
    fun StatisticsFragmentUI() {
        val scrollState = rememberScrollState()

        JuniorMathTheme {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        FragmentTitleText(string = stringResource(id = R.string.statistics))
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        //Addition
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.addition))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        StatisticsButton(
                            statisticsFragmentViewModel.additionScoreData.value,
                            statisticsFragmentViewModel.additionDataLoaded.value
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        //Subtraction
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.subtraction))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        StatisticsButton(
                            statisticsFragmentViewModel.subtractionScoreData.value,
                            statisticsFragmentViewModel.subtractionDataLoaded.value
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        //Multiplication
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.multiplication))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        StatisticsButton(
                            statisticsFragmentViewModel.multiplicationScoreData.value,
                            statisticsFragmentViewModel.multiplicationDataLoaded.value
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        //Division
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.division))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        StatisticsButton(
                            statisticsFragmentViewModel.divisionScoreData.value,
                            statisticsFragmentViewModel.divisionDataLoaded.value
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

            }
        }
    }

    @ExperimentalCoroutinesApi
    @Composable
    fun StatisticsButton(data: UserAnswersModel, dataReady: Boolean) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(0.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
        ) {
            if (dataReady.not()) {
                CircularProgressIndicator()
            } else {
                HorizontalChart(
                    correctAnswers = data.userCorrectAnswers.toInt(),
                    totalQuestions = data.totalQuestions.toInt()
                )
            }
        }
    }
}