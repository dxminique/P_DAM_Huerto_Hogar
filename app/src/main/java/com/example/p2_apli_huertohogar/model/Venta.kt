package com.example.p2_apli_huertohogar.model

data class Venta(
    val id: Long,
    val cliente: String,
    val fechaVenta: String,
    val total: Double,
    val pedidoId: Long,
    val activo: Boolean
)
