import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.StepIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailPengunjungScreen(navController: NavHostController) {
    val steps = listOf("Pilih Tiket", "Isi\nData", "Bayar Tiket", "Tiket Online")
    val dataPengunjung = remember { mutableStateListOf<Map<String, String>>() }
    var currentBottomSheet by remember { mutableStateOf("pilih_pengunjung") }
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val coroutineScope = rememberCoroutineScope()
    var tempNama by remember { mutableStateOf("") }
    var tempNIK by remember { mutableStateOf("") }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            when (currentBottomSheet) {
                "pilih_pengunjung" -> PilihPengunjungContent(
                    dataPengunjung = dataPengunjung,
                    onAddDataClicked = {
                        tempNama = ""
                        tempNIK = ""
                        coroutineScope.launch {
                            currentBottomSheet = "isi_data_pengunjung"
                            sheetState.expand()
                        }
                    },
                    onPengunjungSelected = { pengunjung ->
                        tempNama = pengunjung["nama"] ?: ""
                        tempNIK = pengunjung["nik"] ?: ""
                        coroutineScope.launch {
                            currentBottomSheet = "isi_data_pengunjung"
                            sheetState.expand()
                        }
                    }
                )
                "isi_data_pengunjung" -> IsiDataPengunjungContent(
                    nama = tempNama,
                    nik = tempNIK,
                    onSimpanClicked = { nama, nik ->
                        if (nama.isNotBlank() && nik.isNotBlank()) {
                            val updatedPengunjung = mapOf("nama" to nama, "nik" to nik)
                            if (tempNama.isNotBlank() && tempNIK.isNotBlank()) {
                                // Edit existing pengunjung
                                val index = dataPengunjung.indexOfFirst { it["nik"] == tempNIK }
                                if (index != -1) dataPengunjung[index] = updatedPengunjung
                            } else {
                                // Add new pengunjung
                                dataPengunjung.add(updatedPengunjung)
                            }
                        }
                        coroutineScope.launch {
                            currentBottomSheet = "pilih_pengunjung"
                            sheetState.collapse()
                        }
                    }
                )
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            StepIndicator(currentStep = 1, totalSteps = 4, steps = steps)

            TopAppBar(
                title = { Text("Detail Pengunjung") },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )

            // Tambahkan variabel jumlah tiket (contoh: diatur ke 2 untuk 2 tiket)
            val jumlahTiket = 2 // Misal jumlah tiket bisa didapatkan dari parameter sebelumnya

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$jumlahTiket Tiket Masuk",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Min, 19 Nov 2024",
                                    fontSize = 14.sp,
                                    color = Color(0xFFFF6D00)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "1 Dewasa, 1 Anak-anak",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Data Pengunjung",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Tampilkan kotak sebanyak jumlah tiket
                items(jumlahTiket) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(2.dp, Color(0xFF00897B), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Klik tombol edit untuk menampilkan BottomSheet
                                    coroutineScope.launch {
                                        tempNama = dataPengunjung.getOrNull(index)?.get("nama") ?: ""
                                        tempNIK = dataPengunjung.getOrNull(index)?.get("nik") ?: ""
                                        currentBottomSheet = "isi_data_pengunjung"
                                        sheetState.expand()
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pengunjung ${index + 1} - ${dataPengunjung.getOrNull(index)?.get("nama") ?: "Belum diisi"}",
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f),
                                color = Color.Black
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "Edit",
                                tint = Color(0xFFFF6D00),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(300.dp))
                    Button(
                        onClick = { /* Handle pesan action */ },
                        enabled = dataPengunjung.size >= jumlahTiket, // Tombol hanya aktif jika semua data diisi
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (dataPengunjung.size >= jumlahTiket) Color(0xFFFF6D00) else Color.Gray
                        )
                    ) {
                        Text(
                            text = "Pesan",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IsiDataPengunjungContent(
    nama: String,
    nik: String,
    onSimpanClicked: (String, String) -> Unit
) {
    var tempNama by remember { mutableStateOf(nama) }
    var tempNIK by remember { mutableStateOf(nik) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Isi Data Pengunjung", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = tempNama,
            onValueChange = { tempNama = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = tempNIK,
            onValueChange = { tempNIK = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSimpanClicked(tempNama, tempNIK) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00897B))
        ) {
            Text(text = "Simpan", color = Color.White)
        }
    }
}

@Composable
fun PilihPengunjungContent(
    dataPengunjung: List<Map<String, String>>,
    onAddDataClicked: () -> Unit,
    onPengunjungSelected: (Map<String, String>) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pilih Data Pengunjung", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(dataPengunjung) { pengunjung ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPengunjungSelected(pengunjung) }
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${pengunjung["nama"]} - ${pengunjung["nik"]}",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onAddDataClicked,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF6D00))
        ) {
            Text(text = "Tambah Pengunjung", color = Color.White)
        }
    }
}