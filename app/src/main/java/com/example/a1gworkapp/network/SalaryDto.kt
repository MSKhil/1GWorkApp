package com.example.a1gworkapp.network

import com.google.common.math.DoubleMath
import com.google.gson.annotations.SerializedName

data class SalaryDto(
    @SerializedName("month")
    val month: String,

    @SerializedName("employee")
    val employee: String,

    @SerializedName("salaryFact")
    val salary: Double,

    @SerializedName("cashFact")
    val cash: Double,

    @SerializedName("cardFact")
    val card: Double,

    @SerializedName("workShift")
    val workShift: Int
)