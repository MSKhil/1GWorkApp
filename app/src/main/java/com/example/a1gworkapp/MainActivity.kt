package com.example.a1gworkapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.a1gworkapp.ui.home.HomeScreen
import com.example.a1gworkapp.ui.login.LoginScreen
import androidx.compose.runtime.remember


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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

            var selectedCity by remember { mutableStateOf("") }
            var selectedShop by remember { mutableStateOf("") }
            var selectedEmployee by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            val cities = remember { allUsers.map { it.city }.distinct() }
            Log.d("MainActivity", "Список городов: $cities")
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
            var isCityMenuExpanded by remember { mutableStateOf(false) }
            var isShopMenuExpanded by remember { mutableStateOf(false) }
            var isEmployeeMenuExpanded by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = selectedCity) {
                selectedShop = ""
                selectedEmployee = ""
            }
            LaunchedEffect(key1 = selectedShop) {
                selectedEmployee = ""
            }
            LoginScreen(
                cities = cities,
                shops = shops,
                employees = employees,
                selectedCity = selectedCity,
                selectedShop = selectedShop,
                selectedEmployee = selectedEmployee,
                passwordValue = password,
                isCityMenuExpanded = isCityMenuExpanded,
                isShopMenuExpanded = isShopMenuExpanded,
                isEmployeeMenuExpanded = isEmployeeMenuExpanded,
                onCityMenuExpandedChange = { isCityMenuExpanded = it },
                onShopMenuExpandedChange = { isShopMenuExpanded = it },
                onEmployeeMenuExpandedChange = { isEmployeeMenuExpanded = it },
                onCitySelected = { city -> selectedCity = city },
                onShopSelected = { shop -> selectedShop = shop },
                onEmployeeSelected = { employee -> selectedEmployee = employee },
                onPasswordChange = { newPassword -> password = newPassword },
                onLoginClick = {

                }
            )
        }
    }
}