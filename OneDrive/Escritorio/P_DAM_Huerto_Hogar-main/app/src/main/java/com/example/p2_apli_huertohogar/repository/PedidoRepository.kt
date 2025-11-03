package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.Pedido
import com.example.p2_apli_huertohogar.model.PedidoDetalle
import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface PedidoRepository {
    fun observeAll(): Flow<List<Pedido>>
}


class InMemoryPedidoRepository : PedidoRepository {

    private val _pedidos = MutableStateFlow(
        listOf(
            Pedido(
                id = 1,
                fecha = "2025-11-01",
                usuario = Usuario(1, "Dominique", "domi@example.com", "1234", "Casa Central"),
                detalles = listOf(
                    PedidoDetalle(
                        producto = Producto(1, "Lechuga", "Verdura", "Lechuga fresca", 1000.0, ""),
                        cantidad = 2,
                        precioUnitario = 1000.0
                    ),
                    PedidoDetalle(
                        producto = Producto(2, "Zanahoria", "Verdura", "Zanahoria orgánica", 800.0, ""),
                        cantidad = 1,
                        precioUnitario = 800.0
                    )
                ),
                total = 2800.0
            ),
            Pedido(
                id = 2,
                fecha = "2025-11-02",
                usuario = Usuario(2, "María López", "maria@example.com", "abcd", "Villa Los Jardines 105"),
                detalles = listOf(
                    PedidoDetalle(
                        producto = Producto(3, "Tomate", "Verdura", "Tomate jugoso", 900.0, ""),
                        cantidad = 3,
                        precioUnitario = 900.0
                    )
                ),
                total = 2700.0
            ),
            Pedido(
                id = 3,
                fecha = "2025-11-03",
                usuario = Usuario(3, "Juan Pérez", "juan@example.com", "qwerty", "Pasaje Las Rosas 45"),
                detalles = listOf(
                    PedidoDetalle(
                        producto = Producto(4, "Frutillas", "Fruta", "Frutillas dulces", 2000.0, ""),
                        cantidad = 2,
                        precioUnitario = 2000.0
                    )
                ),
                total = 4000.0
            )
        )
    )

    override fun observeAll(): Flow<List<Pedido>> = _pedidos.asStateFlow()
}