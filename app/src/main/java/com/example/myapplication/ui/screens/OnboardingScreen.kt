package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.models.OnboardingPage
import com.example.myapplication.ui.components.OnboardingPageContent
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavHostController, setOnboardingCompleted: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            description = stringResource(id = R.string.onboarding_welcome_text),
            buttonText = stringResource(id = R.string.onboarding_button_jelajah)
        ),
        OnboardingPage(
            description = stringResource(id = R.string.onboarding_hot_spring_text),
            buttonText = stringResource(id = R.string.onboarding_button_selanjutnya)
        ),
        OnboardingPage(
            description = stringResource(id = R.string.onboarding_experience_text),
            buttonText = stringResource(id = R.string.onboarding_button_mulai)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(page = pages[page])
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.Green,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val isLastPage = pagerState.currentPage == pages.lastIndex
            Button(
                onClick = {
                    if (isLastPage) {
                        setOnboardingCompleted()
                        navController.navigate("welcome") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = pages[pagerState.currentPage].buttonText)
            }
        }
    }
}


