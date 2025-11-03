package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Define el Estado de la UI
data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    val errorMensaje: String? = null // <-- Estado para tu mensaje de error
)

// 2. Define los Eventos de Navegación (para desacoplar)
sealed class LoginNavEvent {
    data class NavigateToHome(val route: String) : LoginNavEvent()
}

class LoginViewModel : ViewModel() {

    // 3. Expone el estado a la UI
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // 4. Expone los eventos de navegación (para que la UI reaccione)
    private val _navEvent = MutableSharedFlow<LoginNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    // 5. Funciones que la UI llamará
    fun onCorreoChange(correo: String) {
        _uiState.update { it.copy(correo = correo, errorMensaje = null) }
    }

    fun onContrasenaChange(contrasena: String) {
        _uiState.update { it.copy(contrasena = contrasena, errorMensaje = null) }
    }

    // 6. La lógica de validación
    fun onLoginClick() {
        val estadoActual = _uiState.value

        // Tu validación:
        if (estadoActual.correo.isBlank() || estadoActual.contrasena.isBlank()) {
            _uiState.update {
                it.copy(errorMensaje = "Debes rellenar todos los campos")
            }
            return // Detiene la ejecución
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estadoActual.correo).matches()) {
            _uiState.update {
                it.copy(errorMensaje = "El formato del correo no es válido")
            }
            return
        }


        viewModelScope.launch {
            _navEvent.emit(LoginNavEvent.NavigateToHome("home"))
        }
    }
}