package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel
import com.example.p2_apli_huertohogar.viewModel.ProductoViewModel

@Composable
fun ProductoScreen(
    navController: NavHostController,
    pedidoViewModel: PedidoViewModel,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val state = productoViewModel.uiState

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEFFBF3)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Productos",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF1B5E20),
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .align(Alignment.CenterHorizontally)
            )

            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.productos) { p ->

                            val disponible = p.stock > 0
                            val estadoColor = if (disponible) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                            val bgEstadoColor = if (disponible)
                                Color(0xFF81C784).copy(alpha = 0.25f)
                            else
                                Color(0xFFFFCDD2).copy(alpha = 0.25f)

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE8F5E9)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {

                                    Text(
                                        text = p.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color(0xFF1B5E20)
                                    )

                                    Text(
                                        text = p.descripcion,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(vertical = 6.dp)
                                    )

                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = bgEstadoColor),
                                        shape = RoundedCornerShape(50.dp),
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = if (disponible) "Disponible" else "Agotado",
                                            color = estadoColor,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 6.dp
                                            )
                                        )
                                    }

                                    Text(
                                        text = "Precio: ${p.precio}",
                                        color = Color(0xFF2E7D32)
                                    )
                                    Text(
                                        text = "Stock: ${p.stock}",
                                        color = Color(0xFF2E7D32)
                                    )

                                    Button(
                                        onClick = { pedidoViewModel.agregarAlCarrito(p) },
                                        enabled = disponible,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 10.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (disponible) Color(0xFF4CAF50) else Color(
                                                0xFFBDBDBD
                                            ),
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text(
                                            if (disponible) "Agregar al carrito" else "Sin stock"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
