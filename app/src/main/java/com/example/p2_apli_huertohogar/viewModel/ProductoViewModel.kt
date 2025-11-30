package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.repository.ProductoRepository
import kotlinx.coroutines.launch

data class ProductoUiState(
    val isLoading: Boolean = false,
    val productos: List<Producto> = emptyList(),
    val error: String? = null
)

class ProductoViewModel(
    private val repository: ProductoRepository = ProductoRepository()
): ViewModel() {

    var uiState by mutableStateOf(ProductoUiState())
        private set

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val lista = repository.getProductos()
                uiState = uiState.copy(
                    isLoading = false,
                    productos = lista
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
