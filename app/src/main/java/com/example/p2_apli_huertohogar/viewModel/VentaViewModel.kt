package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.p2_apli_huertohogar.model.Venta
import com.example.p2_apli_huertohogar.repository.VentaRepository
import kotlinx.coroutines.launch

data class VentaUiState(
    val ventas: List<Venta> = emptyList(),
    val isLoading: Boolean = false,
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
                    ventas = lista,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun cargarVentasPorUsuario(email: String) {
        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val lista = repository.getVentasByUsuario(email)
                uiState = uiState.copy(
                    ventas = lista,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}
