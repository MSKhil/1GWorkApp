package com.example.a1gworkapp.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class UserData(val city: String, val shop: String, val employee: String)

val allUsers = listOf(
    UserData("Владивосток", "Седанка сити", "Хиль М"),
    UserData("Владивосток", "Седанка сити", "Кондратюк А"),
    UserData("Владивосток", "Седанка сити", "Мигалев А"),
    UserData("Владивосток", "Седанка сити", "Калиновский Р"),
    UserData("Владивосток", "Калина Молл", "Древетняк В"),
    UserData("Владивосток", "Калина Молл", "Олешко М"),
    UserData("Владивосток", "Калина Молл", "Филатов Н"),
    UserData("Владивосток", "Калина Молл", "Бобрышев К"),
    UserData("Владивосток", "Калина Молл", "Строев И"),
    UserData("Владивосток", "Светланская", "Петров А"),
    UserData("Владивосток", "Светланская", "Ким Д"),
    UserData("Владивосток", "Светланская", "Гаврыш В"),
    UserData("Находка", "Мега", "Дегтярев А"),
    UserData("Находка", "Мега", "Дегтярева В"),
    UserData("Уссурийск", "Тимирязева", "Каткина А"),
    UserData("Уссурийск", "Тимирязева", "Катане Д"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen() {
    var selectedCity by remember { mutableStateOf("") }
    var selectedShop by remember { mutableStateOf("") }
    var selectedEmployee by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val cities = remember { allUsers.map { it.city }.distinct() }
    val shops = remember(selectedCity){
        derivedStateOf {
            if (selectedCity.isNotBlank()) {
                allUsers
                    .filter { it.city == selectedCity }
                    .map { it.shop }
                    .distinct()
            } else {
                emptyList()
            }
        }
    }.value
    val employees = remember(selectedShop){
        derivedStateOf {
            if (selectedShop.isNotBlank()) {
                allUsers
                    .filter { it.shop == selectedShop }
                    .map { it.employee }
                    .distinct()
            } else{
                emptyList()
            }
        }
    }.value
    var isCityMenuExpended by remember { mutableStateOf(false) }
    var isShopMenuExpended by remember { mutableStateOf(false) }
    var isEmployeeMenuExpended by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
//Меню выбора города
        ExposedDropdownMenuBox (
            expanded = isCityMenuExpended,
            onExpandedChange = { isCityMenuExpended = it }
        ){
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedCity,
                onValueChange = {},
                label = { Text("Город") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCityMenuExpended) }
            )

            ExposedDropdownMenu(
                expanded = isCityMenuExpended,
                onDismissRequest = { isCityMenuExpended = false }
            ){
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = {
                            selectedCity = city
                            isCityMenuExpended = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LaunchedEffect(key1 = selectedCity) {
            selectedShop = ""
            selectedEmployee = ""
        }
//Меню выбора магазина
        ExposedDropdownMenuBox(
            expanded = isShopMenuExpended,
            onExpandedChange = { isShopMenuExpended = it }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedShop,
                onValueChange = {},
                label = { Text("Магазин") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isShopMenuExpended) }
            )

            ExposedDropdownMenu(
                expanded = isShopMenuExpended,
                onDismissRequest = { isShopMenuExpended = false }
            ) {
                shops.forEach { shop ->
                    DropdownMenuItem(
                        text = { Text(text = shop) },
                        onClick = {
                            selectedShop = shop
                            isShopMenuExpended = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LaunchedEffect(key1 = selectedShop) {
            selectedEmployee = ""
        }
//Меню выбора сотрудника
        ExposedDropdownMenuBox(
            expanded = isEmployeeMenuExpended,
            onExpandedChange = { isEmployeeMenuExpended = it }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedEmployee,
                onValueChange = {},
                label = { Text("Сотрудник") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEmployeeMenuExpended) }
            )

            ExposedDropdownMenu(
                expanded = isEmployeeMenuExpended,
                onDismissRequest = { isEmployeeMenuExpended = false }
            ) {
                employees.forEach { employee ->
                    DropdownMenuItem(
                        text = { Text(text = employee) },
                        onClick = {
                            selectedEmployee = employee
                            isEmployeeMenuExpended = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {}) {
            Text("Войти")
        }
    }
}