package com.amadev.juniormath.ui.screen.categoryScreen

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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.ui.theme.VerticalGradientBrush
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val bundle = Bundle()

    companion object {
        const val ADDITION = "ADDITION"
        const val SUBTRACTION = "SUBTRACTION"
        const val MULTIPLICATION = "MULTIPLICATION"
        const val DIVISION = "DIVISION"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }

    @Preview
    @Composable
    fun CategoryScreen() {
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = VerticalGradientBrush),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp, 24.dp)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    CategoryText()
                    CategoryAddition()
                    CategorySubtraction()
                    CategoryMultiplication()
                    CategoryDivision()
                }

            }
        }
    }

    @Composable
    fun CategoryText() {
        Text(
            modifier = Modifier.padding(0.dp, 32.dp),
            text = stringResource(id = R.string.selectCategory),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun CategoryAddition() {
        Button(
            onClick = { setUpBundle(ADDITION) }, modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = stringResource(id = R.string.addition))
        }
    }

    @Composable
    fun CategorySubtraction() {
        Button(
            onClick = { setUpBundle(SUBTRACTION) }, modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = stringResource(id = R.string.subtraction))
        }
    }

    @Composable
    fun CategoryMultiplication() {
        Button(
            onClick = { setUpBundle(MULTIPLICATION) }, modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = stringResource(id = R.string.multiplication))
        }
    }

    @Composable
    fun CategoryDivision() {
        Button(
            onClick = { setUpBundle(DIVISION) }, modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = stringResource(id = R.string.division))
        }
    }

    private fun setUpBundle(category: String) {
        bundle.putString("category", category)
    }
}