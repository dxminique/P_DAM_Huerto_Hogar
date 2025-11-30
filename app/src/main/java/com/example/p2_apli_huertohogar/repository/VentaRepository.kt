package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.Venta
import com.example.p2_apli_huertohogar.model.network.ApiClient
import com.example.p2_apli_huertohogar.model.network.VentaApi

class VentaRepository {

    private val api = ApiClient.retrofit.create(VentaApi::class.java)

    suspend fun getVentas(): List<Venta> {
        return api.getVentas()
    }

    suspend fun getVentasByUsuario(email: String): List<Venta> {
        return api.getVentasByUsuario(email)
    }

    suspend fun crearVenta(venta: Venta): Venta {
        return api.crearVenta(venta)
    }
}
