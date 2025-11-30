package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.repository.AuthRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val token: String? = null,
    val error: String? = null
)

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun login(email: String, password: String) {
        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                uiState = uiState.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    token = response.token
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "Error al iniciar sesión"
                )
            }
        }
    }
}
