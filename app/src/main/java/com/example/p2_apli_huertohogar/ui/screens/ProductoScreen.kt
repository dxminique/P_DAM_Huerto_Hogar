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
import com.example.p2_apli_huertohogar.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource




data class Producto(
    val nombre: String,
    val tipo: String,
    val precio: Double,
    val imagen: Int
)
@Composable
fun ProductoScreen(navController: NavController) {
    val productos = listOf(
        Producto("Tomate", "Verdura", 1200.0,R.drawable.tomate),
        Producto("Lechuga Scarola", "Verdura", 800.0,R.drawable.lechuga),
        Producto("Miel", "Dulce", 3500.0,R.drawable.miel),
        Producto("Repollo", "Verdura", 1000.0,R.drawable.repollo),
        Producto("Zanahoria", "Verdura", 1500.0, R.drawable.zanahoria),
        Producto("Manzana Verde", "Fruta", 2000.0, R.drawable.manzana),
        Producto("Pera", "Fruta", 2500.0, R.drawable.pera),
        Producto("Naranja", "Fruta", 2200.0, R.drawable.naranja),
        Producto("Platano", "Fruta", 1800.0, R.drawable.platano)
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF1F8E9))
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Lista de productos", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(productos) { producto ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE0F2F1)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {  Image(
                        painter = painterResource(id = producto.imagen),
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 12.dp)
                    )
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "Nombre: ${producto.nombre}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("Tipo: ${producto.tipo}")
                            Text("Precio: $${producto.precio}")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )) {
                Text("Volver")
            }
        }
    }
}