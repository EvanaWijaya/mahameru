package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.ActionButton
import com.example.myapplication.ui.components.CardHeader
import com.example.myapplication.ui.components.CheckboxWithLabel
import com.example.myapplication.ui.components.ClickableText
import com.example.myapplication.ui.components.PasswordField
import com.example.myapplication.ui.components.TextFieldWithIcon
import com.example.myapplication.utils.showToast
import com.example.myapplication.view_model.AuthViewModel

@Composable
fun LoginScreen(navController: NavController?, viewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    var passwordVisibility by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
    ) {
        CardHeader(imageRes = R.drawable.animasi_login, title = stringResource(id = R.string.welcome))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(33.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                stringResource(id = R.string.login),
                color = colorResource(id = R.color.colorPrimary),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 3.sp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFieldWithIcon(
                    value = viewModel.username.value,
                    onValueChange = { viewModel.username.value = it },
                    label =  stringResource(id = R.string.username) ,
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Person , contentDescription = null)
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                PasswordField(
                    value = viewModel.password.value,
                    onValueChange = { viewModel.password.value = it },
                    passwordVisibility = passwordVisibility ,
                    onVisibilityChange = { passwordVisibility = !passwordVisibility }
                )

                Spacer(Modifier.height(13.dp))

                CheckboxWithLabel(
                    isChecked = isChecked ,
                    onCheckedChange = { isChecked = it } ,
                    label = stringResource(id = R.string.savePassword) ,
                    onClick = {

                        showToast(context, "Fitur Belum Tersedia")

                    }
                )

                Spacer(Modifier.height(27.dp))

                ActionButton(
                    onClick = {
                        viewModel.login(context)
                    },
                    label = if (viewModel.isLoading.value) "Loading..." else stringResource(id = R.string.login)
                )

                Row {
                    Text(
                        stringResource(id = R.string.doYouHaveAccount1),
                        Modifier.padding(top = 32.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    ClickableText(
                        text = stringResource(id = R.string.register) ,
                        onClick = {

                            navController?.navigate("register") {
                                popUpTo("login") { inclusive = false }
                            }
                            viewModel.clearForm()

                        }
                    )
                }

            }
        }
    }
}