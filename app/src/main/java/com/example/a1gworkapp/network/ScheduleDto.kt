package com.example.a1gworkapp.network

import com.google.gson.annotations.SerializedName

data class ScheduleDto(
    @SerializedName("week")
    val week: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("day")
    val day: String,

    @SerializedName("employee")
    val employee: String,

    @SerializedName("workTime")
    val workTime: String
)