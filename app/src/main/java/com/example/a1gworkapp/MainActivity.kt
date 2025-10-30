package com.example.a1gworkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1gworkapp.ui.login.LoginScreen
import com.example.a1gworkapp.ui.login.LoginViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import com.example.a1gworkapp.ui.home.HomeScreen
import com.example.a1gworkapp.ui.home.HomeViewModel
import com.example.a1gworkapp.ui.login.LoginState
import com.example.a1gworkapp.data.UserPreferencesRepository
import com.example.a1gworkapp.ui.login.LoginViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userPreferencesRepository = UserPreferencesRepository(applicationContext)

            val loginViewModel:  LoginViewModel = viewModel(
                factory = LoginViewModelFactory(userPreferencesRepository)
            )
            val homeViewModel: HomeViewModel = viewModel()

            val loginState by loginViewModel.loginState.collectAsState()
            val loggedInUser by loginViewModel.loggedInUser.collectAsState()

            LaunchedEffect(loggedInUser) {
                loggedInUser?.let { user ->
                    homeViewModel.loadData(
                        salarySheetsId = user.salarySheetId,
                        scheduleSheetId = user.scheduleSheetId,
                        employeeName = user.employeeName
                    )
                }
            }

            if (loginState == LoginState.SUCCESS || loginState == LoginState.LOADING_AUTO) {
                HomeScreen(
                    homeViewModel = homeViewModel,
                    onLogoutClick = { loginViewModel.logout() }
                )
            } else {
                val selectedCity by loginViewModel.selectedCity.collectAsState()
                val selectedShop by loginViewModel.selectedShop.collectAsState()
                val selectedEmployee by loginViewModel.selectedEmployee.collectAsState()
                val password by loginViewModel.password.collectAsState()
                val isLoading by loginViewModel.isLoading.collectAsState()

                val cities = loginViewModel.cities
                val shops = loginViewModel.shops
                val employees = loginViewModel.employees

                val isCityMenuExpanded by loginViewModel.isCityMenuExpanded.collectAsState()
                val isShopMenuExpanded by loginViewModel.isShopMenuExpanded.collectAsState()
                val isEmployeeMenuExpanded by loginViewModel.isEmployeeMenuExpanded.collectAsState()

                LoginScreen(
                    cities = cities,
                    shops = shops,
                    employees = employees,
                    selectedCity = selectedCity,
                    selectedShop = selectedShop,
                    selectedEmployee = selectedEmployee,
                    passwordValue = password,
                    isLoading = isLoading,
                    isCityMenuExpanded = isCityMenuExpanded,
                    isShopMenuExpanded = isShopMenuExpanded,
                    isEmployeeMenuExpanded = isEmployeeMenuExpanded,
                    onCityMenuExpandedChange = loginViewModel::onCityMenuExpandedChange,
                    onShopMenuExpandedChange = loginViewModel::onShopMenuExpandedChange,
                    onEmployeeMenuExpandedChange = loginViewModel::onEmployeeMenuExpandedChange,
                    onCitySelected = loginViewModel::onCitySelected,
                    onShopSelected = loginViewModel::onShopSelected,
                    onEmployeeSelected = loginViewModel::onEmployeeSelected,
                    onPasswordChange = loginViewModel::onPasswordChange,
                    onLoginClick = loginViewModel::login
                )
            }
        }
    }
}