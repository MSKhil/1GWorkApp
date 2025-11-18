package com.example.a1gworkapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salary")
data class SalaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employee: String,
    val salary: Double,
    val cash: Double,
    val card: Double,
    val workShift: Int,
    val month: String
)
@Entity("schedule")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val week: String,
    val date: String,
    val day: String,
    val employee: String,
    val workTime: String
)