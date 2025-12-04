package com.example.a1gworkapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1gworkapp.ui.login.LoginScreen
import com.example.a1gworkapp.ui.login.LoginViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a1gworkapp.data.AppDatabase
import com.example.a1gworkapp.data.DataRepository
import com.example.a1gworkapp.ui.home.HomeScreen
import com.example.a1gworkapp.ui.home.HomeViewModel
import com.example.a1gworkapp.ui.login.LoginState
import com.example.a1gworkapp.data.UserPreferencesRepository
import com.example.a1gworkapp.navigation.HomeRoute
import com.example.a1gworkapp.navigation.LoginRoute
import com.example.a1gworkapp.navigation.SplashRoute
import com.example.a1gworkapp.network.RetrofitClient
import com.example.a1gworkapp.ui.splash.SplashScreen
import com.example.a1gworkapp.ui.theme._1GWorkAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            _1GWorkAppTheme {

                val navController = rememberNavController()

                val loginViewModel: LoginViewModel = hiltViewModel()
                val homeViewModel: HomeViewModel = hiltViewModel()

                val loginState by loginViewModel.loginState.collectAsState()
                val loggedInUser by loginViewModel.loggedInUser.collectAsState()

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

                NavHost(
                    navController = navController,
                    startDestination = SplashRoute
                ) {
                    composable<SplashRoute> {
                        SplashScreen()

                        LaunchedEffect(loginState) {
                            when (loginState) {
                                LoginState.SUCCESS -> {
                                    navController.navigate(HomeRoute) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }

                                LoginState.IDLE, LoginState.FAILURE -> {
                                    if (loginState == LoginState.IDLE) {
                                    } else {
                                        navController.navigate(LoginRoute) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                }

                                else -> {}
                            }
                        }
                    }

                    composable<LoginRoute> {
                        val selectedCity by loginViewModel.selectedCity.collectAsState()
                        val selectedShop by loginViewModel.selectedShop.collectAsState()
                        val selectedEmployee by loginViewModel.selectedEmployee.collectAsState()
                        val password by loginViewModel.password.collectAsState()
                        val isLoading by loginViewModel.isLoading.collectAsState()

                        val isCityMenuExpanded by loginViewModel.isCityMenuExpanded.collectAsState()
                        val isShopMenuExpanded by loginViewModel.isShopMenuExpanded.collectAsState()
                        val isEmployeeMenuExpanded by loginViewModel.isEmployeeMenuExpanded.collectAsState()

                        LoginScreen(
                            onLoginClick = loginViewModel::login,
                            cities = loginViewModel.cities,
                            shops = loginViewModel.shops,
                            employees = loginViewModel.employees,

                            selectedCity = selectedCity,
                            selectedShop = loginViewModel.selectedShop.collectAsState().value,
                            selectedEmployee = loginViewModel.selectedEmployee.collectAsState().value,
                            passwordValue = loginViewModel.password.collectAsState().value,
                            isLoading = loginViewModel.isLoading.collectAsState().value,

                            isCityMenuExpanded = loginViewModel.isCityMenuExpanded.collectAsState().value,
                            isShopMenuExpanded = loginViewModel.isShopMenuExpanded.collectAsState().value,
                            isEmployeeMenuExpanded = loginViewModel.isEmployeeMenuExpanded.collectAsState().value,

                            onCityMenuExpandedChange = loginViewModel::onCityMenuExpandedChange,
                            onShopMenuExpandedChange = loginViewModel::onShopMenuExpandedChange,
                            onEmployeeMenuExpandedChange = loginViewModel::onEmployeeMenuExpandedChange,
                            onCitySelected = loginViewModel::onCitySelected,
                            onShopSelected = loginViewModel::onShopSelected,
                            onEmployeeSelected = loginViewModel::onEmployeeSelected,
                            onPasswordChange = loginViewModel::onPasswordChange
                        )
                        LaunchedEffect(loginState) {
                            if (loginState == LoginState.SUCCESS) {
                                navController.navigate(HomeRoute) {
                                    popUpTo(0)
                                }
                            }
                        }
                    }
                    composable<HomeRoute> {
                        HomeScreen(
                            homeViewModel = homeViewModel,
                            onLogoutClick = {
                                loginViewModel.logout()
                                navController.navigate(LoginRoute) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
