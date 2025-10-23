package com.example.a1gworkapp.data

data class DaySchedule(
    val dayName: String,
    val date: String,
    val workers: List<String>,
    val workTimes: List<String>
)
