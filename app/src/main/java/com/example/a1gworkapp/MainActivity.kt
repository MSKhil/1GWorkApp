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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.a1gworkapp.data.AppDatabase
import com.example.a1gworkapp.data.DataRepository
import com.example.a1gworkapp.ui.home.HomeScreen
import com.example.a1gworkapp.ui.home.HomeViewModel
import com.example.a1gworkapp.ui.login.LoginState
import com.example.a1gworkapp.data.UserPreferencesRepository
import com.example.a1gworkapp.network.RetrofitClient
import com.example.a1gworkapp.ui.home.HomeViewModelFactory
import com.example.a1gworkapp.ui.login.LoginViewModelFactory
import com.example.a1gworkapp.ui.splash.SplashScreen
import com.example.a1gworkapp.ui.theme._1GWorkAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            _1GWorkAppTheme {
                val userPreferencesRepository = UserPreferencesRepository(applicationContext)
                val appDao = AppDatabase.getDatabase(applicationContext).appDao()
                val apiService = RetrofitClient.apiService
                val dataRepository = DataRepository(apiService, appDao)

                val loginViewModel: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory(userPreferencesRepository)
                )
                val homeViewModel: HomeViewModel = viewModel(
                    factory = HomeViewModelFactory(dataRepository)
                )

                val loginState by loginViewModel.loginState.collectAsState()
                val loggedInUser by loginViewModel.loggedInUser.collectAsState()
                val selectedCity by loginViewModel.selectedCity.collectAsState()
                val selectedShop by loginViewModel.selectedShop.collectAsState()
                val selectedEmployee by loginViewModel.selectedEmployee.collectAsState()
                val password by loginViewModel.password.collectAsState()
                val isLoading by loginViewModel.isLoading.collectAsState()

                val isCityMenuExpanded by loginViewModel.isCityMenuExpanded.collectAsState()
                val isShopMenuExpanded by loginViewModel.isShopMenuExpanded.collectAsState()
                val isEmployeeMenuExpanded by loginViewModel.isEmployeeMenuExpanded.collectAsState()

                LaunchedEffect(loggedInUser) {
                    loggedInUser?.let { user ->
                        if (user.salarySheetId.isNotBlank() && user.scheduleSheetId.isNotBlank()) {
                            homeViewModel.start(
                                salarySheetId = user.salarySheetId,
                                scheduleSheetId = user.scheduleSheetId,
                                employeeName = user.employeeName,
                            )
                        }
                    }
                }

                when (loginState) {
                    LoginState.LOADING_AUTO -> {
                        SplashScreen()
                    }

                    LoginState.SUCCESS -> {
                        HomeScreen(
                            homeViewModel = homeViewModel,
                            onLogoutClick = { loginViewModel.logout() }
                        )
                    }

                    else -> {
                        val cities = loginViewModel.cities
                        val shops = loginViewModel.shops
                        val employees = loginViewModel.employees

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
    }
}
