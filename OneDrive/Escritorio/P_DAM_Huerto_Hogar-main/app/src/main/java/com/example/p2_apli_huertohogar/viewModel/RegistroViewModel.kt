package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val errorMensaje: String? = null // Estado para el mensaje de error
)


sealed class RegistroNavEvent {
    data class NavigateToLogin(val route: String) : RegistroNavEvent()
}

class RegistroViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState = _uiState.asStateFlow()


    private val _navEvent = MutableSharedFlow<RegistroNavEvent>()
    val navEvent = _navEvent.asSharedFlow()


    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, errorMensaje = null) }
    }

    fun onCorreoChange(correo: String) {
        _uiState.update { it.copy(correo = correo, errorMensaje = null) }
    }

    fun onContrasenaChange(contrasena: String) {
        _uiState.update { it.copy(contrasena = contrasena, errorMensaje = null) }
    }


    fun onRegistroClick() {
        val estado = _uiState.value


        if (estado.nombre.isBlank() || estado.correo.isBlank() || estado.contrasena.isBlank()) {
            _uiState.update {
                it.copy(errorMensaje = "Debes rellenar todos los campos")
            }
            return
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estado.correo).matches()) {
            _uiState.update {
                it.copy(errorMensaje = "El formato del correo no es válido")
            }
            return
        }


        if (estado.contrasena.length < 6) {
            _uiState.update {
                it.copy(errorMensaje = "La contraseña debe tener al menos 6 caracteres")
            }
            return
        }


        viewModelScope.launch {
            _navEvent.emit(RegistroNavEvent.NavigateToLogin("login"))
        }
    }
}