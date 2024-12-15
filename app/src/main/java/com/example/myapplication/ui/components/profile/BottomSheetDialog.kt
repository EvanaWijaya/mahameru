package com.example.myapplication.ui.components.profile

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.components.ActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    selectedImageUri: Uri?,
    onDismiss: () -> Unit,
    onSave: (Uri) -> Unit
) {
    if (selectedImageUri != null) {
        ModalBottomSheet(onDismissRequest = onDismiss) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
                ) {
                    ActionButton(
                        onClick = onDismiss,
                        label = "Batal",
                        containerColor = colorResource(id = R.color.colorSecondaryVariant),
                        fontSize = 12.sp,
                    )
                    ActionButton(
                        onClick = { onSave(selectedImageUri) },
                        label = "Simpan",
                        containerColor = colorResource(id = R.color.colorSecondaryVariant),
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}
