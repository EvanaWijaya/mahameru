package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun CustomFloatingActionButton(onClick: () -> Unit = {}, containerColor : Color) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = containerColor ,
        contentColor = Color.White,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.edit) ,
            contentDescription = "Edit",
            modifier = Modifier.size(24.dp)
        )
    }
}