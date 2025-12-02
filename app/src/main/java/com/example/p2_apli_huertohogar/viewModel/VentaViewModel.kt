package com.example.p2_apli_huertohogar.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.Venta
import com.example.p2_apli_huertohogar.repository.VentaRepository
import kotlinx.coroutines.launch

data class VentaUiState(
    val isLoading: Boolean = false,
    val ventas: List<Venta> = emptyList(),
    val error: String? = null
)

class VentaViewModel(
    private val repository: VentaRepository = VentaRepository()
) : ViewModel() {

    var uiState by mutableStateOf(VentaUiState())
        private set


    fun cargarVentas() {
        uiState = uiState.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val lista = repository.getVentas()
                uiState = uiState.copy(
                    isLoading = false,
                    ventas = lista
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }


    fun cargarHistorial(email: String) {
        uiState = uiState.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {

                val lista = repository.getVentasByUsuario(email)
                uiState = uiState.copy(
                    isLoading = false,
                    ventas = lista
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

}
