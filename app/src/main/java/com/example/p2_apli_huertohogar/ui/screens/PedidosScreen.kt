package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Pedido(
    val id: Int,
    val fecha: String,
    val usuario: String,
    val total: Double
)

@Composable
fun PedidosScreen(navController: NavController) {
    val pedidos = listOf(
        Pedido(1, "2025-10-28", "Juan Pérez", 5600.0),
        Pedido(2, "2025-10-29", "Juan Pérez", 8200.0),
        Pedido(3, "2025-10-30", "Juan Pérez", 3900.0)
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF1F8E9)
    )
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Lista de pedidos", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(pedidos) { pedido ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "Pedido #${pedido.id}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("Cliente: ${pedido.usuario}")
                            Text("Fecha: ${pedido.fecha}")
                            Text("Total: $${pedido.total}")
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
}