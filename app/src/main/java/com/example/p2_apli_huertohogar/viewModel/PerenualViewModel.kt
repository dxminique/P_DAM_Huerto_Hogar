package com.example.p2_apli_huertohogar.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.PerenualPlant
import com.example.p2_apli_huertohogar.repository.PerenualRepository
import kotlinx.coroutines.launch

data class PerenualUiState(
    val isLoading: Boolean = false,
    val plantas: List<PerenualPlant> = emptyList(),
    val error: String? = null
)

class PerenualViewModel(
    private val repository: PerenualRepository = PerenualRepository()
) : ViewModel() {

    var uiState by mutableStateOf(PerenualUiState())
        private set

    init {
        cargarPlantas()
    }

    fun cargarPlantas() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val lista = repository.obtenerPlantas()
                uiState = uiState.copy(isLoading = false, plantas = lista)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "No se pudieron cargar las plantas recomendadas"
                )
            }
        }
    }
}
