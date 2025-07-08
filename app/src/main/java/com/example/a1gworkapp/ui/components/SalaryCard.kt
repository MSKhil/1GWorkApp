package com.example.a1gworkapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a1gworkapp.R

//Зарплата
@Composable
fun SalaryCard(
    salary1с: Int, cash1с: Int, card1с: Int, salaryf: Int, cashf: Int, cardf: Int
) {
    LazyRow (
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
// Начисление по 1С
        item {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(start = 15.dp),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(colorResource(R.color.your_balance)),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
//Общий баланс - надпись сверху
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 30.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = stringResource(R.string.balance),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(50.dp))
                        Text(
                            text = "$salary1с ₽",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
//Карточки (Нал и ЗП)
                    Row {
//Первая карточка - Нал. Создаём поле Card, добавляем изображение и 2 строки с описанием и кол-ом нала
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(30.dp),
                                elevation = CardDefaults.cardElevation(15.dp),
                                colors = CardDefaults.cardColors(colorResource(R.color.balance_card))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp, bottom = 20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.cash),
                                            contentDescription = "cash",
                                            modifier = Modifier.size(50.dp)
                                        )
                                        Text("   ")
                                        Text(
                                            text = "$cash1с ₽",
                                            fontSize = 23.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Box {
                                        Row (
                                            modifier = Modifier
                                                .padding(start = 20.dp, end = 20.dp)
                                        ){
                                            Text(
                                                text = stringResource(R.string.cash),
                                                fontSize = 25.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))
//Вторая карточка - ЗП. Создаём поле Card, добавляем изображение и 2 строки с описанием и кол-ом в ЗП
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(30.dp),
                                elevation = CardDefaults.cardElevation(15.dp),
                                colors = CardDefaults.cardColors(colorResource(R.color.balance_card))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp, bottom = 20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.card),
                                            contentDescription = "card",
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                        Text("   ")
                                        Text(
                                            text = "$card1с ₽",
                                            fontSize = 23.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Box {
                                        Row (
                                            modifier = Modifier
                                                .padding(start = 20.dp, end = 20.dp)
                                        ){
                                            Text(
                                                text = stringResource(R.string.payday),
                                                fontSize = 25.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Начисления по 1С",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium)
                    }
                }
            }
        }


// Начисление по факту
        item{
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 15.dp),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(colorResource(R.color.your_balance)),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
//Общий баланс - надпись сверху
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 30.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = stringResource(R.string.balance),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(50.dp))
                        Text(
                            text = "$salaryf ₽",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
//Карточки (Нал и ЗП)
                    Row {
//Первая карточка - Нал. Создаём поле Card, добавляем изображение и 2 строки с описанием и кол-ом нала
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(30.dp),
                                elevation = CardDefaults.cardElevation(15.dp),
                                colors = CardDefaults.cardColors(colorResource(R.color.balance_card))
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.cash),
                                            contentDescription = "cash",
                                            modifier = Modifier.size(50.dp)
                                        )
                                        Text("   ")
                                        Text(
                                            text = "$cashf ₽",
                                            fontSize = 23.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Box {
                                        Row (modifier = Modifier.padding(start = 20.dp, end = 20.dp)){
                                            Text(
                                                text = stringResource(R.string.cash),
                                                fontSize = 25.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))
//Вторая карточка - ЗП. Создаём поле Card, добавляем изображение и 2 строки с описанием и кол-ом в ЗП
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(30.dp),
                                elevation = CardDefaults.cardElevation(15.dp),
                                colors = CardDefaults.cardColors(colorResource(R.color.balance_card))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp, bottom = 20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.card),
                                            contentDescription = "card",
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                        Text("   ")
                                        Text(
                                            text = "$cardf ₽",
                                            fontSize = 23.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Box {
                                        Row (
                                            modifier = Modifier
                                                .padding(start = 20.dp, end = 20.dp)
                                        ){
                                            Text(
                                                text = stringResource(R.string.payday),
                                                fontSize = 25.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Начисления по факту",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}