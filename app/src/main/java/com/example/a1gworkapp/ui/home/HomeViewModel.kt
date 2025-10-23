package com.example.a1gworkapp.ui.home

import android.util.Log
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1gworkapp.data.DaySchedule
import com.example.a1gworkapp.network.RetrofitClient
import com.example.a1gworkapp.network.SalaryDto
import com.example.a1gworkapp.network.ScheduleDto
import com.example.a1gworkapp.ui.components.ScheduleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class HomeViewModel: ViewModel() {

    private val apiService = RetrofitClient.apiService

    private val _salaryData = MutableStateFlow<List<SalaryDto>>(emptyList())
    private val _scheduleData = MutableStateFlow<List<ScheduleDto>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    val salaryData: StateFlow<List<SalaryDto>> = _salaryData.asStateFlow()
    val scheduleData: StateFlow<List<ScheduleDto>> = _scheduleData.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadData(salarySheetsId: String, scheduleSheetId: String, employeeName: String) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            Log.d("HomeViewModel", "Загрузка данных для $employeeName...")

            try {
                val salary = apiService.getSalary(
                    spreadsheetId = salarySheetsId,
                    employeeName = employeeName
                )
                _salaryData.value = salary
                Log.d("HomeViewModel", "Зарплата загружена: ${salary.size} записей.")

                val schedule = apiService.getSchedule(spreadsheetId = scheduleSheetId)
                _scheduleData.value = schedule
                Log.d("HomeViewModel", "График загружен: ${schedule.size} записей.")
            }catch (e: Exception) {
                Log.e("HomeViewModel", "Ошибка при загрузке данных", e)
                _errorMessage.value = "Не удалось загрузить данные."
            } finally {
                _isLoading.value = false
            }
        }
    }

    val scheduleState: StateFlow<ScheduleUiState> = _scheduleData.map { rawScheduleList ->
        val groupedByWeek = rawScheduleList.groupBy { it.week.trim().uppercase(Locale.ROOT) }
        Log.d("HomeViewModel_Debug", "Ключи после группировки: ${groupedByWeek.keys}")
        val thisWeek = groupedByWeek["ТЕКУЩАЯ НЕДЕЛЯ"] ?: emptyList()
        val nextWeek = groupedByWeek["НЕКСТ НЕДЕЛЯ"] ?: emptyList()
        val thisWeekGrouped = groupScheduleByDay(thisWeek)
        val nextWeekGrouped = groupScheduleByDay(nextWeek)

        ScheduleUiState(
            thisWeekSchedule = thisWeekGrouped,
            nextWeekSchedule = nextWeekGrouped
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScheduleUiState()
    )

    private fun groupScheduleByDay (scheduleList: List<ScheduleDto>): List<DaySchedule> {
        if (scheduleList.isEmpty()) return emptyList()

        val groupedByDate = scheduleList.groupBy { it.date }
        return groupedByDate.map { (_, entries) ->
            val firstEntry = entries.first()
            DaySchedule(
                dayName = firstEntry.day,
                date = firstEntry.date,
                workers = entries.map { it.employee },
                workTimes = entries.map { it.workTime }
            )
        }.sortedBy { it.date }
    }
}