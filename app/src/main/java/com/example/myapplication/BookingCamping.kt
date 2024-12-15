package com.example.myapplication

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun CampingBookingScreen() {
    var selectedDate by remember { mutableStateOf("") }
    var additionalItems by remember { mutableStateOf(mutableMapOf<String, Int>()) }
    val itemPrices = mapOf(
        "Buah strawberry 1 kg" to 35000,
        "Kentang 1 kg" to 15000,
        "Matras" to 15000,
        "Sleeping bag" to 20000,
        "Kursi & meja outdoor" to 50000,
        "Lampu tenda" to 15000,
        "Kasur" to 50000,
        "Kayu bakar" to 35000,
        "Grill pan" to 20000,
        "Gas portable" to 35000
    )

    val totalAdditionalCost = additionalItems.entries.sumOf { (item, count) ->
        itemPrices[item]?.times(count) ?: 0
    }
    val packagePrice = 200000
    val totalCost = packagePrice + totalAdditionalCost

    Scaffold(
        topBar = {
            Column {
                // Progress Bar
                StepIndicator(currentStep = 1, totalSteps = 4, steps = listOf("Pilih Tiket", "Isi\nData", "Bayar\nTiket", "Tiket\nOnline"))

                // Top App Bar
                TopAppBar(
                    title = { Text("Detail Pemesanan") },
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    navigationIcon = {
                        IconButton(onClick = { /* Handle back action */ }) {
                            Icon(painter = painterResource(id = R.drawable.icon_back), contentDescription = "Back")
                        }
                    }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pilih Tanggal
            item {
                Text(text = "Pilih Tanggal", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                CampingCalendarView(selectedDate = selectedDate) { date ->
                    selectedDate = date
                }
            }

            // Detail Paket
            item {
                Text(text = "Pesanan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text("Paket Camping Biasa", fontWeight = FontWeight.Bold)
                    Text(
                        text = "Fasilitas:\n• Tenda kapasitas 3-4 orang\n• Sleeping bag\n• Matras",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Tambahan
            item {
                Text(text = "Tambahan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                itemPrices.keys.forEach { item ->
                    AdditionalItemRow(
                        itemName = item,
                        price = itemPrices[item] ?: 0,
                        count = additionalItems[item] ?: 0,
                        onCountChange = { count ->
                            additionalItems[item] = count
                        }
                    )
                }
            }

            // Total Harga
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Bayar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "Rp $totalCost", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            // Tombol Pesan
            item {
                Button(
                    onClick = { /* Handle ticket booking */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0B746D))
                ) {
                    Text("Pesan Tiket", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun AdditionalItemRow(itemName: String, price: Int, count: Int, onCountChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = itemName, fontSize = 14.sp)
            Text(text = "IDR $price", fontSize = 12.sp, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (count > 0) onCountChange(count - 1) }) {
                Icon(painter = painterResource(id = R.drawable.icon_minus), contentDescription = "Decrease", tint = Color(0xFF0B746D))
            }
            Text(text = "$count", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            IconButton(onClick = { onCountChange(count + 1) }) {
                Icon(painter = painterResource(id = R.drawable.icon_plus), contentDescription = "Increase", tint = Color(0xFF0B746D))
            }
        }
    }
}

@Composable
fun CampingCalendarView(selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var showDialog by remember { mutableStateOf(false) } // Untuk mengontrol tampilan dialog

    // Tampilkan tombol untuk membuka DatePickerDialog
    Button(
        onClick = { showDialog = true },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF7F50), // Ganti dengan warna yang diinginkan
            contentColor = Color.White)) {
        Text(text = if (selectedDate.isEmpty()) "Pilih Tanggal" else "Tanggal: $selectedDate")
    }

    // Tampilkan dialog hanya jika showDialog == true
    if (showDialog) {
        DatePickerDialog(
            ContextThemeWrapper(context, R.style.CustomDatePickerTheme), // Gunakan tema
            { _, year, month, dayOfMonth ->
                val selected = "$dayOfMonth/${month + 1}/$year"
                onDateSelected(selected)
                showDialog = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCampingBookingScreen() {
    CampingBookingScreen()
}
