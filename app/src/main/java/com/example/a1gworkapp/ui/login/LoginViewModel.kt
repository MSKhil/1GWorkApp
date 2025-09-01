package com.example.a1gworkapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
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

    var selectedCity by mutableStateOf("")
    var selectedShop by mutableStateOf("")
    var selectedEmployee by mutableStateOf("")
    var password by mutableStateOf("")
    val cities = allUsers.map { it.city }.distinct()
    val shops: List<String>
        get() = if (selectedCity.isNotBlank()) {
                allUsers
                    .filter { it.city == selectedCity }
                    .map { it.shop }
                    .distinct()
        } else {
                emptyList()
            }
    val employees: List<String>
        get() = if (selectedShop.isNotBlank()) {
                allUsers
                    .filter { it.shop == selectedShop }
                    .map { it.employee }
                    .distinct()
            } else{
                emptyList()
            }

    var isCityMenuExpanded by mutableStateOf(false)
    var isShopMenuExpanded by mutableStateOf(false)
    var isEmployeeMenuExpanded by mutableStateOf(false)

    fun onCitySelected(city: String) {
        selectedCity = city
        selectedShop = ""
        selectedEmployee = ""
    }

    fun onShopSelected (shop: String) {
        selectedShop = shop
        selectedEmployee = ""
    }

    fun onEmployeeSelected(employee: String) {
        selectedEmployee = employee
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onCityMenuExpandedChange(isExpanded: Boolean) {
        isCityMenuExpanded = isExpanded
    }

    fun onShopMenuExpandedChange(isExpanded: Boolean) {
        isShopMenuExpanded = isExpanded
    }

    fun onEmployeeMenuExpandedChange(isExpanded: Boolean) {
        isEmployeeMenuExpanded = isExpanded
    }
}