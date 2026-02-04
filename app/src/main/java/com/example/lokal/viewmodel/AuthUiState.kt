package com.example.lokal.viewmodel


sealed interface AuthUiState {
    data object Idle : AuthUiState
    data class EmailInput(val email: String = "") : AuthUiState
    data class OtpInput(
        val email: String,
        val timeLeftSeconds: Int = 60,
        val attemptsLeft: Int = 3,
        val error: String? = null
    ) : AuthUiState
    data class LoggedIn(
        val startTime: Long,
        val email: String
    ) : AuthUiState
}