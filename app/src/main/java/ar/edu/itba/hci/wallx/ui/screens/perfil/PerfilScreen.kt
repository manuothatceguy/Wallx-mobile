package ar.edu.itba.hci.wallx.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.model.Payment
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.wallx.ui.components.YourInfo
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.screens.tarjetas.TarjetasScreen
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme

@Composable
fun PerfilScreen(modifier: Modifier = Modifier, viewModel : WallXViewModel){
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ){
            YourInfo(viewModel)
            Spacer(modifier = Modifier.height(6.dp))
            Button(onClick = {

            }) {
                Text(
                    text = stringResource(R.string.cambiar_contraseña),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Button(onClick = {

            }) {
                Text(
                    text = stringResource(R.string.cambiar_alias),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }
}

