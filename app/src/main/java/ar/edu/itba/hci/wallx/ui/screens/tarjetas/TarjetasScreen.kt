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
import ar.edu.itba.hci.wallx.ui.theme.Accent
import ar.edu.itba.hci.wallx.ui.theme.AmexGrey
import ar.edu.itba.hci.wallx.ui.theme.Black
import ar.edu.itba.hci.wallx.ui.theme.ButtonColor
import ar.edu.itba.hci.wallx.ui.theme.DefaultCard
import ar.edu.itba.hci.wallx.ui.theme.MaestroBlue
import ar.edu.itba.hci.wallx.ui.theme.MastercardGrey
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.Selected
import ar.edu.itba.hci.wallx.ui.theme.SurfaceLight
import ar.edu.itba.hci.wallx.ui.theme.VisaBlack
import ar.edu.itba.hci.wallx.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale

data class fullCard(
    val card: Card,
    val brand: String?
)

@Composable
fun TarjetasScreen(modifier: Modifier = Modifier, onNavigate: (String) -> Unit) {
    val formatter = SimpleDateFormat("MM/yy", Locale.getDefault())

    val dummyCards = listOf(
        fullCard(
            card = Card(1, "credito", "4123", formatter.parse("12/26")!!, "Juan Pérez"),
            brand = detectCardBrandFromNumber("4123")
        ),
        fullCard(
            card = Card(2, "debito", "5234", formatter.parse("03/27")!!, "Ana López"),
            brand = detectCardBrandFromNumber("5234")
        ),
        fullCard(
            card = Card(3, "credito", "3765", formatter.parse("08/25")!!, "Carlos Ruiz"),
            brand = detectCardBrandFromNumber("3765")
        )
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
            Button(onClick = { onNavigate("agregar") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Selected,
                    contentColor = White
                ),
            )
            {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.agregar))
                Spacer(Modifier.width(4.dp))
                Text(  text=stringResource(R.string.agregar), style = MaterialTheme.typography.titleLarge.copy(
                    color = White,
                    fontWeight = FontWeight.Bold)
                )
            }

            Button(onClick = { /* escanear */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Selected,
                    contentColor = White
                ),
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = stringResource(R.string.escanear))
                Spacer(Modifier.width(4.dp))
                Text(  text=stringResource(R.string.escanear), style = MaterialTheme.typography.titleLarge.copy(
                    color = White,
                    fontWeight = FontWeight.Bold)
                )
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
fun CardItem(cardData: fullCard) {
    val card = cardData.card
    val brandLogo = getBrandLogo(cardData.brand)
    val backgroundColor = getCardColor(cardData.brand)
    val expiry = SimpleDateFormat("MM/yy", Locale.getDefault()).format(card.expirationDate)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.586f)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "**** ${card.number.takeLast(4)}",
            style = MaterialTheme.typography.titleLarge.copy(
                color = White,
                fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = card.fullName,
            style = MaterialTheme.typography.titleLarge.copy(
                color = White,
                fontWeight = FontWeight.SemiBold),

            modifier = Modifier.align(Alignment.BottomStart)
        )

        Text(
            text = expiry,
            style = MaterialTheme.typography.titleMedium.copy(
                color = White,
                fontWeight = FontWeight.SemiBold),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        // Aquí pongo logo y tipo juntos en un Row
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
                    color = White,
                    fontWeight = FontWeight.Bold)
            )
        }
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.eliminar),
            tint = White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 30.dp)
                .size(30.dp)
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
        else -> R.drawable.visa
    }
}

fun getCardColor(brand: String?): Color {
    return when (brand?.lowercase()) {
        "visa" -> VisaBlack
        "mastercard" -> MastercardGrey
        "amex" -> AmexGrey
        "maestro" -> MaestroBlue
        else -> DefaultCard
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetasScreenPreview() {
    WallxTheme(dynamicColor = false) {
        TarjetasScreen(modifier = Modifier, onNavigate = {})
    }
}






