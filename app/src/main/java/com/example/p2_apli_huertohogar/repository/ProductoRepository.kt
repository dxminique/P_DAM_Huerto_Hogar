package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.model.network.ApiClient
import com.example.p2_apli_huertohogar.model.network.ProductoApi

class ProductoRepository {

    private val api = ApiClient.retrofit.create(ProductoApi::class.java)

    suspend fun getProductos(): List<Producto> {
        return api.getProductos()
    }

    suspend fun getProductoById(id: Long): Producto {
        return api.getProductoById(id)
    }
}
