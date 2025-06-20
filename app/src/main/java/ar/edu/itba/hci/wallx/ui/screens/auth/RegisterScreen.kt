package ar.edu.itba.hci.wallx.ui.screens.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.network.model.user.NewUserData
import ar.edu.itba.hci.wallx.data.repository.UserRepository
import ar.edu.itba.hci.wallx.ui.theme.Background
import ar.edu.itba.hci.wallx.ui.theme.Error
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.Primary
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1
import ar.edu.itba.hci.wallx.ui.theme.SurfaceLight
import ar.edu.itba.hci.wallx.ui.theme.SurfaceVariant
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
import ar.edu.itba.hci.wallx.ui.theme.White
import java.util.Calendar

@SuppressLint("DefaultLocale")
@OptIn(InternalTextApi::class)
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
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    fun isValidEmail(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String) = password.length >= 6
    fun isValidName(name: String) = name.length >= 2
    fun isValidDate(date: String) = date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Error
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = modifier.fillMaxSize().padding(innerPadding)) {
            Row(
                modifier = modifier,
            ) {
                Card(
                    modifier = modifier
                        .padding(horizontal = 120.dp, vertical = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = CardDefaults.cardColors(containerColor = Secondary)
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
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
            Row(modifier = modifier.padding(horizontal = 30.dp, vertical = 10.dp)){
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                        .heightIn(min = 400.dp),
                    colors = CardDefaults.cardColors(containerColor = Secondary)
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
                                .heightIn(min = 400.dp)
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors(containerColor = Primary)
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.registrate),
                                    style = Typography.titleLarge
                                )
                                // Nombre
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.nombre),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = firstName,
                                        onValueChange = { firstName = it },
                                        placeholder = { Text(text = stringResource(R.string.nombre), color = Info) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = SurfaceVariant,
                                            errorTextColor = Error,
                                            focusedContainerColor = Secondary,
                                            unfocusedContainerColor = Background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = Error.copy(alpha = 0.1f),
                                            cursorColor = Secondary,
                                            focusedBorderColor = SecondaryDarken1,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = SurfaceVariant,
                                            errorBorderColor = Error,
                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),
                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        ),
                                        isError = !isValidName(firstName) && firstName.isNotEmpty(),
                                        singleLine = true
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                // Apellido
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.apellido),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = lastName,
                                        onValueChange = { lastName = it },
                                        placeholder = { Text(text = stringResource(R.string.apellido), color = Info) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = SurfaceVariant,
                                            errorTextColor = Error,
                                            focusedContainerColor = Secondary,
                                            unfocusedContainerColor = Background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = Error.copy(alpha = 0.1f),
                                            cursorColor = Secondary,
                                            focusedBorderColor = SecondaryDarken1,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = SurfaceVariant,
                                            errorBorderColor = Error,
                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),
                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        ),
                                        isError = !isValidName(lastName) && lastName.isNotEmpty(),
                                        singleLine = true
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                // Email
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.email),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = email,
                                        onValueChange = { email = it },
                                        placeholder = { Text(text = stringResource(R.string.email), color = Info) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = SurfaceVariant,
                                            errorTextColor = Error,
                                            focusedContainerColor = Secondary,
                                            unfocusedContainerColor = Background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = Error.copy(alpha = 0.1f),
                                            cursorColor = Secondary,
                                            focusedBorderColor = SecondaryDarken1,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = SurfaceVariant,
                                            errorBorderColor = Error,
                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),
                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        ),
                                        isError = !isValidEmail(email) && email.isNotEmpty(),
                                        singleLine = true
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                // Contraseña
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.password),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = password,
                                        onValueChange = { password = it },
                                        placeholder = { Text(text = stringResource(R.string.password), color = Info) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = SurfaceVariant,
                                            errorTextColor = Error,
                                            focusedContainerColor = Secondary,
                                            unfocusedContainerColor = Background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = Error.copy(alpha = 0.1f),
                                            cursorColor = Secondary,
                                            focusedBorderColor = SecondaryDarken1,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = SurfaceVariant,
                                            errorBorderColor = Error,
                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),
                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        ),
                                        isError = !isValidPassword(password) && password.isNotEmpty(),
                                        singleLine = true
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                // Fecha de nacimiento
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
                                        placeholder = { Text(stringResource(R.string.fecha_nacimiento_placeholder), color = Info) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = SurfaceVariant,
                                            errorTextColor = Error,
                                            focusedContainerColor = Secondary,
                                            unfocusedContainerColor = Background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = Error.copy(alpha = 0.1f),
                                            cursorColor = Secondary,
                                            focusedBorderColor = SecondaryDarken1,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = SurfaceVariant,
                                            errorBorderColor = Error,
                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),
                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        ),
                                        isError = birthDate.isNotEmpty() && !isValidDate(birthDate),
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                val datePicker = DatePickerDialog(
                                                    context,
                                                    { _, year, month, dayOfMonth ->
                                                        birthDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                                                    },
                                                    calendar.get(Calendar.YEAR),
                                                    calendar.get(Calendar.MONTH),
                                                    calendar.get(Calendar.DAY_OF_MONTH)
                                                )
                                                datePicker.show()
                                            }
                                    )
                                    if (birthDate.isNotEmpty() && !isValidDate(birthDate)) {
                                        Text(stringResource(R.string.formato_fecha_invalido), color = Error)
                                    }
                                }
                                Button(
                                    onClick = {
                                        if (!isValidName(firstName) || !isValidName(lastName) || !isValidEmail(email) || !isValidPassword(password) || !isValidDate(birthDate)) {
                                            errorMessage = context.getString(R.string.error_completar_campos)
                                            return@Button
                                        }
                                        viewModel.register(
                                                firstName = firstName,
                                                lastName = lastName,
                                                email = email,
                                                password = password,
                                                birthDate = birthDate

                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = White)
                                ){
                                    Box(){
                                        Text(text = stringResource(R.string.registrarse))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (errorMessage != null) {
                LaunchedEffect(errorMessage) {
                    snackbarHostState.showSnackbar(errorMessage!!)
                    errorMessage = null
                }
            }
        }
    }
}



