package ar.edu.itba.hci.wallx.ui.screens.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card{
            Text(stringResource(R.string.perfil))
            YourInfo(viewModel)
            HorizontalDivider()
            Button(onClick = {

            }) {
                Text(stringResource(R.string.cambiar_contraseña))
            }
            Button(onClick = {

            }) {
                Text(stringResource(R.string.cambiar_alias))
            }
        }

    }
}

