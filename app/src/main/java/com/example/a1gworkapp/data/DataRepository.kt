package com.example.a1gworkapp.data

import android.util.Log
import com.example.a1gworkapp.common.Resource
import com.example.a1gworkapp.network.ApiService
import com.example.a1gworkapp.network.SalaryDto
import com.example.a1gworkapp.network.ScheduleDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val appDao: AppDao
) {
    fun getSalaryStream(employeeName: String): Flow<List<SalaryDto>> {
        return appDao.getSalaryForEmployee(employeeName).map { entities ->
            entities.map { it.toSalaryDto() }
        }
    }

    fun getScheduleStream(): Flow<List<ScheduleDto>> {
        return appDao.getSchedule().map { entities ->
            entities.map { it.toScheduleDto() }
        }
    }

    suspend fun refreshData(salarySheetId: String, scheduleSheetId: String, employeeName: String): Resource<Unit> {
        return try {
            val freshSalaries = apiService.getSalary(salarySheetId, employeeName)
            val freshSchedule = apiService.getSchedule(scheduleSheetId)

            Log.d("Repo_Debug", "СЕТЬ: Зарплат: ${freshSalaries.size}, Графика: ${freshSchedule.size}")

            appDao.clearSalariesForEmployee(employeeName)
            appDao.clearSchedule()
            appDao.insertSalaries(freshSalaries.map { it.toSalaryEntity() })
            appDao.insertSchedule(freshSchedule.map { it.toScheduleEntity() })

            Log.d("DataRepository", "Data refreshed and saved to DB.")

            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e("DataRepository", "Failed to refresh data", e)

            Resource.Error(message = "Не удалось обновить данные: ${e.localizedMessage}", cause = e)
        }
    }
}

private fun SalaryDto.toSalaryEntity() = SalaryEntity(
    month = month,
    employee = employee,
    salary = salary,
    cash = cash,
    card = card,
    workShift = workShift
)

private fun SalaryEntity.toSalaryDto() = SalaryDto(
    month = month,
    employee = employee,
    salary = salary,
    cash = cash,
    card = card,
    workShift = workShift
)

private fun ScheduleDto.toScheduleEntity() = ScheduleEntity(
    week = week,
    date = date,
    day = day,
    employee = employee,
    workTime = workTime
)

private fun ScheduleEntity.toScheduleDto() = ScheduleDto(
    week = week,
    date = date,
    day = day,
    employee = employee,
    workTime = workTime
)