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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.model.Card
import ar.edu.itba.hci.wallx.ui.theme.Selected
import ar.edu.itba.hci.wallx.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale
import ar.edu.itba.hci.wallx.ui.components.CardItem
import ar.edu.itba.hci.wallx.ui.components.detectCardBrandFromNumber
import ar.edu.itba.hci.wallx.ui.components.fullCard
import androidx.compose.runtime.getValue
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations


@Composable
fun TarjetasScreen( modifier: Modifier = Modifier,
                    wallXViewModel: WallXViewModel,
                    onNavigateTo: (String) -> Unit) {
    val formatter = SimpleDateFormat("MM/yy", Locale.getDefault())
    val uiState by wallXViewModel.uiState.collectAsState()
    val cards = uiState.cardsDetail ?: emptyList()

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
            Button(onClick = { onNavigateTo(AppDestinations.AGREGAR_TARJETA.route) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
                    containerColor =  MaterialTheme.colorScheme.secondaryContainer,
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
            items(cards) { card ->
                CardItem(fullCard(card, detectCardBrandFromNumber(card.number)))
            }
        }
    }
}








