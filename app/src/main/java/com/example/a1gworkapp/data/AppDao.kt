package com.example.a1gworkapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    //Зарплата
    @Query("SELECT * FROM salary WHERE employee = :employeeName")
    fun getSalaryForEmployee(employeeName: String): Flow<List<SalaryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalaries(salaries: List<SalaryEntity>)

    @Query("DELETE FROM salary WHERE employee = :employeeName")
    suspend fun clearSalariesForEmployee(employeeName: String)

    //График
    @Query("SELECT * FROM schedule")
    fun getSchedule(): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: List<ScheduleEntity>)

    @Query("DELETE FROM schedule")
    suspend fun clearSchedule()
}