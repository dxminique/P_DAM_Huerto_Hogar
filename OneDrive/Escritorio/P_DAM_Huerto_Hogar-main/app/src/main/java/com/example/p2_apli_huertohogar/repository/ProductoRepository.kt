package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


interface ProductoRepository {
    fun observeAll(): Flow<List<Producto>>
}

class InMemoryProductoRepository : ProductoRepository {

    private val _productos = MutableStateFlow(
        listOf(
            Producto(1, "Lechuga", "Verdura", "Lechuga fresca", 1000.0, ""),
            Producto(2, "Zanahoria", "Verdura", "Zanahoria orgánica", 800.0, ""),
            Producto(3, "Tomate", "Verdura", "Tomate jugoso", 900.0, ""),
            Producto(4, "Frutillas", "Fruta", "Frutillas dulces", 2000.0, ""),
            Producto(5, "Manzana", "Fruta", "Manzana verde", 1200.0, ""),
            Producto(6, "Cebolla", "Verdura", "Cebolla fresca", 700.0, ""),
            Producto(7, "Papa", "Verdura", "Papa de campo", 600.0, "")
        )
    )

    override fun observeAll(): Flow<List<Producto>> = _productos.asStateFlow()
}