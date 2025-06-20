package ar.edu.itba.hci.wallx.ui.screens.tarjetas

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R

data class CardData(
    val lastDigits: String,
    val brand: String,
    val backgroundColor: Color,
    val owner: String,
    val expiryDate: String
)

@Composable
fun TarjetasScreen(modifier: Modifier = Modifier, onNavigate: (String) -> Unit) {
    val dummyCards = listOf(
        CardData("4123", detectCardBrandFromNumber("4123"), Color(0xFF1A1A1A), "Juan Pérez", "12/26"),
        CardData("5234", detectCardBrandFromNumber("5234"), Color(0xFF7E7B7B), "Ana López", "03/27"),
        CardData("3765", detectCardBrandFromNumber("3765"), Color(0xFFD3D3D3), "Carlos Ruiz", "08/25")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Botones arriba
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { /* TODO */ }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
                Spacer(Modifier.width(4.dp))
                Text(stringResource(R.string.agregar))
            }

            Button(onClick = { /* TODO */ }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.PhotoCamera, contentDescription = "Escanear")
                Spacer(Modifier.width(4.dp))
                Text(stringResource(R.string.escanear))
            }
        }

        Spacer(Modifier.height(24.dp))


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(dummyCards) { card ->
                CardItem(card)
            }
        }
    }
}

@Composable
fun CardItem(card: CardData) {
    val brandLogo = getBrandLogo(card.brand)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.586f)
            .background(card.backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        Text(
            text = "${card.lastDigits}********${card.lastDigits}",//ver como mostrar primeros y ultimos de un solo string de nums
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = card.owner,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.BottomStart)
        )

        Text(
            text = card.expiryDate,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        // Logo arriba a la izquierda
        Image(
            painter = painterResource(id = brandLogo),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopStart)
        )

        // Botón eliminar (si querés mantenerlo)
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Eliminar",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
        )
    }
}



fun detectCardBrandFromNumber(cardNumber: String?): String {
    if (cardNumber.isNullOrEmpty()) return ""
    return when (cardNumber[0]) {
        '4' -> "visa"
        '5', '2' -> "mastercard"
        '3' -> "amex"
        '6' -> "maestro"
        else -> ""
    }
}

fun getBrandLogo(brand: String?): Int {
    return when (brand?.lowercase()) {
        "visa" -> R.drawable.visa
        "mastercard" -> R.drawable.mastercard
        "amex" -> R.drawable.amex
        "maestro" -> R.drawable.maestro
        else -> R.drawable.visa // ver q hacemos cuando no detecta
    }
}
