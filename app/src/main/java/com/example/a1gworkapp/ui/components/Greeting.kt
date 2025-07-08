package com.example.a1gworkapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a1gworkapp.R

//Описание сверху над карточками. Менеджер и месяц
@Composable
fun Greeting(
    worker: String,
    month: String,
    dayCount: Int
) {
    Column(
        modifier = Modifier
            .padding(start = 45.dp, top = 70.dp, bottom = 15.dp, end = 45.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text(
            text = worker,
            fontSize = 35.sp,
            fontWeight = FontWeight.Medium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.month) + " " + month,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Row{
                Text(
                    text = "Смен:",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$dayCount",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}