package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinishOnboarding: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    // Data untuk onboarding
    val pages = listOf(
        OnboardingPage(
            description = "Selamat Datang\ndi Tepas\nPapandayan",
            buttonText = "Jelajah"
        ),
        OnboardingPage(
            description = "Nikmati pemandian\nair panas dengan pemandangan Gunung Cikuray",
            buttonText = "Selanjutnya"
        ),
        OnboardingPage(
            description = "Temukan pengalaman bermalam dan berkebun di alam terbuka.",
            buttonText = "Mulai"
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Pager konten onboarding
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(page = pages[page])
        }

        // Indikator dan tombol di atas gambar latar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Indikator halaman
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.Green,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Tombol untuk navigasi
            val isLastPage = pagerState.currentPage == pages.lastIndex
            Button(
                onClick = {
                    if (isLastPage) {
                        onFinishOnboarding()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f) // Tombol menyesuaikan 80% lebar layar
            ) {
                Text(text = pages[pagerState.currentPage].buttonText)
            }
        }
    }
}

// Data class untuk konten onboarding
data class OnboardingPage(
    val description: String,
    val buttonText: String
)

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    val PoppinsBold = FontFamily(
        Font(R.font.poppins_bold) // Mengacu pada file `poppins_bold.ttf`
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Gambar latar belakang
        Image(
            painter = painterResource(id = R.drawable.bg_terang), // Ganti dengan ID gambar Anda
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Menyesuaikan skala gambar
        )

        // Konten utama halaman
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = page.description,
                fontSize = 37.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsBold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 100.dp)
            )
        }
    }
}
