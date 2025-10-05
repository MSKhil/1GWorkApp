package com.example.a1gworkapp.network

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("город")
    val city: String,

    @SerializedName("магазин")
    val shop: String,

    @SerializedName("сотрудник")
    val employeeName: String,

    @SerializedName("пароль")
    val password: String,

    @SerializedName("salary_spreadsheet_id")
    val salarySheetId: String,

    @SerializedName("schedule_spreadsheet_id")
    val scheduleSheetId: String
)