package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.ActionButton

@Composable
fun WelcomeScreen(navController : NavController) {
    val buttonModifier=Modifier
        .padding(bottom=10.dp)
        .width(200.dp)

    val fontSize=18.sp
    Box(
        modifier=Modifier.fillMaxSize()
    ) {
        Image(
            painter=painterResource(id=R.drawable.tepaspapandayanhd) ,
            contentDescription="BackgroundWelcome" ,
            Modifier.fillMaxSize() ,
            contentScale=ContentScale.Crop
        )
        Column(
            modifier=Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.38f) // Bagian bawah layar (25%)
                .align(Alignment.BottomCenter) , // Posisi di bawah layar
            horizontalAlignment=Alignment.CenterHorizontally ,
            verticalArrangement=Arrangement.Center // Rata tengah secara vertikal
        ) {
            ActionButton(
                onClick={
                    navController.navigate("register")
                } ,
                modifier=buttonModifier ,
                label=stringResource(id=R.string.createAccount) ,
                fontSize=fontSize

            )

            ActionButton(
                onClick={
                    navController.navigate("login")
                } ,
                modifier=buttonModifier ,
                label=stringResource(id=R.string.login) ,
                fontSize=fontSize
            )
        }
    }
}

@Preview(showBackground=true , showSystemUi=true , device=Devices.PIXEL_3A)
@Composable
private fun RegisterScreenPreview() {
    MaterialTheme {
        WelcomeScreen(navController=rememberNavController())
    }
}