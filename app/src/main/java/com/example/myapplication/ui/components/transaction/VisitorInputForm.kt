package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.ActionButton
import com.example.myapplication.ui.components.TextFieldWithLabel

@Composable
fun VisitorInputForm(
    name : String ,
    nik : String ,
    onNameChange : (String) -> Unit ,
    onNikChange : (String) -> Unit ,
    onPostVisitor : () -> Unit,
    onClick : () -> Unit
) {
    Column(modifier=Modifier.padding(16.dp)) {

        Row(
            modifier=Modifier.fillMaxWidth() ,
            verticalAlignment=Alignment.CenterVertically
        ) {
            IconButton(onClick={onClick()}) {
                Icon(
                    painter=painterResource(id=R.drawable.icon_back) ,
                    contentDescription="Back" ,
                    tint=Color.Black
                )
            }
            Spacer(modifier=Modifier.width(8.dp))
            Text(
                text="Buat Data Pengunjung" ,
                fontSize=18.sp ,
                style=TextStyle(fontWeight=FontWeight.Medium , fontSize=18.sp)
            )
        }
        Spacer(modifier=Modifier.height(16.dp))

        TextFieldWithLabel(
            value=name ,
            onValueChange=onNameChange ,
            label="Nama Lengkap"
        )
        Spacer(modifier=Modifier.padding(8.dp))
        TextFieldWithLabel(
            value=nik ,
            onValueChange=onNikChange ,
            label="NIK"
        )
        Spacer(modifier=Modifier.padding(8.dp))


        ActionButton(
            label="Simpan" ,
            onClick={
                onPostVisitor()


            } ,
            enabled=nik.isNotEmpty() && name.isNotEmpty() ,
            containerColor=if (nik.isNotEmpty() && name.isNotEmpty() &&name.length == 18) Color(0xFFFF7F50) else Color.Gray ,
            modifier=Modifier
                .align(Alignment.CenterHorizontally)
            .height(56.dp),


        )
        Spacer(modifier=Modifier.padding(8.dp))



    }
}
