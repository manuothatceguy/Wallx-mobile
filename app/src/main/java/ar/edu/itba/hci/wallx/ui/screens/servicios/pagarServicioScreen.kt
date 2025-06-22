package ar.edu.itba.hci.wallx.ui.screens.servicios

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.model.Card
import ar.edu.itba.hci.wallx.ui.components.getBrandLogo
import ar.edu.itba.hci.wallx.ui.components.getCardColor
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.screens.dashboard.AddCardButton
import ar.edu.itba.hci.wallx.ui.screens.dashboard.InfoRow
import ar.edu.itba.hci.wallx.ui.screens.dashboard.format
import ar.edu.itba.hci.wallx.ui.theme.Black
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1
import ar.edu.itba.hci.wallx.ui.theme.Selected
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.White

@Composable
fun PagarServicioScreen(
    modifier: Modifier,
    viewModel: WallXViewModel,
    onNavigate: (String) -> Unit,
){
    val codigoPago = viewModel.codigoDePago.collectAsState().value
    var montoError by remember { mutableStateOf<String?>(null) }
    var selectedCardIndex by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val cards = uiState.cardsDetail ?: emptyList()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(760.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),

        ) {
            Spacer(modifier = Modifier.size(95.dp))
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    ,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Text(
                    text = stringResource(R.string.pagar_servicio),
                    style = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = Black,
                    modifier = Modifier.padding(16.dp)
                )
                Text (
                    text = stringResource(R.string.codigo_pago) + ": $codigoPago",
                )
                Text(
                    text = stringResource(R.string.seleccionar_metodo) ,
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
                    item {
                        MiniCardItem(
                            isSelected = -1 == selectedCardIndex,
                            onClick = { selectedCardIndex = -1 },

                            viewModel = viewModel
                        )
                    }
                    if (cards.isEmpty()) {
                        item {
                            AddCardButton { onNavigate(AppDestinations.AGREGAR_TARJETA.route) }
                        }
                    } else {
                        itemsIndexed(cards) { index, card ->
                            MiniCardItem(
                                card = card,
                                isSelected = index == selectedCardIndex,
                                onClick = { selectedCardIndex = index },
                                viewModel = viewModel
                            )
                        }
                        item {
                            AddCardButton { onNavigate(AppDestinations.AGREGAR_TARJETA.route) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (cards.isEmpty()) {
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
                    )
                ) {
                    Text(
                        text = stringResource(R.string.Pagar),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

    if (showDialog && cards.isNotEmpty() && codigoPago != null) {
        PagarServicioDialog(
            cards[selectedCardIndex],
            wallXViewModel = viewModel,
            onDismiss = { showDialog = false },
            codigo = codigoPago,
            onConfirm = { onNavigate(AppDestinations.DASHBOARD.route) }
        )
    }
}
@Composable
fun PagarServicioDialog(

    card: Card? = null,
    onDismiss: () -> Unit,
    wallXViewModel: WallXViewModel,
    onConfirm: ()-> Unit,
    codigo: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
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
                        text = "Confirmar pago",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    MiniCardItem(
                        card = card,
                        isSelected = true,
                        onClick = {},
                        viewModel = wallXViewModel
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if(card != null)wallXViewModel.payService(codigo, card.id) else wallXViewModel.payService(codigo)
                            onDismiss()
                            onConfirm()
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

@Composable
fun MiniCardItem( card: Card? = null,
                  isSelected: Boolean,
                  onClick: () -> Unit,
                  viewModel: WallXViewModel
){
    val cardColor = if(card != null)getCardColor(card.type) else MaterialTheme.colorScheme.surfaceContainerHigh
    val brandLogo = getBrandLogo(card?.type)
    val uiState by viewModel.uiState.collectAsState()
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
                .padding(12.dp),


            ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                if (card != null) {
                    Text(
                        text = "**** ${card.number.takeLast(4)}",
                        style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White,
                    )
                }else{
                    Text(
                        text = "Dinero en cuenta",
                        style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    )

                    Text(text = uiState.accountDetail?.balance.toString())
                }
                if (card != null) {
                    Text(
                        text = card.fullName,
                        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                    )
                }
            }
            if (card != null) {
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

}