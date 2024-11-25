package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginActivity(navController: NavController) {

    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var passwordVisibility = remember { mutableStateOf(false) }
    var isChecked = remember { mutableStateOf(false) }
    var scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF00796B))
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.animasi_login),
                    contentDescription = "Welcome",
                    modifier = Modifier
                        .size(280.dp)
                )
                Text(
                    stringResource(id = R.string.welcome),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 3.sp
                )
            }

        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                stringResource(id = R.string.login),
                color = Color(0xFF00796B),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 3.sp
            )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = username.value,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    },
                    onValueChange = {username.value = it },
                    label = { Text("Nama Pengguna") }
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    value = password.value,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null
                        )
                    },
                    onValueChange = {password.value = it },
                    label = { Text("Kata Sandi") },
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisibility.value) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        }
                        IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    }
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Checkbox(
                            modifier = Modifier,
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF00796B))
                        )
                        Text(
                            stringResource(id = R.string.savePassword),
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        stringResource(
                            id = R.string.forgetPassword),
                        fontSize = 14.sp,
                        color = Color(0xFF00796B),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {  })
                }
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = { /* Aksi tombol masuk */ },
                    modifier = Modifier
                        .width(150.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6400)),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        stringResource(id = R.string.login), color = Color(0xFFFFFFFF),
                        fontSize = 20.sp)
                }
                Row {
                    Text(
                        stringResource(id = R.string.doYouHaveAccount1),
                        Modifier.padding(top = 32.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        stringResource(id = R.string.register),
                        Modifier
                            .padding(top = 32.dp)
                            .clickable {
                                navController.navigate("register"){
                                    popUpTo("welcome") { inclusive = false}
                                }
                            },
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginActivity(navController = rememberNavController())
    }
}