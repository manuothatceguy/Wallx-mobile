package ar.edu.itba.hci.wallx.ui.screens.tarjetas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import ar.edu.itba.hci.wallx.ui.components.detectCardBrandFromNumber
import ar.edu.itba.hci.wallx.ui.components.getBrandLogo
import ar.edu.itba.hci.wallx.ui.components.getCardColor
import ar.edu.itba.hci.wallx.ui.theme.Black
import ar.edu.itba.hci.wallx.ui.theme.Grey
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.White
import java.util.Calendar
import java.util.Locale

enum class CardType {
    CREDIT {
        override fun toString() : String {
            return "CREDIT"
        }
    },
    DEBIT {
        override fun toString() : String {
            return "DEBIT"
        }
    };
}

@Composable
fun CreditCardPreview(
    modifier: Modifier = Modifier,
    number: String,
    fullName: String,
    expirationDate: String,
    brand: String,
    cvv: String,
    mostrarDorso: Boolean,
) {
    val brandLower = brand.lowercase(Locale.getDefault())
    val backgroundColor = getCardColor(brandLower)
    val logo = getBrandLogo(brandLower)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.586f)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        if (!mostrarDorso) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(65.dp)
                            .padding(end = 10.dp)
                    )
                }
                Spacer(Modifier.height(13.dp))

                Text(
                    text = if (number.isNotBlank()) number.chunked(4).joinToString(" ") else "•••• •••• •••• ••••",
                    color = White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = if (fullName.isNotBlank()) fullName.uppercase() else "NOMBRE Y APELLIDO",
                        color = White, style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (expirationDate.isNotBlank()) expirationDate else "MM/AA",
                        color = White, style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color.Black, RoundedCornerShape(7.dp))
                )
                Spacer(Modifier.height(29.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Box(
                        Modifier
                            .width(80.dp)
                            .height(28.dp)
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .padding(end = 10.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = if (cvv.isNotBlank()) cvv else "CVV",
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    Modifier.graphicsLayer {
                                        scaleX = -1f
                                    }
                                )
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarTarjetaScreen(modifier: Modifier = Modifier, viewModel: WallXViewModel, onSuccess: () -> Unit) {
    var number by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardType by remember { mutableStateOf(CardType.CREDIT) }
    var cvvFocused by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
    val years = (currentYear..currentYear + 10).toList()
    val months = (1..12).map { it.toString().padStart(2, '0') }
    var selectedMonth by remember { mutableStateOf(months.first()) }
    var selectedYear by remember { mutableStateOf(years.first().toString()) }

    val brand = detectCardBrandFromNumber(number)

    data class CardInputRules(val panLength: Int, val cvvLength: Int)
    fun getCardInputRules(brand: String): CardInputRules = when (brand.lowercase()) {
        "mastercard" -> CardInputRules(16, 3)
        "visa" -> CardInputRules(16, 3)
        "amex" -> CardInputRules(15, 4)
        "maestro" -> CardInputRules(19, 3)
        else -> CardInputRules(19, 3)
    }
    val rules = getCardInputRules(brand)

    val rotationY by animateFloatAsState(
        targetValue = if (cvvFocused) 180f else 0f,
        label = "card-rotation"
    )
    val density = LocalDensity.current.density
    val scrollState = rememberScrollState()

    var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }
    var cardDetailsForDialog by remember { mutableStateOf<NewCardData?>(null) }

    if (showConfirmationDialog && cardDetailsForDialog != null) {
        val details = cardDetailsForDialog!! // Sabemos que no es null por la condición

        AlertDialog(
            onDismissRequest = {
                showConfirmationDialog = false
                number = ""
                fullName = ""
                cvv = ""
                selectedMonth = months.first()
                selectedYear = years.first().toString()
            },
            title = {
                Text(stringResource(R.string.confirmar_tarjeta))
            },
            text = {
                Column {
                    Text(stringResource(R.string.confirmar_tarjeta_detalle))
                    Spacer(Modifier.height(8.dp))

                    Text(stringResource(R.string.tipo) + ": ${details.type}")
                    Text(stringResource(R.string.numero) + ": ${(details.number)?.takeLast(4)}")
                    Text(stringResource(R.string.nombre) + ": ${details.fullName}")
                    Text(stringResource(R.string.vencimiento) + ": ${details.expirationDate}")
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            textContentColor = MaterialTheme.colorScheme.onBackground,
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmationDialog = false
                        viewModel.addCard(details)
                        number = ""
                        fullName = ""
                        cvv = ""
                        selectedMonth = months.first()
                        selectedYear = years.first().toString()
                        onSuccess()
                    }
                ) {
                    Text(stringResource(R.string.confirmar))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showConfirmationDialog = false

                    }
                ) {
                    Text(stringResource(R.string.cancelar))
                }
            }

        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(horizontal = 32.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        cameraDistance = 12 * density
                        if (rotationY > 90f) scaleX = -1f
                    }
                    .padding(bottom = 18.dp)
            ) {
                CreditCardPreview(
                    number = number,
                    fullName = fullName,
                    expirationDate = "$selectedMonth/$selectedYear",
                    brand = brand,
                    cvv = cvv,
                    mostrarDorso = rotationY > 90f
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.agregarTarjeta),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = {
                            Text(
                                text = stringResource(R.string.nombre),
                                style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedLabelColor = Black,
                            unfocusedLabelColor = Grey,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary
                        )
                    )

                    Spacer(Modifier.height(13.dp))

                    OutlinedTextField(
                        value = number,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            number = digits.take(rules.panLength)
                        },
                        label = {
                            Text(
                                text = "${stringResource(R.string.numero)} (${number.length}/${rules.panLength})",
                                style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedLabelColor = Grey,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary
                        )
                    )

                    Spacer(Modifier.height(13.dp))


                    Row(Modifier.fillMaxWidth()) {
                        var expandedMonth by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = expandedMonth,
                            onExpandedChange = { expandedMonth = !expandedMonth },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        ) {
                            OutlinedTextField(
                                value = selectedMonth,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(stringResource(R.string.mes)) },
                                modifier = Modifier.menuAnchor(),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMonth) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                                    disabledContainerColor = MaterialTheme.colorScheme.secondary
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedMonth,
                                onDismissRequest = { expandedMonth = false }
                            ) {
                                months.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            selectedMonth = selectionOption
                                            expandedMonth = false
                                        }
                                    )
                                }
                            }
                        }

                        var expandedYear by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = expandedYear,
                            onExpandedChange = { expandedYear = !expandedYear },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        ) {
                            OutlinedTextField(
                                value = selectedYear,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(stringResource(R.string.año)) },
                                modifier = Modifier.menuAnchor(),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedYear) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                                    disabledContainerColor = MaterialTheme.colorScheme.secondary
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedYear,
                                onDismissRequest = { expandedYear = false }
                            ) {
                                years.forEach { yearOption ->
                                    DropdownMenuItem(
                                        text = { Text(yearOption.toString()) },
                                        onClick = {
                                            selectedYear = yearOption.toString()
                                            expandedYear = false
                                        }
                                    )
                                }
                            }
                        }
                    }


                    Spacer(Modifier.height(13.dp))

                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            cvv = digits.take(rules.cvvLength)
                        },
                        label = {
                            Text(
                                text = "CVV (${cvv.length}/${rules.cvvLength})",
                                style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState -> cvvFocused = focusState.isFocused },
                        colors = TextFieldDefaults.colors(
                            focusedLabelColor = Black,
                            unfocusedLabelColor = Grey,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary
                        )
                    )

                    Spacer(Modifier.height(18.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        RadioButton(
                            selected = cardType == CardType.CREDIT,
                            onClick = { cardType = CardType.CREDIT }
                        )
                        Text(text = "Crédito", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(
                            selected = cardType == CardType.DEBIT,
                            onClick = { cardType = CardType.DEBIT }
                        )
                        Text(text = "Débito", style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val nuevaTarjeta = NewCardData(
                                type = cardType.toString(),
                                number = number,
                                expirationDate = "$selectedMonth/$selectedYear",
                                fullName = fullName,
                                cvv = cvv
                            )
                            cardDetailsForDialog = nuevaTarjeta
                            showConfirmationDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = White
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.agregar),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}