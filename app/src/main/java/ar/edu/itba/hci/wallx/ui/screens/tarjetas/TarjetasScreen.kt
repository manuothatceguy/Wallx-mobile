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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
import ar.edu.itba.hci.wallx.data.model.Card
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TarjetasScreen(modifier: Modifier = Modifier) {
    val dummyCards = listOf(
        Card(1, detectCardBrandFromNumber("4123456789012345"), "4123456789012345", getDate("12/26"), "Juan Pérez"),
        Card(2, detectCardBrandFromNumber("5234567890123456"), "5234567890123456", getDate("03/27"), "Ana López"),
        Card(3, detectCardBrandFromNumber("376543210987654"), "376543210987654", getDate("08/25"), "Carlos Ruiz")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
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
fun CardItem(card: Card) {
    val brandLogo = getBrandLogo(card.type)
    val backgroundColor = getBrandColor(card.type)
    val maskedNumber = "**** **** **** ${card.number.takeLast(4)}"
    val expiryFormatted = formatExpiry(card.expirationDate)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.586f)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        Text(
            text = maskedNumber,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = card.fullName,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.BottomStart)
        )

        Text(
            text = expiryFormatted,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Image(
            painter = painterResource(id = brandLogo),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopStart)
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Eliminar",
            tint = Color.White,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

fun formatExpiry(date: Date): String {
    val format = SimpleDateFormat("MM/yy", Locale.getDefault())
    return format.format(date)
}

fun getDate(dateStr: String): Date {
    val format = SimpleDateFormat("MM/yy", Locale.getDefault())
    return format.parse(dateStr) ?: Date()
}

fun getBrandLogo(brand: String?): Int {
    return when (brand?.lowercase()) {
        "visa" -> R.drawable.visa
        "mastercard" -> R.drawable.mastercard
        "amex" -> R.drawable.amex
        "maestro" -> R.drawable.maestro
        else -> R.drawable.visa
    }
}

fun getBrandColor(brand: String?): Color {
    return when (brand?.lowercase()) {
        "visa" -> Color(0xFF1A1A1A)
        "mastercard" -> Color(0xFF7E7B7B)
        "amex" -> Color(0xFFD3D3D3)
        "maestro" -> Color.Gray
        else -> Color.Gray
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


@Preview(showBackground = true)
@Composable
fun TarjetasScreenPreview() {
    WallxTheme(dynamicColor = false) {
        TarjetasScreen(
            modifier = Modifier
        )
    }
}