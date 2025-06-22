package ar.edu.itba.hci.wallx.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel

@Composable
fun YourInfo(viewModel: WallXViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    
    WallXCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                WallXTitle(
                    text = stringResource(R.string.tu_información),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            WallXVerticalSpacer(16.dp)

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                InfoCard(
                    text = uiState.accountDetail?.cvu ?: "N/A",
                    label = "CVU"
                )

                InfoCard(
                    text = uiState.accountDetail?.alias ?: "N/A",
                    label = stringResource(R.string.alias)
                )
            }
        }
    }
}

@Composable
private fun InfoCard(
    text: String,
    label: String
) {
    WallXCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                WallXText(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                WallXText(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            WallXIconButton(
                onClick = { /* TODO: Implement copy functionality */ },
                icon = Icons.Filled.ContentCopy,
                contentDescription = "copy",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
