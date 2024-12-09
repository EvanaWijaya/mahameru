package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PembayaranScreen() {
    val steps = listOf("Pilih Tiket", "Isi\nData", "Bayar Tiket", "Tiket Online")
    val poppinsFontFamily = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Step Indicator tetap tanpa padding
        StepIndicator(currentStep = 1, totalSteps = 4, steps = steps)

        // Konten utama dengan padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Data Pemesan Section
            Text(
                text = "Data pemesan",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Informasi tiket akan dikirimkan ke kontak pemesan",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFF00796B), RoundedCornerShape(8.dp)) // Border hijau
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp) // Padding internal
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Nama Lengkap", fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)
                    Text(text = "AYUMI NINGSIH", fontFamily = poppinsFontFamily)
                    Text(text = "Alamat Email", fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)
                    Text(text = "ayuminingsih@gmail.com", fontFamily = poppinsFontFamily)
                    Text(text = "Nomor Telepon", fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)
                    Text(text = "082167134588", fontFamily = poppinsFontFamily)
                }
            }

            // Ticket Information Section
            TicketRow(label = "1 Tiket Masuk\nDewasa (x1)", price = "Rp10.000")
            TicketRow(label = "1 Tiket Masuk\nAnak-anak (x1)", price = "Rp5.000")

            // Payment Method Section
            Text(
                text = "Metode Pembayaran",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Transfer Bank",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = poppinsFontFamily
            )

            Spacer(modifier = Modifier.weight(1f))

            // Total Bayar Section dengan Divider
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp), // Lebih kecil untuk naik ke atas
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Bayar",
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                    Text(
                        text = "Rp15.000",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = poppinsFontFamily
                    )
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(150.dp))

            // Bayar Button
            Button(
                onClick = { /* Handle click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F3C)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Bayar", color = Color.White, fontSize = 16.sp, fontFamily = poppinsFontFamily)
            }
        }
    }
}

@Composable
fun TicketRow(label: String, price: String) {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            val poppinsFontFamily = FontFamily(
                Font(R.font.poppins_regular, FontWeight.Normal),
                Font(R.font.poppins_bold, FontWeight.Bold)
            )

            // Split the label into two parts: the ticket description and the quantity.
            val parts = label.split("(", limit = 2)
            val description = parts[0].trim() // "1 Tiket Masuk Dewasa"
            val quantity = if (parts.size > 1) "(${parts[1]}" else "" // "(x1)"

// Teks tiket dengan bagian bold dan normal
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = " $quantity", // spasi agar terlihat terpisah
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFontFamily
            )
        }
        Text(
            text = price,
            color = Color(0xFFFF6F3C),
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPembayaranScreen() {
    PembayaranScreen()
}

