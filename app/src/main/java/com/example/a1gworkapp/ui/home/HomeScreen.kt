package com.example.a1gworkapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a1gworkapp.ui.components.Greeting
import com.example.a1gworkapp.ui.components.SalaryCard
import com.example.a1gworkapp.ui.components.TaskCard
import com.example.a1gworkapp.ui.components.LinkBar

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()

    Scaffold (
        bottomBar =  {
            LinkBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .verticalScroll(scrollState)
        ) {
            Greeting("Хиль Марк", "Июнь", 10)
            SalaryCard(
                100000, 60000, 40000, 130000,70000, 60000
            )
            Spacer(modifier = Modifier.height(10.dp))
            TaskCard()
            Spacer(modifier = Modifier.height(110.dp))
        }
    }
}