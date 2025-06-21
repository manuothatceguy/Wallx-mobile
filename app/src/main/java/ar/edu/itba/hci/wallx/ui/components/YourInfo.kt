package ar.edu.itba.hci.wallx.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.screens.dashboard.InfoCard
import ar.edu.itba.hci.wallx.ui.theme.SurfaceVariant

@Composable
fun YourInfo(viewModel: WallXViewModel){
    val uiState by viewModel.uiState.collectAsState()
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .heightIn(min = 210.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background) // o Secondary, si querés un bloque destacado
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = stringResource(R.string.tu_información),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold, color=MaterialTheme.colorScheme.onSecondaryContainer)
                )
            }
            HorizontalDivider(thickness = 1.dp,color = SurfaceVariant)
            var aliasString = if(uiState.accountDetail != null) uiState.accountDetail!!.alias else "Error"
            var cvuString = if(uiState.accountDetail != null) uiState.accountDetail!!.cvu else "Error"
            Text(stringResource(R.string.alias))
            InfoCard( aliasString)
            Text(stringResource(R.string.CVU))
            InfoCard(cvuString)
            HorizontalDivider(thickness = 1.dp,color = MaterialTheme.colorScheme.outline)

        }
    }
}
