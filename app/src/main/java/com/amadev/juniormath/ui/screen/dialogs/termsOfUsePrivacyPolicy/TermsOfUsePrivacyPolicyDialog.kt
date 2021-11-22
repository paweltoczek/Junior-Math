package com.amadev.juniormath.ui.screen.dialogs.termsOfUsePrivacyPolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.titleTexts.SmallTitleText
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsOfUsePrivacyPolicyDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DialogUI()
            }
        }
    }

    @Composable
    fun DialogUI() {
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .height(500.dp)
                    .background(color = Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SmallTitleText(string = stringResource(id = R.string.termsOfUsePrivacyPolicy))
                }
                Column(
                    modifier = Modifier
                        .weight(8f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(buildAnnotatedString {
                        withStyle(style = ParagraphStyle(textAlign = TextAlign.Center)) {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            ) {
                                append(text = stringResource(id = R.string.privacyPolicy))
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            ) {
                                append(text = stringResource(id = R.string.privacyPolicyText))
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            ) {
                                append(text = stringResource(id = R.string.termsOfUse))
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            ) {
                                append(text = stringResource(id = R.string.termsOfUseText))
                            }
                        }
                    }
                    )

                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Button(onClick = { dialog?.dismiss() }) {
                        Text(text = stringResource(id = R.string.accept))
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun Preview() {
        DialogUI()
    }
}