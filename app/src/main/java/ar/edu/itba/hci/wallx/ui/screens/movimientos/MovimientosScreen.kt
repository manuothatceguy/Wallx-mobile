package ar.edu.itba.hci.wallx.ui.screens.movimientos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R



data class movement(
    val destinatario: String,
    val detalle: String,
    val metodo: String,
    val monto: String,
    val pagador: String
)


@Composable
fun MovimientosScreen(modifier: Modifier = Modifier, onNavigate: (String) -> Unit) {
    val dummyMovements = listOf(
        movement("Juan Pérez", "Pago de servicio", "Transferencia", "$10.000,00", "María"),
        movement("Ana Gómez", "Compra online", "Tarjeta", "$25.000,50", "Ana"),
        movement("Carlos Ruiz", "Alquiler", "Transferencia", "$40.000,00", "Carlos"),
        movement("Empresa SA", "Sueldo", "Depósito", "$120.000,00", "Empresa SA"),
        movement("Marta López", "Regalo", "Transferencia", "$5.500,00", "Pedro")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 60.dp
            )
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(12.dp))
    ) {


        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Historial",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.ultimos_movimientos),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        stringResource(R.string.buscar_movimientos),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(dummyMovements) { movement ->
                    MovementOverview(movement)
                }
            }
        }
    }
}

@Composable
fun MovementOverview(movement: movement) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp)) // verde suave
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movement.monto,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = movement.destinatario,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp
        )
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(stringResource(R.string.detalle), color = Color.White, fontSize = 12.sp)
        }
    }
}


@Composable
fun Movements(movements: List<movement>){
    Column{
        for(movement in movements){
                MovementOverview(movement)
        }
    }
}

