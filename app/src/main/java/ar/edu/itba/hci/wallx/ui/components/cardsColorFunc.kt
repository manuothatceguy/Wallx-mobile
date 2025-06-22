package ar.edu.itba.hci.wallx.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.data.model.Card
import ar.edu.itba.hci.wallx.ui.theme.card_amex
import ar.edu.itba.hci.wallx.ui.theme.card_default
import ar.edu.itba.hci.wallx.ui.theme.card_maestro
import ar.edu.itba.hci.wallx.ui.theme.card_mastercard
import ar.edu.itba.hci.wallx.ui.theme.card_visa
import java.text.SimpleDateFormat
import java.util.Locale

data class fullCard(
    val card: Card,
    val brand: String?
)

@Composable
fun CardItem(cardData: fullCard) {
    val card = cardData.card
    val brandLogo = getBrandLogo(cardData.brand)
    val backgroundColor = getCardColor(cardData.brand)
    val expiry = card.expirationDate

    WallXElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.586f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            // Card number
            Text(
                text = "**** ${card.number.takeLast(4)}",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            )

            // Card holder name
            Text(
                text = card.fullName,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.align(Alignment.BottomStart)
            )

            // Expiry date
            Text(
                text = expiry,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.align(Alignment.BottomEnd)
            )

            // Brand logo and type
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Image(
                    painter = painterResource(id = brandLogo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = card.type.capitalize(Locale.getDefault()),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // Delete icon
            WallXIconButton(
                onClick = { /* TODO: Implement delete functionality */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 30.dp),
                icon = Icons.Default.Delete,
                contentDescription = stringResource(R.string.eliminar),
                tint = Color.White
            )
        }
    }
}

@Composable
fun CompactCardItem(cardData: fullCard) {
    val card = cardData.card
    val brandLogo = getBrandLogo(cardData.brand)
    val backgroundColor = getCardColor(cardData.brand)

    WallXCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Brand logo
                Image(
                    painter = painterResource(id = brandLogo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Card info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "**** ${card.number.takeLast(4)}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = card.fullName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    )
                }
                
                // Card type
                Text(
                    text = card.type.capitalize(Locale.getDefault()),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
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
        else -> R.drawable.visa
    }
}

fun getCardColor(brand: String?): Color {
    return when (brand?.lowercase()) {
        "visa" -> card_visa
        "mastercard" -> card_mastercard
        "amex" -> card_amex
        "maestro" -> card_maestro
        else -> card_default
    }
}