package com.example.a1gworkapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a1gworkapp.ui.components.Greeting
import com.example.a1gworkapp.ui.components.SalaryCard
import com.example.a1gworkapp.ui.components.TaskCard
import com.example.a1gworkapp.ui.components.LinkBar
import androidx.compose.runtime.getValue

@Composable
fun HomeScreen(
homeViewModel: HomeViewModel,
onLogoutClick: () -> Unit
) {
    val salaryData by homeViewModel.salaryData.collectAsState()
    val scheduleData by homeViewModel.scheduleData.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val errorMessage by homeViewModel.errorMessage.collectAsState()
    val scheduleState by homeViewModel.scheduleState.collectAsState()
    val currentMonthSalary = salaryData.lastOrNull()

    Scaffold(bottomBar = { LinkBar() }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Button(onClick = onLogoutClick) {
                Text("Выход")
            }

            currentMonthSalary?.let { salary ->
                Greeting(
                    worker = salary.employee,
                    month = salary.month,
                    dayCount = salary.workShift
                )
                SalaryCard(
                    salary = salary.salary.toInt(),
                    cash = salary.cash.toInt(),
                    card = salary.card.toInt()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TaskCard(
                thisWeekSchedule = scheduleState.thisWeekSchedule,
                nextWeekSchedule = scheduleState.nextWeekSchedule
            )
        }
    }
}