package ar.edu.itba.hci.wallx.ui.screens.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.Primary
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.White
import java.util.Calendar

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: WallXViewModel,
    onRegisterSuccess: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    fun isValidEmail(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String) = password.length >= 6
    fun isValidName(name: String) = name.length >= 2
    fun isValidDate(date: String) = date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))



    Scaffold { innerPadding ->
        Column(modifier = modifier.fillMaxSize().padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = modifier.align(Alignment.CenterHorizontally)) {
                Card(
                    modifier = modifier
                        .padding(horizontal = 120.dp, vertical = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.wallx),
                            modifier = Modifier.padding(16.dp),
                            style = Typography.titleLarge
                        )
                    }
                }
            }

            Row(modifier = modifier.padding( vertical = 10.dp)) {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                        .heightIn(min = 400.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .wrapContentHeight(),
                            colors = CardDefaults.cardColors(containerColor = Primary)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.registrate),
                                    style = Typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.nombre),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = firstName,
                                        onValueChange = { firstName = it },
                                        placeholder = {
                                            Text(text = stringResource(R.string.nombre), color = Info)
                                        },
                                        colors = registerFieldColors(),
                                        isError = !isValidName(firstName) && firstName.isNotEmpty(),
                                        singleLine = true
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.apellido),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = lastName,
                                        onValueChange = { lastName = it },
                                        placeholder = {
                                            Text(text = stringResource(R.string.apellido), color = Info)
                                        },
                                        colors = registerFieldColors(),
                                        isError = !isValidName(lastName) && lastName.isNotEmpty(),
                                        singleLine = true
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.email),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = email,
                                        onValueChange = { email = it },
                                        placeholder = {
                                            Text(text = stringResource(R.string.email), color = Info)
                                        },
                                        colors = registerFieldColors(),
                                        isError = !isValidEmail(email) && email.isNotEmpty(),
                                        singleLine = true
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.password),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = password,
                                        onValueChange = { password = it },
                                        placeholder = {
                                            Text(text = stringResource(R.string.password), color = Info)
                                        },
                                        colors = registerFieldColors(),
                                        isError = !isValidPassword(password) && password.isNotEmpty(),
                                        singleLine = true
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.fecha_nacimiento),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    val calendar = remember { Calendar.getInstance() }
                                    OutlinedTextField(
                                        value = birthDate,
                                        onValueChange = { birthDate = it },
                                        placeholder = {
                                            Text(stringResource(R.string.fecha_nacimiento_placeholder), color = Info)
                                        },
                                        colors = registerFieldColors(),
                                        isError = birthDate.isNotEmpty() && !isValidDate(birthDate),
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                val datePicker = DatePickerDialog(
                                                    context,
                                                    { _, year, month, day ->
                                                        birthDate = String.format("%04d-%02d-%02d", year, month + 1, day)
                                                    },
                                                    calendar.get(Calendar.YEAR),
                                                    calendar.get(Calendar.MONTH),
                                                    calendar.get(Calendar.DAY_OF_MONTH)
                                                )
                                                datePicker.show()
                                            }
                                    )
                                    if (birthDate.isNotEmpty() && !isValidDate(birthDate)) {
                                        Text(stringResource(R.string.formato_fecha_invalido), color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.register(
                                    firstName = firstName,
                                    lastName = lastName,
                                    email = email,
                                    password = password,
                                    birthDate = birthDate
                                )
                                if(uiState.error == null){
                                    onRegisterSuccess()
                                }
                                firstName = ""
                                lastName = ""
                                email = ""
                                password = ""
                                birthDate = ""
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = White
                            )
                        ) {
                            Text(text = stringResource(R.string.registrarse))
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun registerFieldColors() = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
        disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
        errorTextColor = MaterialTheme.colorScheme.error,
        errorContainerColor = MaterialTheme.colorScheme.error.copy(
            alpha = 0.1f
        ),

        cursorColor = Info,

        focusedBorderColor = Info,
        unfocusedBorderColor = Info.copy(0.8f),
        disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
        errorBorderColor = MaterialTheme.colorScheme.error,

        focusedLeadingIconColor = SecondaryDarken1,
        unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),

        focusedTrailingIconColor = SecondaryDarken1,
        unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
    )



