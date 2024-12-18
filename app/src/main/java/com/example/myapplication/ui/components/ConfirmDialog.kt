package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins

@Composable
fun ConfirmDialog(
    title : String ,
    subTitle : String ,
    onDismiss : () -> Unit ,
    onSave : (String) -> Unit
) {

    AlertDialog(
        containerColor=Color.White ,
        onDismissRequest={ onDismiss() } ,
        shape=RoundedCornerShape(16.dp) ,
        title={
            Text(
                text=title ,
                fontSize=16.sp ,
                fontWeight=FontWeight.W500 ,
                color=Color.Black ,
                style=TextStyle(
                    fontFamily=Poppins ,
                )
            )
        } ,
        text={
            Column(modifier=Modifier.fillMaxWidth()) {
                Spacer(modifier=Modifier.height(2.dp))
                Text(
                    text=subTitle ,
                    fontSize=14.sp ,
                    fontWeight=FontWeight.W400 ,
                    color=Color.Black ,
                    style=TextStyle(
                        fontFamily=Poppins ,
                    )
                )
            }
        } ,

        confirmButton={
            TextButton(onClick={ onSave("Simpan") }) {
                Text(
                    "Simpan" ,
                    color=Color(0xFF00897B) ,
                    fontSize=14.sp ,
                    fontWeight=FontWeight.W500 , style=TextStyle(
                        fontFamily=Poppins ,
                    )

                )
            }
        } ,
        dismissButton={
            TextButton(onClick={ onDismiss() }) {
                Text(
                    "Batal" ,
                    color=Color.Black
                )
            }
        }
    )
}
