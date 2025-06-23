package ar.edu.itba.hci.wallx.ui.screens.servicios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.screens.tarjetas.AgregarTarjetaScreen
import ar.edu.itba.hci.wallx.ui.theme.Background
import ar.edu.itba.hci.wallx.ui.theme.Error
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.Primary
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1
import ar.edu.itba.hci.wallx.ui.theme.Selected
import ar.edu.itba.hci.wallx.ui.theme.SurfaceLight
import ar.edu.itba.hci.wallx.ui.theme.SurfaceVariant
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
import ar.edu.itba.hci.wallx.ui.theme.White


@Composable
fun ServiciosScreen(
    modifier: Modifier = Modifier,
    viewModel: WallXViewModel,
    onNavigate: (String) -> Unit
) {
    var codigoPago by remember { mutableStateOf("") }
    var motivoCobro by remember { mutableStateOf("") }
    var montoCobro by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.Pagar),
                    style = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 12.dp),
                )

                Text(text =stringResource(R.string.ingresarLink),style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = codigoPago,
                    onValueChange = { newCode -> codigoPago = newCode },
                    placeholder = { Text(stringResource(R.string.codigoPago), color = Info)},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.setCodigoDePago(codigoPago)
                        onNavigate(AppDestinations.PAGAR_SERVICIO.route)
                    },
                    modifier = Modifier.fillMaxWidth().height(70.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    shape = RoundedCornerShape(10.dp),



                ) {
                    Text(stringResource(R.string.pagarServicio), style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                }
            }
        }

/*
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor =MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text =stringResource(R.string.Cobrar),
                    style = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 12.dp),


                )

                Text(
                    text = stringResource(R.string.ingresaMotivoCobro),
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = motivoCobro,
                    onValueChange = { motivoCobro = it },
                    placeholder = { Text(stringResource(R.string.motivoCobro), color = Info) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.ingresaMontoCobrar),
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = montoCobro,
                    onValueChange = { montoCobro = it },
                    placeholder = { Text(stringResource(R.string.monto), color = Info) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /* Lógica para generar código */ },
                    modifier = Modifier.fillMaxWidth().height(70.dp),
                    colors = ButtonDefaults.buttonColors( contentColor = White,containerColor = Selected),
                    shape = RoundedCornerShape(10.dp),

                    ) {
                    Text(stringResource(R.string.generarCodigo), style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold) , color = White)
                }
            }
        }*/
    }
}

@Composable
fun textFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedTextColor =  MaterialTheme.colorScheme.onPrimary,
        unfocusedTextColor = Info,
        disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
        errorTextColor = MaterialTheme.colorScheme.error,
        focusedContainerColor =  MaterialTheme.colorScheme.secondary,
        unfocusedContainerColor =  MaterialTheme.colorScheme.background,
        disabledContainerColor =  MaterialTheme.colorScheme.surfaceBright,
        errorContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
        cursorColor =  MaterialTheme.colorScheme.secondary,
        focusedBorderColor =  MaterialTheme.colorScheme.secondaryContainer,
        unfocusedBorderColor = Interactive,
        disabledBorderColor =  MaterialTheme.colorScheme.surfaceVariant,
        errorBorderColor = MaterialTheme.colorScheme.error
    )
}
