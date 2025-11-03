package com.example.a1gworkapp.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.a1gworkapp.R
import com.example.a1gworkapp.data.DaySchedule

@Composable
fun TaskCard(
    thisWeekSchedule: List<DaySchedule>,
    nextWeekSchedule: List<DaySchedule>,
    onOpenUrlClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.task)),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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
            Icon(
                imageVector = Icons.Default.OpenInBrowser,
                contentDescription = "Open in browser",
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable { onOpenUrlClick() }
            )
        }
    }
}