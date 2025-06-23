package ar.edu.itba.hci.wallx.ui.screens.movimientos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.data.model.User
import androidx.compose.runtime.collectAsState
import ar.edu.itba.hci.wallx.WallXViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import ar.edu.itba.hci.wallx.R

@Composable
fun MovimientoDetalleScreen( modifier: Modifier = Modifier,
                             wallXViewModel: WallXViewModel,
                             onNavigateTo: (String) -> Unit) {

    val uiState by wallXViewModel.uiState.collectAsState()
    val payment=uiState.currentPayment

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = stringResource(R.string.Detalle_del_movimiento),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(stringResource(R.string.monto), "$ ${payment?.amount?.format(2) ?: "-"}")
                InfoRow(stringResource(R.string.descripcion), payment?.description ?: "-")
                InfoRow(stringResource(R.string.destinatario), payment?.receiver?.fullName() ?: "-")
                InfoRow(stringResource(R.string.pagador), payment?.payer?.fullName() ?: "-")
                InfoRow(stringResource(R.string.metodo_de_pago), payment?.method ?: "-")
                InfoRow(
                    stringResource(R.string.tarjeta),
                    payment?.card?.number?.takeLast(4)?.let { "**** $it" } ?: "-"
                )
                InfoRow(stringResource(R.string.estado), if (payment?.pending == true) stringResource(R.string.pendiente) else stringResource(R.string.completado))
            }
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

fun User.fullName(): String = "$firstName $lastName"

