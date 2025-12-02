package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.CrearPedidoDTO
import com.example.p2_apli_huertohogar.model.network.ApiClient
import com.example.p2_apli_huertohogar.model.network.PedidoApi
import okhttp3.ResponseBody

class PedidoRepository {

    private val api = ApiClient.retrofit.create(PedidoApi::class.java)

    suspend fun confirmarPedido(dto: CrearPedidoDTO): ResponseBody? {
        val response = api.confirmarPedido(dto)

        if (!response.isSuccessful) {
            throw Exception("Error al confirmar pedido: ${response.code()}")
        }

        return response.body()
    }
}
