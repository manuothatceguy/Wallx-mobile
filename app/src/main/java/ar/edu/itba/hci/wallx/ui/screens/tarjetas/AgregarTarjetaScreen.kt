package ar.edu.itba.hci.wallx.ui.screens.tarjetas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.style.TextAlign
import ar.edu.itba.hci.wallx.ui.components.detectCardBrandFromNumber
import ar.edu.itba.hci.wallx.ui.theme.Black
import ar.edu.itba.hci.wallx.ui.theme.Error
import ar.edu.itba.hci.wallx.ui.theme.Grey
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.Selected
import ar.edu.itba.hci.wallx.ui.theme.Success
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.White


@Composable
fun AgregarTarjetaScreen(modifier: Modifier = Modifier) {
    var number by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    val type = detectCardBrandFromNumber(number)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(R.string.agregarTarjeta),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = {
                        Text(text=stringResource(R.string.numero),
                            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Grey)
                            },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = Secondary,
                        unfocusedContainerColor = Secondary,
                        disabledContainerColor = Secondary
                    )

                )

                Spacer(modifier.height(13.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text(text=stringResource(R.string.nombre),
                        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Grey) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = Secondary,
                        unfocusedContainerColor = Secondary,
                        disabledContainerColor = Secondary
                    )

                )

                Spacer(modifier.height(13.dp))

                OutlinedTextField(
                    value = expirationDate,
                    onValueChange = { expirationDate = it },
                    label = { Text(text=stringResource(R.string.vencimiento),
                        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Grey )},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = Secondary,
                        unfocusedContainerColor = Secondary,
                        disabledContainerColor = Secondary
                    )

                )

                Spacer(modifier.height(13.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text(text=stringResource(R.string.CVV),
                        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Grey ) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = Secondary,
                        unfocusedContainerColor = Secondary,
                        disabledContainerColor = Secondary
                    )

                )

                Spacer(Modifier.height(27.dp))

                Button(
                    onClick = {
                        val nuevaTarjeta = NewCardData(
                            type = detectCardBrandFromNumber(number),
                            number = number,
                            expirationDate = expirationDate,
                            fullName = fullName,
                            cvv = cvv
                        )
                        // falta API
                    },
                    modifier = Modifier.fillMaxWidth().height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Selected,
                        contentColor = White
                    ),
                ) {
                    Text(text=stringResource(R.string.agregar), style = MaterialTheme.typography.titleLarge.copy(
                        color = White,
                        fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgregarTarjetasScreenPreview() {
    WallxTheme(dynamicColor = false) {
        AgregarTarjetaScreen(
            modifier = Modifier
        )
    }
}