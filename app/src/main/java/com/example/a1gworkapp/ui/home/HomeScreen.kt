package com.example.a1gworkapp.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onLogoutClick: () -> Unit
) {
    val salaryData by homeViewModel.salaryData.collectAsState()
    val scheduleState by homeViewModel.scheduleState.collectAsState()
    val currentMonthSalary = salaryData.lastOrNull()

    val salarySheetId = "1_P1MF7hW_MoGY9U-WXXmZv-WyeyhJPIzB0j3FR3e6nU"
    val scheduleSheetId = "1r_jXKjDm42ITw3bdWaDNMfaQnvIf9Ye9u8NR2KoQ8y8"

    val context = LocalContext.current
    val motivationUrl =
        "https://docs.google.com/spreadsheets/d/1ynj_7wBLXgnFCaqLhr_OTuv61fm_reU0/edit?gid=1355084412#gid=1355084412"
    val actionsUrl =
        "https://docs.google.com/spreadsheets/d/1swtOJv0Lu5_Mu1USUltOeAg2FQFw4eiZTxLO3bTDo1s/edit?gid=0#gid=0"
    val salaryUrl =
        "https://docs.google.com/spreadsheets/d/$salarySheetId"
    val scheduleUrl =
        "https://docs.google.com/spreadsheets/d/$scheduleSheetId"

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onLogoutClick
            ) {
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
                    card = salary.card.toInt(),
                    onOpenUrlClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(salaryUrl))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TaskCard(
                thisWeekSchedule = scheduleState.thisWeekSchedule,
                nextWeekSchedule = scheduleState.nextWeekSchedule,
                onOpenUrlClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scheduleUrl))
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, salaryUrl.toUri())
                    context.startActivity(intent)
                }) {
                    Text("Получка")
                }
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, scheduleUrl.toUri())
                    context.startActivity(intent)
                }) {
                    Text("График")
                }
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, actionsUrl.toUri())
                    context.startActivity(intent)
                }) {
                    Text("Акции")
                }
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, motivationUrl.toUri())
                    context.startActivity(intent)
                }) {
                    Text("Мотивация")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
