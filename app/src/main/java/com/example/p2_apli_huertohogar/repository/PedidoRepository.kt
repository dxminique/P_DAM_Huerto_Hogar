package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.Pedido
import com.example.p2_apli_huertohogar.model.network.ApiClient
import com.example.p2_apli_huertohogar.model.network.PedidoApi

class PedidoRepository {

    private val api = ApiClient.retrofit.create(PedidoApi::class.java)

    suspend fun crearPedido(pedido: Pedido): Pedido {
        return api.crearPedido(pedido)
    }
}
