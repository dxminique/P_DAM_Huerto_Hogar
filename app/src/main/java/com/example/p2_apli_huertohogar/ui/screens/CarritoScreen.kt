package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel

@Composable
fun CarritoScreen(
    navController: NavHostController,
    pedidoViewModel: PedidoViewModel = viewModel()
) {
    val state = pedidoViewModel.uiState
    val carrito = pedidoViewModel.carrito

    val total = carrito.sumOf { it.producto.precio.toDouble() * it.cantidad }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (carrito.isEmpty()) {
            Text("Carrito vacío")
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
                            .padding(vertical = 4.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(item.producto.nombre)
                            Text("Cantidad: ${item.cantidad}")
                            Text("Precio: ${item.producto.precio}")
                            Text("Subtotal: ${item.producto.precio.toDouble() * item.cantidad}")
                            Row {
                                TextButton(
                                    onClick = { pedidoViewModel.quitarDelCarrito(item.producto.id) }
                                ) {
                                    Text("Quitar")
                                }
                            }
                        }
                    }
                }
            }

            Text(
                text = "Total: $total",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (state.error != null) {
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    pedidoViewModel.confirmarPedido(
                        emailCliente = "correo@usuario.cl",
                        idUsuario = 1L
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled = !state.isLoading && carrito.isNotEmpty()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Confirmar compra")
                }
            }

            if (state.pedidoCreado != null) {
                Text(
                    text = "Pedido creado: #${state.pedidoCreado.id}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
