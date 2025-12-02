package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel

@Composable
fun CarritoScreen(
    navController: NavHostController,
    pedidoViewModel: PedidoViewModel
) {
    val uiState = pedidoViewModel.uiState
    val carrito = pedidoViewModel.carrito

    val totalArticulos = carrito.sumOf { it.cantidad }
    val totalPrecio = carrito.fold(0.0) { acc, item ->
        acc + (item.producto.precio.toDouble() * item.cantidad)
    }

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
                text = "Tu carrito",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1B5E20),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            if (carrito.isEmpty()) {
                Text(
                    text = "Carrito vacío",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF757575),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(carrito) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE8F5E9)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = item.producto.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF1B5E20)
                                )
                                Text(
                                    text = "Cantidad: ${item.cantidad}",
                                    color = Color(0xFF2E7D32)
                                )
                                Text(
                                    text = "Precio unitario: ${item.producto.precio}",
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFC8E6C9)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Resumen",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF1B5E20)
                        )
                        Text(
                            text = "Artículos: $totalArticulos",
                            color = Color(0xFF2E7D32)
                        )
                        Text(
                            text = "Total: $totalPrecio",
                            color = Color(0xFF2E7D32)
                        )
                    }
                }

                if (uiState.pedidoCreado != null) {
                    Text(
                        text = "Compra realizada correctamente",
                        color = Color(0xFF1B5E20),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (uiState.error != null) {
                    Text(
                        text = uiState.error ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        val email = "test@correo.cl"
                        val idUsuario = 1L
                        pedidoViewModel.confirmarPedido(email, idUsuario)
                    },
                    enabled = carrito.isNotEmpty() && !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    Text("Confirmar compra")
                }
            }
        }
    }
}
