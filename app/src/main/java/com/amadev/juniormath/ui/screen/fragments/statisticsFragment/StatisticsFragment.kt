package com.amadev.juniormath.ui.screen.fragments.statisticsFragment

import HorizontalChart
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentDescriptionText
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentTitleText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val statisticsFragmentViewModel: StatisticsFragmentViewModel by viewModels()

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
            popUpMessage.observe(viewLifecycleOwner){
                showSnackBar(requireView(), it)
            }
            additionData.observe(viewLifecycleOwner){
                Log.e("data", it.toString())
            }


        }
    }

    private fun setUpViewModel() {
        statisticsFragmentViewModel.apply {
        }
    }


    @Preview
    @Composable
    fun StatisticsFragmentUI() {
        val scrollState = rememberScrollState()

        JuniorMathTheme {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.addition))
                        }
                        StatisticsButton()
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.subtraction))
                        }
                        StatisticsButton()
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.multiplication))
                        }
                        StatisticsButton()
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FragmentDescriptionText(string = stringResource(id = R.string.division))
                        }
                        StatisticsButton()
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

            }
        }
    }

    @Composable
    fun StatisticsButton() {
        TextButton(
            onClick = { statisticsFragmentViewModel.getUserScoreData() },
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        ) {
            HorizontalChart()
        }
    }
}