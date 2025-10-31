package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.Pedido
import com.example.p2_apli_huertohogar.repository.PedidoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PedidoUiState(
    val pedidos: List<Pedido> = emptyList()
)

class PedidoViewModel(
    private val repo: PedidoRepository
) : ViewModel() {

    val uiState: StateFlow<PedidoUiState> =
        repo.observeAll()
            .mapgit status { list -> PedidoUiState(pedidos = list) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                PedidoUiState()
            )
}