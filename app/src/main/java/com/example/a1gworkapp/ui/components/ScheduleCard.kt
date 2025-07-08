package com.example.a1gworkapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a1gworkapp.R
import com.example.a1gworkapp.data.DaySchedule

//График и Задачи
@Composable
fun TaskCard(
) {
    val thisWeekSchedule = listOf(
        DaySchedule(
            dayName = "Понедельник",
            workers = listOf("Хиль", "Кондратюк"),
            workTimes = listOf("10-20", "12-21")
        ),
        DaySchedule(
            dayName = "Вторник",
            workers = listOf("Хиль", "Мигалев", "Калиновский"),
            workTimes = listOf("10-19", "11-20", "12-21")
        ),
        DaySchedule(
            dayName = "Среда",
            workers = listOf("Мигалев"),
            workTimes = listOf("10-22")
        ),
        DaySchedule(
            dayName = "Четверг",
            workers = listOf("Хиль"),
            workTimes = listOf("10-21")
        ),
        DaySchedule(
            dayName = "Пятница",
            workers = listOf("Хиль"),
            workTimes = listOf("10-21")
        ),
        DaySchedule(
            dayName = "Суббота",
            workers = listOf("Хиль", "Мигалев", "Кондратюк", "Калиновский"),
            workTimes = listOf("10-18", "10-18", "13-21", "13-21")
        ),
        DaySchedule(
            dayName = "Воскресенье",
            workers = listOf("Хиль", "Мигалев", "Кондратюк"),
            workTimes = listOf("10-18", "10-18", "13-21")
        )
    )
    val nextWeekSchedule = listOf(
        DaySchedule(
            dayName = "Понедельник",
            workers = listOf("Хиль", "Кондратюк"),
            workTimes = listOf("10-20", "12-21")
        ),
        DaySchedule(
            dayName = "Вторник",
            workers = listOf("Хиль", "Мигалев", "Калиновский"),
            workTimes = listOf("10-19", "11-20", "12-21")
        ),
        DaySchedule(
            dayName = "Среда",
            workers = listOf("Мигалев", "Хиль", "Лох"),
            workTimes = listOf("10-22", "10-24", "11-24")
        ),
        DaySchedule(
            dayName = "Четверг",
            workers = listOf("Мигалев", "Хиль", "Лох"),
            workTimes = listOf("10-22", "10-24", "11-24")
        ),
        DaySchedule(
            dayName = "Пятница",
            workers = listOf("Хиль"),
            workTimes = listOf("10-21")
        ),
        DaySchedule(
            dayName = "Суббота",
            workers = listOf("Хиль", "Мигалев", "Кондратюк", "Калиновский"),
            workTimes = listOf("10-18", "10-18", "13-21", "13-21")
        ),
        DaySchedule(
            dayName = "Воскресенье",
            workers = listOf("Хиль", "Мигалев", "Кондратюк"),
            workTimes = listOf("10-18", "10-18", "13-21")
        )
    )
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.task)),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
//Текущая неделя
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Текущая неделя",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, start = 7.dp, end = 7.dp, bottom = 10.dp)
            ) {
                LazyRow {
                    items(thisWeekSchedule) { dayData ->
                        DayScheduleCard(
                            dayName = dayData.dayName,
                            workers = dayData.workers,
                            workTimes = dayData.workTimes
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }
// Следующая неделя
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Следующая неделя",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, start = 7.dp, end = 7.dp, bottom = 10.dp)
            ) {
                LazyRow {
                    items(nextWeekSchedule) { dayData ->
                        DayScheduleCard(
                            dayName = dayData.dayName,
                            workers = dayData.workers,
                            workTimes = dayData.workTimes
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }
        }
    }
}