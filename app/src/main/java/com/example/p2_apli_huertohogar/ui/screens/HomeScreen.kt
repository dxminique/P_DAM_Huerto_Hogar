package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.p2_apli_huertohogar.R
import com.example.p2_apli_huertohogar.viewModel.PerenualViewModel

data class Noticia(
    val titulo: String,
    val descripcion: String,
    val fecha: String
)

@Composable
fun HomeScreen(
    navController: NavController,
    perenualViewModel: PerenualViewModel = viewModel()
) {
    val noticias = listOf(
        Noticia(
            "🌱 Temporada de siembra",
            "Empieza la temporada de siembra de hortalizas de hoja.",
            "1 de noviembre 2025"
        ),
        Noticia(
            "🍓 Nueva cosecha disponible",
            "Frutillas y tomates frescos de huerto local.",
            "30 de octubre 2025"
        ),
        Noticia(
            "🌻 Taller de jardinería urbana",
            "Sábado en la mañana, cupos limitados.",
            "28 de octubre 2025"
        )
    )

    val apiState = perenualViewModel.uiState

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF1F8E9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_huerto),
                contentDescription = "Logo Huerto Hogar",
                modifier = Modifier
                    .size(140.dp)
                    .padding(bottom = 12.dp, top = 8.dp)
            )

            Text(
                text = "Noticias del huerto",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Noticias locales
                items(noticias) { noticia ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = noticia.titulo,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = noticia.descripcion)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = noticia.fecha,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "🍀 Tips agrícolas ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    when {
                        apiState.isLoading -> {
                            CircularProgressIndicator(
                                color = Color(0xFF4CAF50),
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                        apiState.error != null -> {
                            Text(
                                text = apiState.error ?: "",
                                color = Color.Red
                            )
                        }

                        apiState.plantas.isEmpty() -> {
                            Text("No se encontraron plantas recomendadas.")
                        }

                        else -> {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                apiState.plantas.take(3).forEach { plant ->
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
                                                text = plant.commonName ?: "Planta sin nombre",
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF1B5E20)
                                            )

                                            val sciName =
                                                plant.scientificName?.joinToString(", ") ?: ""
                                            if (sciName.isNotBlank()) {
                                                Text(
                                                    text = sciName,
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(4.dp))

                                            val luz =
                                                plant.sunlight?.joinToString(", ")
                                                    ?: "Sin info de luz"
                                            val riego =
                                                plant.watering ?: "Sin info de riego"

                                            Text(
                                                text = "Luz: $luz",
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "Riego: $riego",
                                                fontSize = 12.sp
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
}
