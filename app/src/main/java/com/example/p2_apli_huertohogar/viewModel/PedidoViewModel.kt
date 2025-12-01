package com.example.p2_apli_huertohogar.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.Pedido
import com.example.p2_apli_huertohogar.model.PedidoDetalle
import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.repository.PedidoRepository
import kotlinx.coroutines.launch

data class PedidoUiState(
    val isLoading: Boolean = false,
    val pedidoCreado: Pedido? = null,
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
            val item = carrito[index]
            carrito[index] = item.copy(cantidad = item.cantidad + 1)
        } else {
            carrito.add(CarritoItem(producto = producto, cantidad = 1))
        }
        uiState = uiState.copy(
            pedidoCreado = null,
            error = null
        )
    }

    fun confirmarPedido(emailCliente: String, idUsuario: Long) {
        if (carrito.isEmpty()) return

        val detalles = carrito.map {
            PedidoDetalle(
                id = null,
                idProducto = it.producto.id,
                cantidad = it.cantidad
            )
        }

        val pedido = Pedido(
            id = null,
            emailCliente = emailCliente,
            fecha = null,
            idUsuario = idUsuario,
            detalles = detalles
        )

        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val creado = repository.crearPedido(pedido)
                uiState = uiState.copy(
                    isLoading = false,
                    pedidoCreado = creado
                )
                vaciarCarrito()
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun vaciarCarrito() {
        carrito.clear()
    }
}
