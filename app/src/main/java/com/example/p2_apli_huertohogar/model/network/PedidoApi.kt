package com.example.p2_apli_huertohogar.model.network

import com.example.p2_apli_huertohogar.model.CrearPedidoDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PedidoApi {

    @POST("api/pedidos/confirmar")
    suspend fun confirmarPedido(
        @Body dto: CrearPedidoDTO
    ): Response<ResponseBody>
}
