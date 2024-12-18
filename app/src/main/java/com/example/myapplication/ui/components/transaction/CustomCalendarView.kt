package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.screens.Poppins
import java.util.Calendar

@Composable
fun CustomCalendarView(
    modifier: Modifier = Modifier,
    selectedDate: Calendar = Calendar.getInstance(),
    onDateSelected: (Calendar) -> Unit
) {
    val calendar = Calendar.getInstance()
    val currentMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val currentYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

    val daysInMonth = getDaysOfMonth(currentMonth.value, currentYear.value)

    Column(modifier = modifier.padding(16.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CalendarHeader(
                month = currentMonth.value,
                year = currentYear.value,
                onMonthChange = { newMonth, newYear ->
                    currentMonth.value = newMonth
                    currentYear.value = newYear
                }
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        WeekdayHeader()

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            items(daysInMonth) { day ->
                DayCell(
                    day = day,
                    isSelected = day.isSelected(selectedDate),
                    onClick = { onDateSelected(day.date!!) },
                    currentMonth = currentMonth.value
                )
            }
        }
    }
}

@Composable
fun CalendarHeader(
    month: Int,
    year: Int,
    onMonthChange: (Int, Int) -> Unit
) {
    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)

    val monthNames = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val upcomingMonths = (0..3).map { offset ->
        val newMonth = (currentMonth + offset) % 12
        val newYear = currentYear + (currentMonth + offset) / 12
        Pair(newMonth, newYear)
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(Color(0xFF00796B), RoundedCornerShape(8.dp))
            .clickable { isDropdownExpanded = true }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${monthNames[month]} $year",
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = Poppins,
        )

        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier.height(200.dp) ,
            containerColor = colorResource(id= R.color.teal_200)
        ) {
            upcomingMonths.forEach { (monthIndex, yearValue) ->
                DropdownMenuItem(
                    onClick = {
                        isDropdownExpanded = false
                        onMonthChange(monthIndex, yearValue)
                    },
                    text = {
                        Text(
                            text = "${monthNames[monthIndex]} $yearValue",
                            fontSize = 14.sp,
                            fontFamily = Poppins
                        )
                    }
                )
            }
        }
    }
}





@Composable
fun DayCell(day: Day, isSelected: Boolean, onClick: () -> Unit, currentMonth: Int) {
    val dayColor = when {
        day.date == null || day.date.get(Calendar.MONTH) != currentMonth -> Color(0xFFB3B3B3) // Di luar bulan
        day.date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || day.date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY -> Color(0xFF00796B) // Sabtu/Minggu
        else -> Color(0xFF000000) // Hari biasa
    }

    val backgroundColor = if (isSelected) Color(0xFF00796B) else Color.Transparent
    val textColor = if (isSelected) Color.White else dayColor

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        day.date?.let {
            Text(
                text = it.get(Calendar.DAY_OF_MONTH).toString(),
                fontSize = 11.sp,
                color = textColor,
                fontFamily = Poppins,

            )
        }
    }
}

data class Day(val date: Calendar?)

fun getDaysOfMonth(month: Int, year: Int): List<Day> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, 1)
    }

    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) - 2 + 7) % 7 // Adjust to Monday start
    val days = mutableListOf<Day>()

    for (i in 0 until firstDayOfWeek) {
        days.add(Day(null))
    }

    for (day in 1..daysInMonth) {
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        days.add(Day(date))
    }

    return days
}

fun Day.isSelected(selectedDate: Calendar): Boolean {
    return this.date?.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR) &&
            this.date?.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
            this.date?.get(Calendar.DAY_OF_MONTH) == selectedDate.get(Calendar.DAY_OF_MONTH)
}
