package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


data class Producto(
    val nombre: String,
    val tipo: String,
    val precio: Double
)
@Composable
fun ProductoScreen(navController: NavController){
    val productos = listOf(
        Producto("Tomate", "Verdura", 1200.0),
        Producto("Lechuga", "Verdura", 800.0),
        Producto("Miel", "Dulce", 3500.0)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lista de productos", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Nombre: ${producto.nombre}", style = MaterialTheme.typography.titleMedium)
                        Text("Tipo: ${producto.tipo}")
                        Text("Precio: $${producto.precio}")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }

}