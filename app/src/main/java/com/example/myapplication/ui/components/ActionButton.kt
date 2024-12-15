package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun ActionButton(
    onClick: () -> Unit ,
    label: String ,
    modifier: Modifier= Modifier.width(150.dp) ,
    enabled: Boolean = true ,
    fontSize: TextUnit = 20.sp ,
    containerColor: Color = colorResource(id = R.color.colorTertiary) ,
    disabledContainerColor: Color = colorResource(id = R.color.colorButtonDisable) ,
    textColor: Color = colorResource(id = R.color.colorBackground)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        ),
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = fontSize
        )
    }
}
