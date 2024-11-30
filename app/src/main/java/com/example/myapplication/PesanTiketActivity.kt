@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun BookingScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var totalPrice by remember { mutableStateOf(0) }
    var adultCount by remember { mutableStateOf(0) }
    var childCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF7F5))
    ) {
        TopBar()
        ProgressIndicator()
        BookingDetails(
            selectedDate = selectedDate,
            onDateSelected = { date -> selectedDate = date },
            onAdultCountChange = { count ->
                adultCount = count
                totalPrice = (adultCount * 10_000) + (childCount * 5_000)
            },
            onChildCountChange = { count ->
                childCount = count
                totalPrice = (adultCount * 10_000) + (childCount * 5_000)
            },
            totalPrice = totalPrice
        )
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Detail Pemesanan",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}

@Composable
fun ProgressIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProgressStep(step = "Pilih Tiket", isActive = true)
        ProgressStep(step = "Isi Data Pengunjung", isActive = false)
        ProgressStep(step = "Konfirmasi Pembayaran", isActive = false)
        ProgressStep(step = "E-Tiket", isActive = false)
    }
}

@Composable
fun ProgressStep(step: String, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isActive) Color(0xFF04B486) else Color(0xFFBDBDBD),
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = step,
            color = if (isActive) Color.Black else Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun BookingDetails(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onAdultCountChange: (Int) -> Unit,
    onChildCountChange: (Int) -> Unit,
    totalPrice: Int
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Pilih Tanggal",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        DatePicker(selectedDate, onDateSelected)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Jumlah Tiket",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        TicketSelector(
            label = "Dewasa (Usia 6 tahun ke atas)",
            price = "IDR 10.000",
            count = 0,
            onCountChange = onAdultCountChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        TicketSelector(
            label = "Anak-anak (Usia 1 - 5 tahun)",
            price = "IDR 5.000",
            count = 0,
            onCountChange = onChildCountChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        TotalPrice(totalPrice)
        Spacer(modifier = Modifier.height(16.dp))
        ContinueButton()
    }
}

@Composable
fun DatePicker(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    var showPicker by remember { mutableStateOf(false) }

    Button(
        onClick = { showPicker = true },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF04B486))
    ) {
        Text(
            text = selectedDate?.format(dateFormatter) ?: "Pilih Tanggal",
            color = Color.White
        )
    }

    if (showPicker) {
        // Simple calendar dialog for picking dates
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = { showPicker = false }) {
                    Text(text = "OK")
                }
            },
            content = {
                // Implement calendar here
            }
        )
    }
}

@Composable
fun TicketSelector(
    label: String,
    price: String,
    count: Int,
    onCountChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = label, color = Color.Black)
            Text(text = price, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (count > 0) onCountChange(count - 1) }) {
                Icon(Icons.Filled.Remove, contentDescription = "Remove")
            }
            Text(text = count.toString(), modifier = Modifier.padding(horizontal = 8.dp))
            IconButton(onClick = { onCountChange(count + 1) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun TotalPrice(totalPrice: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Total Bayar", style = MaterialTheme.typography.titleMedium, color = Color.Black)
        Text(text = "Rp $totalPrice", style = MaterialTheme.typography.titleMedium, color = Color.Black)
    }
}

@Composable
fun ContinueButton() {
    Button(
        onClick = { /* Navigate to next screen */ },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF04B486))
    ) {
        Text(text = "Lanjutkan", color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookingScreen() {
    BookingScreen()
}