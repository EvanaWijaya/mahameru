package com.example.myapplication.ui.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun ProfilePicture(
    pictureUrl: String,
    onClick: (() -> Unit)? = null
) {
    val imageBitmap = if (pictureUrl.isNotEmpty() && pictureUrl.startsWith("data:image/")) {
        val base64Image = pictureUrl.replace("data:image/jpeg;base64,", "")
        val decodedByteArray = Base64.decode(base64Image, Base64.DEFAULT)

        val bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        bitmap.asImageBitmap()
    } else {
        null
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .let { modifier ->
                    if (onClick != null) modifier.clickable { onClick() }
                    else modifier
                }
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .let { modifier ->
                    if (onClick != null) modifier.clickable { onClick() }
                    else modifier
                }
        )
    }
}
