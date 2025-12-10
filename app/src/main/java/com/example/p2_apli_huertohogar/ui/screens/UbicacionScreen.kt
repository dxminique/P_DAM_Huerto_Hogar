package com.example.p2_apli_huertohogar.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices

@Composable
fun UbicacionScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var ubicacionTexto by remember { mutableStateOf<String?>(null) }
    var errorTexto by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorTexto) {
        val msg = errorTexto
        if (msg != null) {
            snackbarHostState.showSnackbar(msg)
            errorTexto = null
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            isLoading = true
            fusedClient.lastLocation
                .addOnSuccessListener { location ->
                    isLoading = false
                    if (location != null) {
                        ubicacionTexto =
                            "Latitud: ${location.latitude}\nLongitud: ${location.longitude}"
                    } else {
                        errorTexto = "No se pudo obtener la ubicación"
                    }
                }
                .addOnFailureListener { e ->
                    isLoading = false
                    errorTexto = e.message ?: "Error al obtener la ubicación"
                }
        } else {
            errorTexto = "Se necesita el permiso de ubicación para continuar"
        }
    }

    fun solicitarUbicacion() {
        val tienePermiso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (tienePermiso) {
            isLoading = true
            fusedClient.lastLocation
                .addOnSuccessListener { location ->
                    isLoading = false
                    if (location != null) {
                        ubicacionTexto =
                            "Latitud: ${location.latitude}\nLongitud: ${location.longitude}"
                    } else {
                        errorTexto = "No se pudo obtener la ubicación"
                    }
                }
                .addOnFailureListener { e ->
                    isLoading = false
                    errorTexto = e.message ?: "Error al obtener la ubicación"
                }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    androidx.compose.material3.Scaffold(
        containerColor = Color(0xFFF1F8E9),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = Color(0xFFF1F8E9)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ubicación actual",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (ubicacionTexto != null) {
                    Text(
                        text = ubicacionTexto ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF1B5E20),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else if (!isLoading) {
                    Text(
                        text = "Pulsa el botón para obtener tu ubicación",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (isLoading) {
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.CircularProgressIndicator(
                        color = Color(0xFF4CAF50)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { solicitarUbicacion() },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    Text("Obtener ubicación actual")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF81C784),
                        contentColor = Color.White
                    )
                ) {
                    Text("Volver")
                }
            }
        }
    }
}
