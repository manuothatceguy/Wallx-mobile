package ar.edu.itba.hci.wallx.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.model.Card
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.theme.AmexGrey
import ar.edu.itba.hci.wallx.ui.theme.DefaultCard
import ar.edu.itba.hci.wallx.ui.theme.MaestroBlue
import ar.edu.itba.hci.wallx.ui.theme.MastercardGrey
import ar.edu.itba.hci.wallx.ui.theme.VisaBlack
import ar.edu.itba.hci.wallx.ui.theme.White
import java.util.Locale


data class FullCard(
    val card: Card,
    val brand: String?
)


@Composable
fun CardItem(cardData: FullCard, viewModel : WallXViewModel,onNavigate: (String) -> Unit,) {
    val card = cardData.card
    val brandLogo = getBrandLogo(cardData.brand)
    val backgroundColor = getCardColor(cardData.brand)
    val expiry = card.expirationDate

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
                text = card.type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.titleMedium.copy(
                    color = White,
                    fontWeight = FontWeight.Bold)
            )
        }
        var showDeleteDialog by remember { mutableStateOf(false) }
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.eliminar),
            tint = White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 30.dp)
                .size(30.dp)
                .clickable {
                    showDeleteDialog = true
                }
        )
        if(showDeleteDialog){
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text(stringResource(R.string.eliminar_tarjeta))
            },
            text = {
                Column {
                    Text(stringResource(R.string.confirmar_eliminacion))
                    Spacer(Modifier.height(8.dp))
                    Text(stringResource(R.string.numero) + ": ****${card.number.takeLast(4)}")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.deleteCard(card.id)
                    }
                ) {
                    Text(stringResource(R.string.borrar))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(stringResource(R.string.cancelar))
                }
            }
        )
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
        "visa" -> VisaBlack
        "mastercard" -> MastercardGrey
        "amex" -> AmexGrey
        "maestro" -> MaestroBlue
        else -> DefaultCard
    }
}