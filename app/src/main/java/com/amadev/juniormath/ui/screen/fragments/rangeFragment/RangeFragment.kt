package com.amadev.juniormath.ui.screen.fragments.rangeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.amadev.juniormath.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RangeFragmentUI()
            }
        }
    }

    @Preview
    @Composable
    fun RangeFragmentUI() {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(32.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            )
            {
                FragmentTitleText()
                SubTitleText()
            }
        }
    }

    @Composable
    fun SubTitleText() {
        Text(
            text = stringResource(id = R.string.multiplication),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colors.primary
        )
    }

    @Composable
    fun FragmentTitleText() {
        Text(
            text = stringResource(id = R.string.category),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = MaterialTheme.colors.onSurface
        )
    }
}