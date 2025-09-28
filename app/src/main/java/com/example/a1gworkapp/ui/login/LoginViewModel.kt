package com.example.a1gworkapp.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1gworkapp.repository.SheetsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class LoginState {
    IDLE,
    SUCCESS,
    FAILURE
}

data class UserData(
    val city: String,
    val shop: String,
    val employee: String,
    val password: String,
    val salarySpreadsheetId: String,
    val scheduleSpreadsheetId: String
)

class LoginViewModel: ViewModel() {

    private val repository = SheetsRepository()

    private val _allUsers = MutableStateFlow<List<UserData>>(emptyList())
    private val _selectedCity = MutableStateFlow("")
    private val _selectedShop = MutableStateFlow("")
    private val _selectedEmployee = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _isCityMenuExpanded = MutableStateFlow(false)
    private val _isShopMenuExpanded = MutableStateFlow(false)
    private val _isEmployeeMenuExpanded = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val _loginState = MutableStateFlow(LoginState.IDLE)


    val allUsers: StateFlow<List<UserData>> = _allUsers.asStateFlow()
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()
    var selectedShop: StateFlow<String> = _selectedShop
    val selectedEmployee: StateFlow<String> = _selectedEmployee
    val password: StateFlow<String> = _password
    val isCityMenuExpanded: StateFlow<Boolean> = _isCityMenuExpanded.asStateFlow()
    val isShopMenuExpanded: StateFlow<Boolean> = _isShopMenuExpanded.asStateFlow()
    val isEmployeeMenuExpanded: StateFlow<Boolean> = _isEmployeeMenuExpanded.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    val cities: List<String>
        get() = _allUsers.value.map { it.city }.distinct()

    val shops: List<String>
        get() = if (selectedCity.value.isNotBlank()){
            _allUsers.value.filter { it.city == _selectedCity.value }.map { it.shop }.distinct()
        } else {
                emptyList()
            }

    val employees: List<String>
        get() = if (selectedShop.value.isNotBlank()){
            _allUsers.value.filter { it.shop == _selectedShop.value }.map { it.employee }.distinct()
            } else{
                emptyList()
            }

    init {
        Log.d("LoginViewModel", "ViewModel создана. Запускаю loadUsers...")

        loadUsers()
    }

    fun loadUsers(){
        Log.d("LoginViewModel", "Функция loadUsers НАЧАЛАСЬ.")

        viewModelScope.launch {
            Log.d("LoginViewModel", "Корутина ЗАПУЩЕНА.")

            _isLoading.value = true
            try {
                val apiKey = "AIzaSyDAKa20T9wWRScf5_G7ECSvH20kNudf4m8"
                val spreadsheetId = "168IuYgMbU_c0HSirk9BsNyUrPm3aPDR5nFyy3hj15yU"
                val range = "Users!A:F"

                Log.d("LoginViewModel", "Начинаю сетевой запрос...")

                val rawData = repository.getSheetData(apiKey, spreadsheetId, range)
                if (rawData != null){
                    Log.d("LoginViewModel", "ТЕСТОВЫЕ ДАННЫЕ УСПЕШНО ПОЛУЧЕНЫ! Строк: ${rawData.size}")
                    Log.d("LoginViewModel", "Первая строка: ${rawData.firstOrNull()}")


                    val users = rawData
                        .drop(1)
                        .mapNotNull { row ->
                            if (row.size >= 6) {
                                UserData(
                                    city = row[0].toString(),
                                    shop = row[1].toString(),
                                    employee = row[2].toString(),
                                    password = row[3].toString(),
                                    salarySpreadsheetId = row[4].toString(),
                                    scheduleSpreadsheetId = row[5].toString()
                                )
                            } else {
                                null
                            }
                        }
                    _allUsers.value = users
                    Log.d("LoginViewModel", "Парсинг завершен. Пользователей в списке: ${_allUsers.value.size}")

                } else {
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "ОШИБКА при загрузке: ${e.message}", e)

            } finally {
                _isLoading.value = false
                Log.d("LoginViewModel", "Блок finally. Загрузка завершена.")

            }
        }
    }

    fun onCitySelected(city: String) {
        _loginState.value = LoginState.IDLE
        _selectedCity.value = city
        _selectedShop.value = ""
        _selectedEmployee.value = ""
    }

    fun onShopSelected (shop: String) {
        _loginState.value = LoginState.IDLE
        _selectedShop.value = shop
        _selectedEmployee.value = ""
    }

    fun onEmployeeSelected(employee: String) {
        _loginState.value = LoginState.IDLE
        _selectedEmployee.value = employee
    }

    fun onPasswordChange(newPassword: String) {
        _loginState.value = LoginState.IDLE
        _password.value = newPassword
    }

    fun onCityMenuExpandedChange(isExpanded: Boolean) {
        _isCityMenuExpanded.value = isExpanded
    }

    fun onShopMenuExpandedChange(isExpanded: Boolean) {
        _isShopMenuExpanded.value = isExpanded
    }

    fun onEmployeeMenuExpandedChange(isExpanded: Boolean) {
        _isEmployeeMenuExpanded.value = isExpanded
    }

    fun login(){
        if (_selectedCity.value.isBlank() || _selectedShop.value.isBlank() || _selectedEmployee.value.isBlank()) {
            _loginState.value = LoginState.FAILURE
            return
        }

        val currentUserData = _allUsers.value.find { userData -> userData.city == _selectedCity.value &&
                    userData.shop == _selectedShop.value &&
                    userData.employee == _selectedEmployee.value}

        if (currentUserData != null && currentUserData.password == _password.value) {
            _loginState.value = LoginState.SUCCESS
        }
    }
}