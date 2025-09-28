package com.example.a1gworkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1gworkapp.ui.login.LoginScreen
import com.example.a1gworkapp.ui.login.LoginViewModel
import androidx.compose.runtime.getValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: LoginViewModel = viewModel()

            val selectedCity by viewModel.selectedCity.collectAsState()
            val selectedShop by viewModel.selectedShop.collectAsState()
            val selectedEmployee by viewModel.selectedEmployee.collectAsState()
            val password by viewModel.password.collectAsState()

            val cities = viewModel.cities
            val shops = viewModel.shops
            val employees = viewModel.employees

            val isCityMenuExpanded by viewModel.isCityMenuExpanded.collectAsState()
            val isShopMenuExpanded by viewModel.isShopMenuExpanded.collectAsState()
            val isEmployeeMenuExpanded by viewModel.isEmployeeMenuExpanded.collectAsState()

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
                onCityMenuExpandedChange = viewModel::onCityMenuExpandedChange,
                onShopMenuExpandedChange = viewModel::onShopMenuExpandedChange,
                onEmployeeMenuExpandedChange = viewModel::onEmployeeMenuExpandedChange,
                onCitySelected = viewModel::onCitySelected,
                onShopSelected = viewModel::onShopSelected,
                onEmployeeSelected = viewModel::onEmployeeSelected,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = viewModel::login
            )
        }
    }
}