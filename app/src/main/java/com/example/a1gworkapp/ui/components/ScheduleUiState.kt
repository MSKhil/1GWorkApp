package com.example.a1gworkapp.ui.components

import com.example.a1gworkapp.data.DaySchedule

data class ScheduleUiState(
    val thisWeekSchedule: List<DaySchedule> = emptyList(),
    val nextWeekSchedule: List<DaySchedule> = emptyList()
)