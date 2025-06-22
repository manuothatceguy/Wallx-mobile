package ar.edu.itba.hci.wallx.ui.screens.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.components.YourInfo

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

