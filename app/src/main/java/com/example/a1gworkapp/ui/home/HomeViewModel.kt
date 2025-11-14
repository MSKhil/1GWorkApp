package com.example.a1gworkapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1gworkapp.data.DaySchedule
import com.example.a1gworkapp.network.RetrofitClient
import com.example.a1gworkapp.network.SalaryDto
import com.example.a1gworkapp.network.ScheduleDto
import com.example.a1gworkapp.ui.components.ScheduleUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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
            Log.d("HomeViewModel", "Параллельная загрузка данных для $employeeName...")

            try {
                val salaryDeferred = async {
                    apiService.getSalary(
                        spreadsheetId = salarySheetsId,
                        employeeName = employeeName
                    )
                }

                val scheduleDeferred = async {
                    apiService.getSchedule(spreadsheetId = scheduleSheetId)
                }

                val salary = salaryDeferred.await()
                val schedule = scheduleDeferred.await()

                _salaryData.value = salary
                Log.d("HomeViewModel", "Зарплата загружена: ${salary.size} записей.")
                _scheduleData.value = schedule
                Log.d("HomeViewModel", "График загружен: ${schedule.size} записей.")
            }catch (e: Exception) {
                Log.e("HomeViewModel", "Ошибка при параллельной загрузке данных", e)
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

        val today = LocalDate.now()
        val apiDateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        val groupedByDate = scheduleList.groupBy { it.date }
        return groupedByDate.map { (_, entries) ->
            val firstEntry = entries.first()
            val entryDate = ZonedDateTime.parse(firstEntry.date, apiDateFormatter)
                .withZoneSameInstant(java.time.ZoneId.systemDefault())
                .toLocalDate()
            DaySchedule(
                dayName = firstEntry.day,
                date = firstEntry.date,
                workers = entries.map { it.employee },
                workTimes = entries.map { it.workTime },
                isToday = entryDate.isEqual(today)
            )
        }.sortedBy { it.date }
    }
}