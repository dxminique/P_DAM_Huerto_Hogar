package com.example.p2_apli_huertohogar.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.LoginRequest
import com.example.p2_apli_huertohogar.model.RegisterRequest
import com.example.p2_apli_huertohogar.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isRegistered: Boolean = false,
    val error: String? = null,
    val emailUsuario: String? = null,
    val idUsuario: Long? = null
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
                val req = LoginRequest(email = email, password = password)
                repository.login(req)

                uiState = uiState.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    emailUsuario = email
                )

            } catch (e: HttpException) {
                val msg = when (e.code()) {
                    400, 401 -> "Correo o contraseña incorrectos"
                    404 -> "Usuario no encontrado"
                    500 -> "Error interno del servidor"
                    else -> "Error desconocido (${e.code()})"
                }

                uiState = uiState.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    error = msg
                )

            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    error = "Error de conexión"
                )
            }
        }
    }

    fun registrar(nombre: String, email: String, password: String) {
        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val req = RegisterRequest(
                    nombre = nombre,
                    email = email,
                    password = password
                )

                repository.registrar(req)

                uiState = uiState.copy(
                    isLoading = false,
                    isRegistered = true,
                    emailUsuario = email
                )

            } catch (e: HttpException) {
                if (e.code() == 500) {
                    uiState = uiState.copy(
                        isLoading = false,
                        isRegistered = true,
                        emailUsuario = email,
                        error = null
                    )
                } else {
                    val msg = when (e.code()) {
                        400 -> "Datos de registro inválidos"
                        409 -> "El correo ya está registrado"
                        else -> "Error en el registro (${e.code()})"
                    }

                    uiState = uiState.copy(
                        isLoading = false,
                        isRegistered = false,
                        error = msg
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    isRegistered = false,
                    error = "Error de conexión"
                )
            }
        }
    }

    fun clearFlags() {
        uiState = uiState.copy(
            isLoggedIn = false,
            isRegistered = false,
            error = null,
            emailUsuario = null,
            idUsuario = null
        )
    }
}
