package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.p2_apli_huertohogar.viewModel.AuthViewModel
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel
import com.example.p2_apli_huertohogar.viewModel.ProductoViewModel

@Composable
fun CarritoScreen(
    navController: NavHostController,
    pedidoViewModel: PedidoViewModel,
    authViewModel: AuthViewModel = viewModel(),
    productoViewModel: ProductoViewModel = viewModel()
) {
    val uiState = pedidoViewModel.uiState
    val carrito = pedidoViewModel.carrito
    val authState = authViewModel.uiState
    val emailUsuario = authState.emailUsuario ?: ""

    val totalArticulos = carrito.sumOf { it.cantidad }
    val totalPrecio = carrito.fold(0.0) { acc, item ->
        acc + (item.producto.precio.toDouble() * item.cantidad)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            productoViewModel.cargarProductos()
            snackbarHostState.showSnackbar("Compra realizada con éxito")
            pedidoViewModel.consumirSuccess()
        }
    }

    LaunchedEffect(uiState.error) {
        val error = uiState.error
        if (error != null) {
            snackbarHostState.showSnackbar("Error: $error")
            pedidoViewModel.consumirError()
        }
    }

    Scaffold(
        containerColor = Color(0xFFF1F8E9),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Tu carrito",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF2E7D32),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            if (carrito.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Carrito vacío")
                }
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
                                .padding(vertical = 6.dp),
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
                                    color = Color(0xFF2E7D32)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Cantidad: ${item.cantidad}")
                                Text("Precio unitario: ${item.producto.precio}")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFDFF0D8)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Resumen",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Artículos: $totalArticulos")
                        Text("Total: $totalPrecio")
                    }
                }

                Button(
                    onClick = {
                        if (emailUsuario.isNotBlank()) {
                            pedidoViewModel.confirmarPedido(emailUsuario)
                        } else {
                            pedidoViewModel.setError("Debes iniciar sesión para confirmar la compra")
                        }
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
