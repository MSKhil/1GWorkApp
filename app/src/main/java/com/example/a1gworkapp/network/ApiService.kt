package com.example.a1gworkapp.network

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("macros/s/AKfycbwMSncG73CPMMUBONxkocxALFNog_IJSyUubEGg92751pvp-J2oBvo7R_CKNr9VFTQ/exec?action=getUsers")
    suspend fun getUsers(): List<UserDto>

    @GET("macros/s/AKfycbwMSncG73CPMMUBONxkocxALFNog_IJSyUubEGg92751pvp-J2oBvo7R_CKNr9VFTQ/exec?action=getSalary")
    suspend fun getSalary(
        @Query("spreadsheetId") spreadsheetId: String,
        @Query("employeeName") employeeName: String,
    ): List<SalaryDto>

    @GET("macros/s/AKfycbwMSncG73CPMMUBONxkocxALFNog_IJSyUubEGg92751pvp-J2oBvo7R_CKNr9VFTQ/exec?action=getSchedule")
    suspend fun getSchedule(
        @Query("spreadsheetId") spreadsheetId: String
    ): List<ScheduleDto>
}