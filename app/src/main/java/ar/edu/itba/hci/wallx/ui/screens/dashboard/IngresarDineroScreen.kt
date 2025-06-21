package ar.edu.itba.hci.wallx.ui.screens.dashboard

import androidx.compose.foundation.Image
import ar.edu.itba.hci.wallx.data.model.Card
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.screens.servicios.textFieldColors
import ar.edu.itba.hci.wallx.ui.theme.Background
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale
import ar.edu.itba.hci.wallx.ui.components.getBrandLogo
import ar.edu.itba.hci.wallx.ui.components.getCardColor
import ar.edu.itba.hci.wallx.ui.theme.Black
import ar.edu.itba.hci.wallx.ui.theme.Grey
import ar.edu.itba.hci.wallx.ui.theme.Selected


@Composable
fun IngresarDineroScreen(
    modifier: Modifier = Modifier,
    wallXViewModel: WallXViewModel,
    onNavigate: (String) -> Unit
) {
    var monto by remember { mutableStateOf("") }
    var montoError by remember { mutableStateOf<String?>(null) }
    var selectedCardIndex by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val uiState by wallXViewModel.uiState.collectAsState()
    val cards = uiState.cardsDetail ?: emptyList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(760.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.ingresar_dinero),
                    style = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = Black,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = stringResource(R.string.seleccionar_tarjeta) + ':',
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SecondaryDarken1,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 19.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(cards) { index, card ->
                        MiniCardItem(
                            card = card,
                            isSelected = index == selectedCardIndex,
                            onClick = { selectedCardIndex = index }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.monto_deseado) + ':',
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SecondaryDarken1,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 19.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = monto,
                    onValueChange = {
                        monto = it
                        montoError = null
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.Ejemplo) + ": 5000",
                            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Grey
                        )
                    },
                    singleLine = true,
                    isError = montoError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = textFieldColors()
                )

                // Mensaje de error
                montoError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = Typography.bodySmall,
                        modifier = Modifier.align(Alignment.Start).padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val montoDouble = monto.toDoubleOrNull()
                        if (montoDouble == null || montoDouble <= 0) {
                            montoError = "El monto ingresado no es válido"
                        } else if (cards.isEmpty()) {
                            montoError = "No hay tarjetas disponibles"
                        } else {
                            montoError = null
                            showDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Selected,
                        contentColor = White
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.ingresar),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

    if (showDialog && monto.toDoubleOrNull() != null && cards.isNotEmpty()) {
        IngresarDineroDialog(
            amount = monto.toDouble(),
            card = cards[selectedCardIndex],
            wallXViewModel = wallXViewModel,
            onDismiss = { showDialog = false }
        )
    }
}



@Composable
fun MiniCardItem( card: Card,
                  isSelected: Boolean,
                  onClick: () -> Unit
){
    val cardColor = getCardColor(card.type)
    val brandLogo = getBrandLogo(card.type)

    Card(
        modifier = Modifier
            .width(240.dp)
            .height(160.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) cardColor else cardColor.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = "**** ${card.number.takeLast(4)}",
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White,
                )
                Text(
                    text = card.fullName,
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                )
            }

            Image(
                painter = painterResource(id = brandLogo),
                contentDescription = "Brand logo",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.TopEnd)
            )
        }
    }

}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium)
        Text(text = value)
    }
}

fun Double.format(decimals: Int): String = "%.${decimals}f".format(this)


@Composable
fun IngresarDineroDialog(
    amount: Double,
    card: Card,
    onDismiss: () -> Unit,
    wallXViewModel: WallXViewModel
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Confirmar ingreso",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    InfoRow("Monto", "$ ${amount.format(2)}")

                    Spacer(modifier = Modifier.height(8.dp))

                    MiniCardItem(
                        card = card,
                        isSelected = true,
                        onClick = {}
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            wallXViewModel.recharge(amount)
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(stringResource(R.string.confirmar), color = Color.White)
                    }
                }
            }
        }
    }
}
