package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun CarritoScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF9FBE7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Carrito de compras",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF33691E)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tu carrito está vacío por ahora.",
                color = Color.Gray
            )
            Text(
                text = "Explora y conoce nuestros productos.",
                color = Color.Gray
            )
            Text(
                text = "🥬",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("pedidos") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFE7A6),
                    contentColor = Color(0xFF1B5E20)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver mis pedidos")
            }
        }
    }
}