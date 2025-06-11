package ar.edu.itba.hci.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DashboardScreen(modifier: Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Dashboard")
    }
}

@Composable
@Preview(showBackground = true)
fun DashboardScreenPreview() {
    DashboardScreen(modifier = Modifier)
}