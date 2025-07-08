package com.example.a1gworkapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a1gworkapp.R

//Создадим панель навигации, для перехода на таблицы с данными
@Composable
fun LinkBar() {
    Card (
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, bottom = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.navigation_background)),
        border = BorderStroke(2.dp, colorResource(R.color.navigation_bar)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Row (
                modifier =  Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(color = colorResource(R.color.navigation_background).copy(alpha = 0f)),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.navigation_card), shape = CircleShape)
                        .border(
                            2.dp,
                            color = colorResource(R.color.navigation_bar),
                            shape = CircleShape
                        )
                ) {
                    Image(painter = painterResource(R.drawable.work_schedule),
                        contentDescription = "schedule",
                        modifier = Modifier.size(70.dp))
                }
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.navigation_card), shape = CircleShape)
                        .border(
                            2.dp, color = colorResource(R.color.navigation_bar), shape = CircleShape
                        )
                ) {
                    Image(painter = painterResource(R.drawable.cash),
                        contentDescription = "salary",
                        modifier = Modifier.size(70.dp))
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.navigation_card), shape = CircleShape)
                        .border(
                            2.dp, color = colorResource(R.color.navigation_bar), shape = CircleShape
                        )
                ) {
                    Image(painter = painterResource(R.drawable.info),
                        contentDescription = "info",
                        modifier = Modifier.size(70.dp))
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.navigation_card), shape = CircleShape)
                        .border(
                            2.dp, color = colorResource(R.color.navigation_bar), shape = CircleShape
                        )

                ) {
                    Image(painter = painterResource(R.drawable.info),
                        contentDescription = "info",
                        modifier = Modifier.size(70.dp))

                }
            }
        }
    }
}