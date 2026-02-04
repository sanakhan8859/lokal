package com.example.lokal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lokal.viewmodel.AuthUiState
import com.example.lokal.viewmodel.AuthViewModel

@Composable
fun OtpScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    val state = viewModel.uiState as? AuthUiState.OtpInput ?: return

    var otpInput by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state.timeLeftSeconds <= 0 && state.error == null) {
            // OTP expired — message already handled in state
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter OTP sent to ${state.email}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Time left: ${state.timeLeftSeconds}s",
            color = if (state.timeLeftSeconds < 10)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = otpInput,
            onValueChange = { if (it.length <= 6) otpInput = it },
            label = { Text("6-digit OTP") },
            singleLine = true
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            viewModel.verifyOtp(otpInput)
            if (viewModel.uiState is AuthUiState.LoggedIn) {
                onLoginSuccess()
            }
        }) {
            Text("Verify")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { viewModel.resendOtp() }) {
            Text("Resend OTP")
        }
    }
}

