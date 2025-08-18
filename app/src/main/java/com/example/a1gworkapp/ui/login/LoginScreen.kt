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
import androidx.compose.material3.Scaffold
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    cities: List<String>,
    shops: List<String>,
    employees: List<String>,
    selectedCity: String,
    selectedShop: String,
    selectedEmployee: String,
    passwordValue: String,
    isCityMenuExpanded: Boolean,
    isShopMenuExpanded: Boolean,
    isEmployeeMenuExpanded: Boolean,
    onCityMenuExpandedChange: (Boolean) -> Unit,
    onShopMenuExpandedChange: (Boolean) -> Unit,
    onEmployeeMenuExpandedChange: (Boolean) -> Unit,
    onCitySelected: (String) -> Unit,
    onShopSelected: (String) -> Unit,
    onEmployeeSelected: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
//Меню выбора города
        ExposedDropdownMenuBox (
            expanded = isCityMenuExpanded,
            onExpandedChange = { onCityMenuExpandedChange(it) }
        ){
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedCity,
                onValueChange = {},
                label = { Text("Город") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCityMenuExpanded) }
            )

            ExposedDropdownMenu(
                expanded = isCityMenuExpanded,
                onDismissRequest = { onCityMenuExpandedChange(false) }
            ){
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = {
                            onCitySelected(city)
                            onCityMenuExpandedChange(false)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
//Меню выбора магазина
        ExposedDropdownMenuBox(
            expanded = isShopMenuExpanded,
            onExpandedChange = { onShopMenuExpandedChange(it) }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedShop,
                onValueChange = {},
                label = { Text("Магазин") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isShopMenuExpanded) }
            )

            ExposedDropdownMenu(
                expanded = isShopMenuExpanded,
                onDismissRequest = { onShopMenuExpandedChange(false) }
            ) {
                shops.forEach { shop ->
                    DropdownMenuItem(
                        text = { Text(text = shop) },
                        onClick = {
                            onShopSelected(shop)
                            onShopMenuExpandedChange(false)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
//Меню выбора сотрудника
        ExposedDropdownMenuBox(
            expanded = isEmployeeMenuExpanded,
            onExpandedChange = { onEmployeeMenuExpandedChange(it) }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedEmployee,
                onValueChange = {},
                label = { Text("Сотрудник") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEmployeeMenuExpanded) }
            )

            ExposedDropdownMenu(
                expanded = isEmployeeMenuExpanded,
                onDismissRequest = { onEmployeeMenuExpandedChange(false) }
            ) {
                employees.forEach { employee ->
                    DropdownMenuItem(
                        text = { Text(text = employee) },
                        onClick = {
                            onEmployeeSelected(employee)
                            onEmployeeMenuExpandedChange(false)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = passwordValue,
            onValueChange = { onPasswordChange(it) },
            label = { Text("Пароль") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {}) {
            Text("Войти")
        }
    }
}