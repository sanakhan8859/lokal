package com.example.lokal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lokal.viewmodel.AuthUiState
import com.example.lokal.viewmodel.AuthViewModel


@Composable
fun SessionScreen(
    viewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    val state = viewModel.uiState as? AuthUiState.LoggedIn ?: return

    var duration by remember { mutableStateOf("00:00") }

    LaunchedEffect(Unit) {
        viewModel.startSessionTimer { duration = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome!", style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(32.dp))

        Text("Session started at: ${viewModel.formatStartTime()}")
        Text("Duration: $duration", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(48.dp))

        Button(onClick = {
            viewModel.logout()
            onLogout()
        }) {
            Text("Logout")
        }
    }
}