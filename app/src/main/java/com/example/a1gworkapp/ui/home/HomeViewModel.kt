package com.example.a1gworkapp.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1gworkapp.common.Resource
import com.example.a1gworkapp.data.DataRepository
import com.example.a1gworkapp.network.SalaryDto
import com.example.a1gworkapp.network.ScheduleDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

data class ScheduleUiState(
    val thisWeekSchedule: List<DaySchedule> = emptyList(),
    val nextWeekSchedule: List<DaySchedule> = emptyList()
)

data class DaySchedule(
    val dayName: String,
    val date: String,
    val workers: List<String>,
    val workTimes: List<String>,
    val isToday: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _currentUserParams = MutableStateFlow<UserParams?>(null)

    val salaryData: StateFlow<List<SalaryDto>> = _currentUserParams
        .filterNotNull()
        .flatMapLatest { params ->
            repository.getSalaryStream(params.employeeName)
                .onEach { salariesFromDb ->
                    Log.d(
                        "VM_Debug",
                        "БАЗА->VM: Получено зарплат из потока: ${salariesFromDb.size}"
                    )
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5), emptyList())

    private val scheduleDataStream: Flow<List<ScheduleDto>> = _currentUserParams
        .filterNotNull()
        .flatMapLatest {
            repository.getScheduleStream()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val scheduleState: StateFlow<ScheduleUiState> = scheduleDataStream.map { rawScheduleList ->
        val groupedByWeek = rawScheduleList.groupBy { it.week.trim().uppercase(Locale.ROOT) }
        val thisWeek = groupedByWeek["ТЕКУЩАЯ НЕДЕЛЯ"] ?: emptyList()
        val nextWeek = groupedByWeek["НЕКСТ НЕДЕЛЯ"] ?: emptyList()
        val thisWeekGrouped = groupScheduleByDay(thisWeek)
        val nextWeekGrouped = groupScheduleByDay(nextWeek)

        ScheduleUiState(
            thisWeekSchedule = thisWeekGrouped,
            nextWeekSchedule = nextWeekGrouped
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ScheduleUiState())


    fun start(
        salarySheetId: String,
        scheduleSheetId: String,
        employeeName: String
    ) {
        val newParams = UserParams(salarySheetId, scheduleSheetId, employeeName)
        if (_currentUserParams.value == newParams) return

        _currentUserParams.value = newParams
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _currentUserParams.value?.let { params ->
                _isRefreshing.value = true
                _errorMessage.value = null

                val result = repository.refreshData(
                    params.salarySheetId,
                    params.scheduleSheetId,
                    params.employeeName
                )

                when (result) {
                    is Resource.Success<*> -> {
                        Log.d("HomeViewModel", "Обновление прошло успешно")
                    }
                    is Resource.Error -> {
                        _errorMessage.value = result.message
                        Log.e("HomeViewModel", "Ошибка: ${result.message}")
                    }
                }
                _isRefreshing.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupScheduleByDay(scheduleList: List<ScheduleDto>): List<DaySchedule> {
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

    private data class UserParams(
        val salarySheetId: String,
        val scheduleSheetId: String,
        val employeeName: String
    )
}