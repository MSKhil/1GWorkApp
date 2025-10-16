package com.example.a1gworkapp.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1gworkapp.network.RetrofitClient
import com.example.a1gworkapp.network.UserDto
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

class LoginViewModel: ViewModel() {

    private val apiService = RetrofitClient.apiService

    private val _allUsers = MutableStateFlow<List<UserDto>>(emptyList())

    private val _selectedCity = MutableStateFlow("")
    private val _selectedShop = MutableStateFlow("")
    private val _selectedEmployee = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _isCityMenuExpanded = MutableStateFlow(false)
    private val _isShopMenuExpanded = MutableStateFlow(false)
    private val _isEmployeeMenuExpanded = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val _loginState = MutableStateFlow(LoginState.IDLE)
    private val _errorMessage = MutableStateFlow<String?>(null)

    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()
    var selectedShop: StateFlow<String> = _selectedShop
    val selectedEmployee: StateFlow<String> = _selectedEmployee
    val password: StateFlow<String> = _password
    val isCityMenuExpanded: StateFlow<Boolean> = _isCityMenuExpanded.asStateFlow()
    val isShopMenuExpanded: StateFlow<Boolean> = _isShopMenuExpanded.asStateFlow()
    val isEmployeeMenuExpanded: StateFlow<Boolean> = _isEmployeeMenuExpanded.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val cities: List<String>
        get() = _allUsers.value.map { it.city }.distinct().filter { it.isNotBlank() }

    val shops: List<String>
        get() = if (selectedCity.value.isNotBlank()) {
            _allUsers.value.filter { it.city == selectedCity.value }.map { it.shop }.distinct().filter { it.isNotBlank() }
        } else {
            emptyList()
        }

    val employees: List<String>
        get() = if (selectedShop.value.isNotBlank()) {
            _allUsers.value.filter { it.shop == selectedShop.value }.map { it.employeeName }.distinct().filter { it.isNotBlank() }
        } else {
            emptyList()
        }

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            Log.d("LoginViewModel", "Начинаю загрузку пользователей через Retrofit...")

            try {
                val users = apiService.getUsers()

                Log.d("LoginViewModel", "Успешно загружено пользователей: ${users.size}")

                _allUsers.value = users
            }
            catch (e: Exception) {
                Log.e("LoginViewModel", "Ошибка при загрузке пользователей", e)
                _errorMessage.value = "Не удалось загрузить данные. Проверьте интернет."
                // В будущем мы покажем это сообщение пользователю
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onCitySelected(city: String) {
        resetLoginState()
        _selectedCity.value = city
        _selectedShop.value = ""
        _selectedEmployee.value = ""
    }

    fun onShopSelected (shop: String) {
        resetLoginState()
        _selectedShop.value = shop
        _selectedEmployee.value = ""
    }

    fun onEmployeeSelected(employee: String) {
        resetLoginState()
        _selectedEmployee.value = employee
    }

    fun onPasswordChange(newPassword: String) {
        resetLoginState()
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

    fun resetLoginState() {
        if (_loginState.value != LoginState.IDLE) {
            _loginState.value = LoginState.IDLE
        }
    }

    fun login(){
        if (selectedCity.value.isBlank() || selectedShop.value.isBlank() || selectedEmployee.value.isBlank()) {
            return
        }

        val currentUser = _allUsers.value.find { user ->
            user.city == selectedCity.value &&
                    user.shop == selectedShop.value &&
                    user.employeeName == selectedEmployee.value
        }

        if (currentUser != null && currentUser.password == password.value) {
            Log.d("LoginViewModel", "Успешный вход для: ${currentUser.employeeName}")
            // TODO: Сохранить ID таблиц (salarySheetId, scheduleSheetId) для дальнейшей работы
            _loginState.value = LoginState.SUCCESS
        } else {
            Log.d("LoginViewModel", "Неверный пароль или пользователь не найден")
            _loginState.value = LoginState.FAILURE
        }
    }
}