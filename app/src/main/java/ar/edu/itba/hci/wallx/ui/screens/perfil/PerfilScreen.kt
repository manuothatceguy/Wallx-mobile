package ar.edu.itba.hci.wallx.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import ar.edu.itba.hci.wallx.ui.components.YourInfo
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1

@Composable
fun PerfilScreen(modifier: Modifier = Modifier, viewModel: WallXViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var newAlias by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column {

            YourInfo(viewModel,true)
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {}) {
                        Text(stringResource(R.string.cambiar_contraseña))
                    }
                }
            }

            Row {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        showDialog = true
                    }) {
                        Text(stringResource(R.string.cambiar_alias))
                    }
                }
            }
        }

        if (showDialog) {
            newAlias = ""
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.cambiar_alias),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            IconButton(onClick = { showDialog = false }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = newAlias,
                            onValueChange = { newAlias = it },
                            label = { Text(stringResource(R.string.nuevo_alias), color = MaterialTheme.colorScheme.onBackground) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
                                errorTextColor = MaterialTheme.colorScheme.error,
                                errorContainerColor = MaterialTheme.colorScheme.error.copy(
                                    alpha = 0.1f
                                ),

                                cursorColor = Info,

                                focusedBorderColor = Info,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                                errorBorderColor = MaterialTheme.colorScheme.error,

                                focusedLeadingIconColor = SecondaryDarken1,
                                unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),

                                focusedTrailingIconColor = SecondaryDarken1,
                                unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.updateAlias(newAlias)
                                showDialog = false
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Aceptar")
                        }
                    }
                }
            }
        }
    }
}

