package ar.edu.itba.hci.wallx.ui.screens.tarjetas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CardData(val lastDigits: String, val brand: String, val backgroundColor: Color)

@Composable
fun TarjetasScreen(modifier: Modifier = Modifier) {
    //queda setear los colores y las imagenes de los logos de las tarjetas lol!!
    val dummyCards = listOf(
        CardData("1234", "visa", Color(0xFF1A1A1A)),
        CardData("5678", "mastercard", Color(0xFF7E7B7B)),
        CardData("9876", "amex", Color(0xFFD3D3D3))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botones centrados
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Agregar")
            }

            Button(onClick = { /* TODO */ }) {
                Icon(Icons.Default.PhotoCamera, contentDescription = "Escanear")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Escanear")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(dummyCards) { card ->
                CreditCardItem(card)
            }
        }
    }
}

@Composable
fun CreditCardItem(card: CardData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // más padding en los costados
            .aspectRatio(1.586f)
            .background(card.backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text("**** ${card.lastDigits}", color = Color.White, fontSize = 16.sp)
        }

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Eliminar",
            tint = Color.White,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        //falta importar imagenes de las marcas de las tarjetas y poner las imagenes en vez de texto lol!!!
        Text(
            text = when (card.brand.lowercase()) {
                "visa" -> "VISA"
                "mastercard" -> "MASTERCARD"
                "amex" -> "AMEX"
                else -> "OTRO"
            },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
