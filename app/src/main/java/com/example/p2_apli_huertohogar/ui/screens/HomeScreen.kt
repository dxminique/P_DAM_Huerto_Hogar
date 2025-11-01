package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color



@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF1F8E9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bienvenido a Huerto Hogar", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("productos") }) {
                Text("Ver productos")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { navController.navigate("pedidos") }) {
                Text("Ver pedidos")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { navController.navigate("login") }) {
                Text("iniciar sesión")
            }


        }
    }
}