package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.ActionButton
import com.example.myapplication.ui.screens.Poppins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VisitorDialog(
    bottomSheetState : ModalBottomSheetState ,
    scope : CoroutineScope ,
    expanded : Boolean ,
    onExpandedChange : (Boolean) -> Unit ,
    selectedItem : String ,
    onItemSelected : (String) -> Unit ,
    items : List<String> ,
    onAddVisitor : () -> Unit ,
) {
    Column(
        modifier=Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier=Modifier.fillMaxWidth() ,
            verticalAlignment=Alignment.CenterVertically
        ) {
            IconButton(onClick={ scope.launch { bottomSheetState.hide() } }) {
                Icon(
                    painter=painterResource(id=R.drawable.icon_back) ,
                    contentDescription="Back" ,
                    tint=Color.Black
                )
            }
            Spacer(modifier=Modifier.width(8.dp))
            Text(
                text="Pilih Data Pengunjung" ,
                fontSize=18.sp ,
                style=TextStyle(fontWeight=FontWeight.Medium , fontSize=18.sp)
            )
        }
        Spacer(modifier=Modifier.height(16.dp))
        AddVisitorButton(onClick=onAddVisitor)
        Spacer(modifier=Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded=expanded ,
            onExpandedChange=onExpandedChange
        ) {
            OutlinedTextField(
                value=selectedItem ,
                onValueChange={} ,
                readOnly=true ,
                modifier=Modifier
                    .fillMaxWidth()
                    .clickable { onExpandedChange(true) } ,
                shape=RoundedCornerShape(10.dp) ,
                trailingIcon={ ExposedDropdownMenuDefaults.TrailingIcon(expanded=expanded) } ,
                colors=OutlinedTextFieldDefaults.colors(
                    focusedBorderColor=colorResource(id=R.color.colorSecondaryVariant) ,
                    unfocusedBorderColor=colorResource(id=R.color.teal_700)
                )
            )
            ExposedDropdownMenu(
                expanded=expanded ,
                onDismissRequest={ onExpandedChange(false) }
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        content = {
                            Text(
                                text = item,
                                style = TextStyle(fontSize = 16.sp, fontFamily = Poppins)
                            )
                        },
                        onClick = {
                            onItemSelected(item)
                            onExpandedChange(false)
                        }
                    )
                    if (index < items.size - 1) {
                        Divider(modifier = Modifier.padding(horizontal = 8.dp))
                    }

                }
            }


        }

        Spacer(modifier=Modifier.height(16.dp))

        ActionButton(
            label="Lanjutkan" ,
            onClick={
                scope.launch { bottomSheetState.hide() }

            } ,
            enabled=selectedItem.isNotEmpty() ,
            containerColor=if (selectedItem.isNotEmpty()) Color(0xFFFF7F50) else Color.Gray ,
            modifier=Modifier
                .align(Alignment.CenterHorizontally)
                .height(56.dp),

        )
        Spacer(modifier=Modifier.height(32.dp))


    }
}


