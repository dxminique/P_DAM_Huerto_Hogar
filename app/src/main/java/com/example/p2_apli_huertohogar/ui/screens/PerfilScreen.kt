package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.p2_apli_huertohogar.viewModel.AuthViewModel
import com.example.p2_apli_huertohogar.viewModel.VentaViewModel

@Composable
fun PerfilScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    ventaViewModel: VentaViewModel = viewModel()
) {
    val authState = authViewModel.uiState
    val ventaState = ventaViewModel.uiState

    val email = authState.emailUsuario ?: ""
    val nombre = email.substringBefore("@").ifBlank { "Usuario HuertoHogar" }

    LaunchedEffect(key1 = email) {
        if (email.isNotBlank()) {
            ventaViewModel.cargarHistorial(email)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEFFBF3)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Perfil de usuario",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Nombre: $nombre", fontWeight = FontWeight.SemiBold)
                    Text("Correo: $email")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Historial de compras",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2E7D32),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                ventaState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF4CAF50))
                    }
                }

                ventaState.error != null -> {
                    Text(
                        text = ventaState.error ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                ventaState.ventas.isEmpty() -> {
                    Text("No tienes compras registradas.")
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        items(ventaState.ventas) { venta ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE3F2FD)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = "Pedido #${venta.pedidoId ?: venta.id}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Fecha: ${venta.fechaVenta}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = "Total: ${venta.total}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("camara") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF81C784),
                    contentColor = Color.White
                )
            ) {
                Text("Cambiar foto de perfil")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate(route = "login") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White
                )
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
