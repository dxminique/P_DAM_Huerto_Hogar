package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PerfilScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF1F8E9)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Perfil de Usuario",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(24.dp))

            // ESTE ES EL BOTÓN NUEVO:
            Button(
                onClick = { navController.navigate("camara") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cambiar foto de perfil")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Nombre: Juan Pérez")
            Text("Correo: juanperez@huertohogar.cl")
            Text("Dirección: Camino del Sol 123, Santiago")
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                navController.navigate("login")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )) {
                Text("Cerrar sesión")
            }
        }
    }
}