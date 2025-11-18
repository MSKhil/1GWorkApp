package com.example.a1gworkapp.ui.home

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.a1gworkapp.ui.components.Header
import com.example.a1gworkapp.ui.components.SalaryCard
import com.example.a1gworkapp.ui.components.TaskCard
import androidx.core.net.toUri
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel, onLogoutClick: () -> Unit
) {
    val salaryData by homeViewModel.salaryData.collectAsState()
    val scheduleState by homeViewModel.scheduleState.collectAsState()
    val currentMonthSalary = salaryData.lastOrNull()

    val context = LocalContext.current
    val motivationUrl =
        "https://docs.google.com/spreadsheets/d/1ynj_7wBLXgnFCaqLhr_OTuv61fm_reU0/edit?gid=1355084412#gid=1355084412"
    val actionsUrl =
        "https://docs.google.com/spreadsheets/d/1swtOJv0Lu5_Mu1USUltOeAg2FQFw4eiZTxLO3bTDo1s/edit?gid=0#gid=0"

    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { homeViewModel.refresh() }
    )
    val isInitialLoading = currentMonthSalary == null && isRefreshing

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (currentMonthSalary != null) {
                    currentMonthSalary?.let { salary ->
                        Header(
                            workName = salary.employee,
                            month = salary.month,
                            dayCount = salary.workShift,
                            onLogoutClick = onLogoutClick
                        )
                    }


                    currentMonthSalary?.let { salary ->
                        SalaryCard(
                            salary = salary.salary.toInt(),
                            cash = salary.cash.toInt(),
                            card = salary.card.toInt(),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    TaskCard(
                        thisWeekSchedule = scheduleState.thisWeekSchedule,
                        nextWeekSchedule = scheduleState.nextWeekSchedule,
                    )
                } else {
                    Spacer(modifier = Modifier.height(200.dp))
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingBottomButton(
                    text = "Акция",
                    onclick = {
                        val intent = Intent(Intent.ACTION_VIEW, actionsUrl.toUri())
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f)
                )
                FloatingBottomButton(
                    text = "Мотивация",
                    onclick = {
                        val intent = Intent(Intent.ACTION_VIEW, motivationUrl.toUri())
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun FloatingBottomButton(
    text: String,
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onclick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(text = text)
    }
}

