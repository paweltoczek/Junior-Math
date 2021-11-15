package com.amadev.juniormath.ui.screen.dialogs.verifyEmailDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyEmailDialog : DialogFragment() {

    private val verifyEmailDialogViewModel : VerifyEmailDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                dialog?.setCancelable(false)
                setUpObservers()
                VerifyEmailDialog()
            }
        }
    }

    private fun setUpObservers() {
        verifyEmailDialogViewModel.apply {
            emailVerified.observe(viewLifecycleOwner) {
                if (it) {
                    dialog?.setCancelable(true)
                    dialog?.dismiss()
                }
            }
            popUpMessage.observe(viewLifecycleOwner) { message ->
                showSnackBar(requireView(), message)
            }
        }
    }


    private fun closeDialog() {
        dialog?.dismiss()
    }

    @Preview
    @Composable
    fun DialogPreview() {
        VerifyEmailDialog()
    }

    @Composable
    fun VerifyEmailDialog() {
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .background(Color.White)
                    .padding(24.dp)
                    .clip(RoundedCornerShape(5.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TitleText()
                Spacer(modifier = Modifier.height(24.dp))
                DescriptionText()
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                    OkButton()
                }
            }
        }

    }

    @Composable
    fun OkButton() {
        Button(onClick = {
            verifyEmailDialogViewModel.isEmailVerified()
        }) {
            Text(text = stringResource(id = R.string.ok))
        }
    }

    @Composable
    fun DescriptionText() {
        Text(
            text = stringResource(id = R.string.pleaseVerifyYourEmail),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
    }

    @Composable
    fun TitleText() {
        Text(
            text = stringResource(id = R.string.verifyYourEmail),
            modifier = Modifier.padding(12.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }


}