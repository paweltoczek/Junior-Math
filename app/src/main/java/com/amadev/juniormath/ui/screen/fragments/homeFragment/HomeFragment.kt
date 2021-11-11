package com.amadev.juniormath.ui.screen.fragments.homeFragment

import HorizontalChart
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amadev.juniormath.R
import com.amadev.juniormath.ui.screen.components.titleTexts.FragmentDescriptionText
import com.amadev.juniormath.ui.screen.dialogs.logoutDialog.LogoutDialog
import com.amadev.juniormath.ui.theme.JuniorMathTheme
import com.amadev.juniormath.util.BundleKeys
import com.amadev.juniormath.util.Categories
import com.amadev.juniormath.util.Util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen()
                setUpViewModel()
                setUpObservers()
                setUpOnBackPressedCallback()
            }
        }
    }

    private fun setUpObservers() {
        homeFragmentViewModel.apply {
            popUpMessage.observe(viewLifecycleOwner) {
                showSnackBar(requireView(), it)
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun setUpViewModel() {
        homeFragmentViewModel.apply {
            getDataFromDatabaseIfPossible()
        }
    }

    private fun navigateToStatisticsFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_statisticsFragment)
    }

    private fun navigateToRangeFragment(categoryName: String) {
        val bundle = Bundle()
        bundle.putString(BundleKeys.Category.toString(), categoryName)
        findNavController().navigate(R.id.action_homeFragment_to_rangeFragment, bundle)
    }

    private fun setUpOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (homeFragmentViewModel.isUserLogged) {
                    activity?.finishAffinity()
                } else {
                    navigateToLoginFragment()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_loginScreenFragment)
    }

    private fun showLogoutDialog() {
        val dialog = LogoutDialog()
        dialog.show(childFragmentManager, null)
    }

    @Composable
    fun HomeScreen() {
        val state = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()

        JuniorMathTheme {
            Scaffold(
                scaffoldState = state,
                drawerContent = {
                    NavDrawer()
                },
                content = {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxSize()
                            .padding(32.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            HomeScreenUI(state, coroutineScope)
                        }
                    }

                })
        }
    }

    @Composable
    fun NavDrawer() {
        JuniorMathTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colors.secondary)
                        .padding(24.dp, 24.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = homeFragmentViewModel.userEmail,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body1,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier.absolutePadding(24.dp, 24.dp),
                            text = stringResource(id = R.string.whatYouWantToDo),
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.h3,
                            fontSize = 18.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NavButton(buttonText = stringResource(id = R.string.support))
                        NavButton(buttonText = stringResource(id = R.string.feedback))
                        NavButton(buttonText = stringResource(id = R.string.logout))
                    }
                    Column {
                        TermsOfUseText()
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun TermsOfUseText() {
        Text(
            text = stringResource(id = R.string.termsOfUse),
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            color = Color.Black
        )
    }

    @Composable
    fun NavButton(buttonText: String) {
        val logoutText = stringResource(id = R.string.logout)
        Button(
            onClick = {
                if (buttonText == logoutText) {
                    showLogoutDialog()
                }
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(24.dp, 12.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
        ) {
            Text(
                text = buttonText,
                color = Color.Black,
                style = MaterialTheme.typography.h3,
                fontSize = 16.sp
            )
        }
    }

    @Composable
    fun HomeScreenUI(state: ScaffoldState, coroutineScope: CoroutineScope) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeText()
                OpenMenuButton(state, coroutineScope)
            }
            WelcomeText()
            Column(
                modifier = Modifier.absolutePadding(0.dp, 48.dp),
                horizontalAlignment = Alignment.Start
            ) {
                TitleTextSmall(stringResource(id = R.string.statistics))
                SubText(stringResource(id = R.string.checkHowYour))
                StatisticsButton()
                Spacer(modifier = Modifier.height(32.dp))
                TitleTextSmall(text = stringResource(id = R.string.practice))
                SubText(text = stringResource(id = R.string.practiceMakesPerfect))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        CategoryButton(
                            stringResource(id = R.string.addition),
                            painterResource(R.drawable.addition),
                            Categories.Addition.name
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        CategoryButton(
                            stringResource(id = R.string.subtraction),
                            painterResource(R.drawable.subtraction),
                            Categories.Subtraction.name
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .absolutePadding(0.dp, 32.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        CategoryButton(
                            stringResource(id = R.string.multiplication),
                            painterResource(R.drawable.multiplication),
                            Categories.Multiplication.name
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CategoryButton(
                            stringResource(id = R.string.division),
                            painterResource(R.drawable.division),
                            Categories.Division.name
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun StatisticsButton() {
        val availableOnlyForLogged = stringResource(id = R.string.availableOnlyForLogged)

        Button(
            onClick = {
                if (homeFragmentViewModel.isUserLogged) {
                    navigateToStatisticsFragment()
                } else {
                    showSnackBar(requireView(), availableOnlyForLogged)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(0.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
        ) {
            if (homeFragmentViewModel.isUserLogged.not()) {
                FragmentDescriptionText(string = availableOnlyForLogged)
            } else {
                if (homeFragmentViewModel.dataLoaded.value) {
                    HorizontalChart(
                        correctAnswers = homeFragmentViewModel.databaseCorrectAnswers.value,
                        totalQuestions = homeFragmentViewModel.databaseTotalQuestions.value
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }

    @Composable
    fun OpenMenuButton(state: ScaffoldState, coroutineScope: CoroutineScope) {
        TextButton(
            onClick = {
                coroutineScope.launch {
                    state.drawerState.open()
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Icon(Icons.Default.Menu, contentDescription = "", tint = MaterialTheme.colors.primary)
        }
    }

    @Composable
    fun TitleTextSmall(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface
        )
    }

    @Composable
    fun SubText(text: String) {
        Text(
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 16.dp),
            text = text,
            style = MaterialTheme.typography.body2,
            fontSize = 12.sp,
            color = MaterialTheme.colors.onSurface
        )
    }

    @Composable
    fun HomeText() {
        Text(
            text = stringResource(id = R.string.home),
            style = MaterialTheme.typography.body1,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface
        )
    }

    @Composable
    fun WelcomeText() {
        val userEmail = homeFragmentViewModel.userEmail ?: ""

        Text(
            buildAnnotatedString {
                withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface
                        )
                    ) {
                        append(stringResource(id = R.string.welcome))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        append("\n")
                        append(userEmail)
                    }
                }
            }
        )
    }

    @Composable
    fun CategoryButton(buttonText: String, icon: Painter, categoryName: String) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
            onClick = { navigateToRangeFragment(categoryName) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .width(100.dp)
                        .height(65.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(text = buttonText, style = MaterialTheme.typography.body1, fontSize = 12.sp)
            }
        }
    }

}