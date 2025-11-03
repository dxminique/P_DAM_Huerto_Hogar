package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class ProductoUiState(
    val productos: List<Producto> = emptyList()
)

class ProductoViewModel(
    private val repo: ProductoRepository
) : ViewModel() {

    val uiState: StateFlow<ProductoUiState> =
        repo.observeAll()
            .map { lista -> ProductoUiState(productos = lista) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                ProductoUiState()
            )
}