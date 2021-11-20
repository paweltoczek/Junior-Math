package com.amadev.juniormath.ui.screen.fragments.termsOfUsePrivacyPolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.titleTexts.SmallOverviewText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsOfUsePrivacyPolicyFragment : Fragment() {

    val viewModel: TermsOfUsePrivacyPolicyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TermsOfUsePrivacyPolicyUI()
            }
        }
    }

    @Composable
    fun TermsOfUsePrivacyPolicyUI() {
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                Column(
                    modifier = Modifier.weight(10f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (!viewModel.noticeUnderstood.value) {
                        Notice()
                    }
                    if (viewModel.noticeUnderstood.value) {
                        PrivacyPolicy()
                    }
                    if (viewModel.privacyPolicyAccepted.value && viewModel.noticeUnderstood.value) {
                        TermsOfUse()
                    }
                }
            }
        }

    }

    @Composable
    fun PrivacyPolicy() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.privacyPolicy),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = stringResource(id = R.string.privacyPolicyText),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(onClick = {
                viewModel.privacyPolicyAccepted.value = !viewModel.privacyPolicyAccepted.value
            }) {
                Text(text = stringResource(id = R.string.iAcceptPrivacyPolicy))
            }
        }
    }

    @Composable
    fun TermsOfUse() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.termsOfUse),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = stringResource(id = R.string.termsOfUseText),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(onClick = {
                navigateToLoginFragment()
            }) {
                Text(text = stringResource(id = R.string.iAcceptTermsOfUe))
            }
        }
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_termsOfUsePrivacyPolicyFragment_to_loginScreenFragment)
    }

    @Preview
    @Composable
    fun UIPreview() {
        JuniorMathTheme {
            PrivacyPolicy()
        }
    }

    @Composable
    fun Notice() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.notice),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(36.dp))
            SmallOverviewText(
                string = stringResource(id = R.string.ifYouAreUnderTwelveYearsOld),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(onClick = {
                viewModel.noticeUnderstood.value = !viewModel.noticeUnderstood.value
            }) {
                Text(text = stringResource(id = R.string.iUnderstand))
            }
        }
    }
}


