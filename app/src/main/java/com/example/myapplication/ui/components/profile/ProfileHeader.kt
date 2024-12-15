package com.example.myapplication.ui.components.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.ActionButton
import com.example.myapplication.ui.components.EditDialog
import com.example.myapplication.ui.components.ProfilePicture
import com.example.myapplication.view_model.UserViewModel

@Composable
fun ProfileHeader(viewModel : UserViewModel) {
    val buttonTextSize=12.sp
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showEditEmailDialog by remember { mutableStateOf(false) }
    val context=LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val filePickerLauncher=rememberLauncherForActivityResult(
        contract=ActivityResultContracts.GetContent() ,
        onResult={ uri : Uri? ->
            uri?.let {
                selectedImageUri=it
                showBottomSheet=true
            }
        }
    )
    Box(
        modifier=Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter=painterResource(id=R.drawable.bg_profile) ,
            contentDescription=null ,
            modifier=Modifier
                .fillMaxWidth()
                .height(250.dp) ,
            contentScale=ContentScale.Crop
        )

        // Profil foto dan nama pengguna
        Column(
            horizontalAlignment=Alignment.CenterHorizontally ,
            verticalArrangement=Arrangement.Center ,
            modifier=Modifier
                .align(Alignment.Center)
                .padding(top=40.dp)
        ) {
            Box(
                modifier=Modifier
                    .size(80.dp)
                    .padding(4.dp)
            ) {
                ProfilePicture(viewModel.pictureUrl.value ,
                    onClick={
                        filePickerLauncher.launch("image/*")
                    }
                )
            }
            Spacer(modifier=Modifier.height(8.dp))
            Text(
                text=viewModel.username.value ,
                fontSize=18.sp ,
                fontWeight=FontWeight.Bold ,
                color=Color.White
            )
            Spacer(modifier=Modifier.height(8.dp))
            Row(
                horizontalArrangement=Arrangement.spacedBy(60.dp)
            ) {
                ActionButton(
                    onClick={ showEditProfileDialog=true } ,
                    label="Ubah Profile" ,
                    fontSize=buttonTextSize ,
                    containerColor=colorResource(id=R.color.colorSecondaryVariant) ,
                    textColor=Color.White
                )
                ActionButton(
                    onClick={ showEditEmailDialog=true } ,
                    label="Ubah Email" ,
                    fontSize=buttonTextSize ,
                    containerColor=colorResource(id=R.color.colorSecondaryVariant) ,
                    textColor=Color.White
                )

            }
        }
    }

    if (showEditProfileDialog) {
        EditDialog(
            title="Nama Pengguna Baru" ,
            initialValue=viewModel.username.value ,
            onDismiss={ showEditProfileDialog=false } ,
            onSave={ newUsername ->
                showEditProfileDialog=false
                viewModel.username.value=newUsername
                viewModel.patchProfile(context)
            }
        )
    }

    if (showEditEmailDialog) {
        EditDialog(
            title="Email Baru" ,
            initialValue=viewModel.email.value ,
            onDismiss={ showEditEmailDialog=false } ,
            onSave={ newEmail ->
                showEditEmailDialog=false
                viewModel.email.value=newEmail
                viewModel.patchProfile(context)

            }
        )
    }

    if (showBottomSheet) {
        BottomSheetDialog(
            selectedImageUri=selectedImageUri ,
            onDismiss={ showBottomSheet=false } ,
            onSave={
                selectedImageUri?.let {
                    val multipartFile=viewModel.createMultipartFromUri(context , it)
                    viewModel.putProfilePicture(context , multipartFile)
                }
                showBottomSheet=false
            }
        )
    }


}