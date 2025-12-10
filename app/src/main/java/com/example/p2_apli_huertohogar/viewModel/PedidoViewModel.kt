package com.example.p2_apli_huertohogar.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.CrearPedidoDTO
import com.example.p2_apli_huertohogar.model.ItemPedidoDTO
import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.repository.PedidoRepository
import kotlinx.coroutines.launch

data class PedidoUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

data class CarritoItem(
    val producto: Producto,
    val cantidad: Int
)

class PedidoViewModel(
    private val repository: PedidoRepository = PedidoRepository()
) : ViewModel() {

    var uiState by mutableStateOf(PedidoUiState())
        private set

    val carrito = mutableStateListOf<CarritoItem>()

    fun agregarAlCarrito(producto: Producto) {
        val index = carrito.indexOfFirst { it.producto.id == producto.id }
        if (index >= 0) {
            carrito[index] = carrito[index].copy(cantidad = carrito[index].cantidad + 1)
        } else {
            carrito.add(CarritoItem(producto = producto, cantidad = 1))
        }
    }

    fun setError(msg: String) {
        uiState = uiState.copy(
            error = msg,
            isLoading = false,
            success = false
        )
    }

    fun confirmarPedido(emailCliente: String) {
        if (carrito.isEmpty()) {
            setError("El carrito está vacío")
            return
        }

        val items = carrito.map {
            ItemPedidoDTO(
                idProducto = it.producto.id,
                cantidad = it.cantidad
            )
        }

        val dto = CrearPedidoDTO(
            emailCliente = emailCliente,
            items = items
        )

        uiState = uiState.copy(
            isLoading = true,
            error = null,
            success = false
        )

        viewModelScope.launch {
            try {
                repository.confirmarPedido(dto)

                uiState = uiState.copy(
                    isLoading = false,
                    success = true,
                    error = null
                )

                carrito.clear()

            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    success = false,
                    error = e.message ?: "Error al confirmar pedido"
                )
            }
        }
    }

    fun consumirSuccess() {
        uiState = uiState.copy(success = false)
    }

    fun consumirError() {
        uiState = uiState.copy(error = null)
    }
}
