package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.p2_apli_huertohogar.model.DetallePedido
import com.example.p2_apli_huertohogar.model.Pedido
import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.repository.PedidoRepository
import kotlinx.coroutines.launch

data class PedidoUiState(
    val isLoading: Boolean = false,
    val pedidoCreado: Pedido? = null,
    val error: String? = null
)

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int
)

class PedidoViewModel(
    private val repository: PedidoRepository = PedidoRepository()
) : ViewModel() {

    var uiState by mutableStateOf(PedidoUiState())
        private set

    var carrito by mutableStateOf<List<ItemCarrito>>(emptyList())
        private set

    fun agregarAlCarrito(producto: Producto) {
        val existente = carrito.find { it.producto.id == producto.id }
        carrito = if (existente != null) {
            carrito.map {
                if (it.producto.id == producto.id)
                    it.copy(cantidad = it.cantidad + 1)
                else it
            }
        } else {
            carrito + ItemCarrito(producto, 1)
        }
    }

    fun quitarDelCarrito(productoId: Long) {
        val existente = carrito.find { it.producto.id == productoId } ?: return
        carrito = if (existente.cantidad > 1) {
            carrito.map {
                if (it.producto.id == productoId)
                    it.copy(cantidad = it.cantidad - 1)
                else it
            }
        } else {
            carrito.filterNot { it.producto.id == productoId }
        }
    }

    fun vaciarCarrito() {
        carrito = emptyList()
    }

    fun confirmarPedido(emailCliente: String, idUsuario: Long) {
        if (carrito.isEmpty()) return

        val detalles = carrito.map {
            DetallePedido(
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

        uiState = uiState.copy(isLoading = true, error = null, pedidoCreado = null)

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
}
