package com.example.p2_apli_huertohogar.model

data class Pedido(
    val id: Long? = null,
    val emailCliente: String,
    val fecha: String? = null,
    val idUsuario: Long,
    val detalles: List<PedidoDetalle>
)
