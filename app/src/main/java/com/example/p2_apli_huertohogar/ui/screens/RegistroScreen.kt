package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p2_apli_huertohogar.viewModel.RegistroNavEvent
import com.example.p2_apli_huertohogar.viewModel.RegistroViewModel

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: RegistroViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is RegistroNavEvent.NavigateToLogin -> {

                    navController.navigate(event.route) {
                        popUpTo("login") { inclusive = false }
                    }
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFB9E4E5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))


            if (uiState.errorMensaje != null) {
                Text(
                    text = uiState.errorMensaje!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) }, // <-- Llama al VM
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMensaje != null
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onCorreoChange(it) }, // <-- Llama al VM
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMensaje != null
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = uiState.contrasena,
                onValueChange = { viewModel.onContrasenaChange(it) }, // <-- Llama al VM
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMensaje != null
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { viewModel.onRegistroClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }
        }
    }
}