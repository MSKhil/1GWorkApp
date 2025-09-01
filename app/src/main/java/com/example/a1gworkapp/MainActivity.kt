package com.example.a1gworkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1gworkapp.ui.login.LoginScreen
import com.example.a1gworkapp.ui.login.LoginViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: LoginViewModel = viewModel()

            val selectedCity = viewModel.selectedCity
            val selectedShop = viewModel.selectedShop
            val selectedEmployee = viewModel.selectedEmployee
            val password = viewModel.password
            val cities = viewModel.cities
            val shops = viewModel.shops
            val employees = viewModel.employees
            val isCityMenuExpanded = viewModel.isCityMenuExpanded
            val isShopMenuExpanded = viewModel.isShopMenuExpanded
            val isEmployeeMenuExpanded = viewModel.isEmployeeMenuExpanded

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
                onLoginClick = {
                }
            )
        }
    }
}