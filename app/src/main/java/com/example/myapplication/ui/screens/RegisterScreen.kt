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
import androidx.compose.material.icons.outlined.Email
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
import com.example.myapplication.ui.components.ClickableText
import com.example.myapplication.ui.components.PasswordField
import com.example.myapplication.ui.components.TextFieldWithIcon
import com.example.myapplication.view_model.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel= viewModel()) {
    val context = LocalContext.current
    var passwordVisibility by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
    ) {
        CardHeader(imageRes = R.drawable.register, title = stringResource(id = R.string.welcome))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(33.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                stringResource(id = R.string.register),
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
                    onValueChange = {
                        viewModel.username.value = it
                        viewModel.validateForm()
                    },
                    label =  stringResource(id = R.string.username) ,
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Person , contentDescription = null)
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                TextFieldWithIcon(
                    value = viewModel.email.value,
                    onValueChange = {
                        viewModel.email.value = it
                        viewModel.validateForm()
                    },                               label =  stringResource(id = R.string.email) ,
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email , contentDescription = null)
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                PasswordField(
                    value = viewModel.password.value,
                    onValueChange = { viewModel.password.value = it
                        viewModel.validateForm()
                    },
                    passwordVisibility = passwordVisibility ,
                    onVisibilityChange = { passwordVisibility = !passwordVisibility }
                )

                Spacer(modifier = Modifier.height(18.dp))

                PasswordField(
                    value = viewModel.confirmPassword.value,
                    onValueChange = { viewModel.confirmPassword.value = it
                        viewModel.validateForm()

                                    },
                    passwordVisibility = passwordVisibility ,
                    labelText =  stringResource(id = R.string.confirm_password) ,
                    onVisibilityChange = { passwordVisibility = !passwordVisibility }
                )

                Spacer(Modifier.height(27.dp))

                ActionButton(
                    onClick = {

                        viewModel.register(context,navController)

                    } ,
                    label = stringResource(id = R.string.register),
                    enabled = viewModel.isFormValid.value
                )

                Row {
                    Text(
                        stringResource(id = R.string.doYouHaveAccount2),
                        Modifier.padding(top = 32.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    ClickableText(
                        text = stringResource(id = R.string.login) ,
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = false }
                            }
                        }
                    )
                }

            }
        }
    }
}