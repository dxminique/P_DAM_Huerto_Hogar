package com.example.p2_apli_huertohogar.model

// Un item de pedido (cada producto del carrito)
data class ItemPedidoDTO(
    val idProducto: Long,
    val cantidad: Int
)


data class CrearPedidoDTO(
    val emailCliente: String,
    val items: List<ItemPedidoDTO>
)
