package com.example.myapplication

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.Calendar

@Composable
fun TicketBookingScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf("") }
    var adultTickets by remember { mutableStateOf(0) }
    var childTickets by remember { mutableStateOf(0) }
    val totalCost = (adultTickets * 10000) + (childTickets * 5000)

    val steps = listOf("Pilih Tiket", "Isi\nData", "Bayar Tiket", "Tiket Online")

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Progress bar with labels
                StepIndicator(currentStep = 1, totalSteps = 4, steps = steps)

                // Top App Bar
                TopAppBar(
                    title = { Text("Detail Pemesanan") },
                    backgroundColor = Color(0xFFFFFFFF),
                    contentColor = Color.Black,
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_back),
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    },
                    modifier = Modifier.padding(0.dp) // Remove extra padding
                )
            }
        }
    ) { contentPadding ->
        // Gunakan LazyColumn untuk membuat halaman dapat di-scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Kalender
            item {
                Text(text = "Pilih Tanggal", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                CalendarView(selectedDate = selectedDate) { date ->
                    selectedDate = date
                }
            }

            // Pilihan Tiket
            item {
                Text(text = "Jumlah Tiket", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Anak usia < 1 tahun gratis", fontSize = 14.sp, color = Color.Gray)
            }

            item {
                TicketCounter(
                    label = "Dewasa (Usia 6 tahun ke atas)",
                    price = 10000,
                    count = adultTickets,
                    onCountChange = { adultTickets = it }
                )
                TicketCounter(
                    label = "Anak-anak (Usia 1 - 5 tahun)",
                    price = 5000,
                    count = childTickets,
                    onCountChange = { childTickets = it }
                )
            }

            // Total Harga
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total Bayar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "Rp $totalCost", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            // Tombol Lanjutkan
            item {
                Spacer(modifier = Modifier.height(300.dp))
                Button(
                    onClick = { navController.navigate("detailPengunjung") },
                    enabled = totalCost > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (totalCost > 0) Color(0xFF0B746D) else Color.Gray
                    )
                ) {
                    Text(text = "Lanjutkan", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun CalendarView(selectedDate: String, onDateSelected: (String) -> Unit) {
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

@Composable
fun StepIndicator(currentStep: Int, totalSteps: Int, steps: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF0B746D),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .padding(horizontal = 16.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress bar with steps and lines
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..totalSteps) {
                // Column untuk Lingkaran dan Teks
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f) // Ruang merata untuk setiap langkah
                ) {
                    // Lingkaran
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (i <= currentStep) Color(0xFFFF7F50) else Color(0x80FF7F50),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$i",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Deskripsi di Bawah Lingkaran
                    Text(
                        text = steps[i - 1],
                        color = if (i <= currentStep) Color(0xFFFFffff) else Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(60.dp), // Lebar tetap untuk konsistensi
                        maxLines = 2 // Batasi hingga 2 baris
                    )
                }

                // Garis Antar Lingkaran
                if (i < totalSteps) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f) // Membagi sisa ruang antar lingkaran
                            .background(if (i <= currentStep) Color(0xFFFF7F50) else Color.LightGray)
                    )
                }
            }
        }
    }
}


@Composable
fun TicketCounter(label: String, price: Int, count: Int, onCountChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, fontSize = 14.sp)
            Text(text = "IDR $price", fontSize = 14.sp, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (count > 0) onCountChange(count - 1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_minus), // Replace with your minus icon resource
                    contentDescription = "Decrease",
                    tint = Color(0xFF0B746D)
                )
            }
            Text(text = "$count", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { onCountChange(count + 1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_plus), // Replace with your plus icon resource
                    contentDescription = "Increase",
                    tint = Color(0xFF0B746D)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketBookingScreenPreview() {
    TicketBookingScreen(navController = rememberNavController())
}