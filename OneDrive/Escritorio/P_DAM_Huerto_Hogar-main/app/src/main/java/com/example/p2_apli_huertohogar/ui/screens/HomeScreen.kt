package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.navigation.NavController
import com.example.p2_apli_huertohogar.R


data class Noticia(
    val titulo: String,
    val descripcion: String,
    val fecha: String
)

@Composable
fun HomeScreen(navController: NavController) {
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(noticias) { noticia ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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
            }
        }
    }
}