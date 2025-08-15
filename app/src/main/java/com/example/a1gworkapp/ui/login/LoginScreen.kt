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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen() {
    var selectedCity by remember { mutableStateOf("") }
    var selectedShop by remember { mutableStateOf("") }
    var selectedEmployee by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val cities = listOf("Владивосток", "Находка", "Уссурийск")
    val shops = listOf("Седанка сити", "Калина Молл", "Светланская")
    val employees = listOf("Хиль Марк", "Кондратюк Александр", "Мигалев Алексей", "Калиновский Роман")
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
                label = { Text("Магазин") },
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