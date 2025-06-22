package ar.edu.itba.hci.wallx.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.components.*
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    wallXViewModel: WallXViewModel,
    onNavigateTo: (String) -> Unit
) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold{ innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WallXVerticalSpacer(80.dp)

            // App Logo/Title Card
            WallXElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    WallXTitle(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            WallXVerticalSpacer(32.dp)

            // Login Form Card
            WallXElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    WallXHeadline(
                        text = stringResource(R.string.inicio_de_sesión),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    WallXVerticalSpacer(24.dp)

                    // Email Field
                    WallXOutlinedTextField(
                        value = user,
                        onValueChange = { user = it },
                        label = stringResource(R.string.email),
                        placeholder = stringResource(R.string.ingrese_su_email),
                        leadingIcon = Icons.Default.Email,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None
                    )

                    WallXVerticalSpacer(16.dp)

                    // Password Field
                    WallXOutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = stringResource(R.string.contraseña),
                        placeholder = stringResource(R.string.ingrese_su_contraseña),
                        leadingIcon = Icons.Default.Lock,
                        trailingIcon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    WallXVerticalSpacer(24.dp)

                    // Login Button
                    WallXButton(
                        onClick = {
                            wallXViewModel.login(user, password)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.inicio_sesion),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    WallXVerticalSpacer(16.dp)

                    // Register Link
                    WallXTextButton(
                        onClick = { onNavigateTo(AppDestinations.REGISTRO.route) },
                        text = stringResource(R.string.no_tenés_una_cuenta),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            WallXVerticalSpacer(32.dp)

        }
    }
}


