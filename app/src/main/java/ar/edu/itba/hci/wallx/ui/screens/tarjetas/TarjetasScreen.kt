package ar.edu.itba.hci.wallx.ui.screens.tarjetas

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import ar.edu.itba.hci.wallx.ui.theme.White
import ar.edu.itba.hci.wallx.ui.components.CardItem
import ar.edu.itba.hci.wallx.ui.components.detectCardBrandFromNumber
import androidx.compose.runtime.getValue
import ar.edu.itba.hci.wallx.ui.components.DeviceLayout
import ar.edu.itba.hci.wallx.ui.components.FullCard
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations


@Composable
fun TarjetasScreen( modifier: Modifier = Modifier,
                    wallXViewModel: WallXViewModel,
                    onNavigateTo: (String) -> Unit) {
    val uiState by wallXViewModel.uiState.collectAsState()
    val cards = uiState.cardsDetail ?: emptyList()

    DeviceLayout(
        phoneVertical= {TarjetasVertical(modifier, onNavigateTo, cards,wallXViewModel)},
        phoneHorizontal={TarjetasHorizontal(modifier, onNavigateTo, cards,wallXViewModel)},
        tabletVertical={TarjetasVertical(modifier, onNavigateTo, cards,wallXViewModel)},
        tabletHorizontal={TarjetasHorizontal(modifier, onNavigateTo, cards,wallXViewModel)}
    )

}

@Composable
fun TarjetasVertical(modifier: Modifier = Modifier,
                    onNavigateTo: (String) -> Unit,
                     cards: List<Card>,
                     viewModel: WallXViewModel){
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

            Button(onClick = {  },
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
                CardItem(FullCard(card, detectCardBrandFromNumber(card.number)),viewModel,onNavigateTo)
            }
        }
    }
}

@Composable
fun TarjetasHorizontal(
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
    cards: List<Card>,
    viewModel: WallXViewModel
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
        ) {
            Button(
                onClick = { onNavigateTo(AppDestinations.AGREGAR_TARJETA.route) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = White
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.agregar))
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.agregar),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Button(
                onClick = {  },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = White
                )
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = stringResource(R.string.escanear))
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.escanear),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cards) { card ->
                CardItem(FullCard(card, detectCardBrandFromNumber(card.number)),viewModel,onNavigateTo)
            }
        }
    }
}






