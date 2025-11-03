package com.example.p2_apli_huertohogar.model

class Pedido (
    val id: Int,
    val fecha: String,
    val usuario: Usuario,
    val detalles: List<PedidoDetalle>,
    val total: Double
){}