package com.example.lokal.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lokal.analytics.AnalyticsLogger
import com.example.lokal.data.OtpManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthViewModel(
    private val otpManager: OtpManager = OtpManager(),
    private val analytics: AnalyticsLogger
) : ViewModel() {

    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    private var timerJob: Job? = null
    private var sessionStartTime by mutableLongStateOf(0L)

    fun onEmailChanged(newEmail: String) {
        if (uiState is AuthUiState.EmailInput || uiState is AuthUiState.Idle) {
            uiState = AuthUiState.EmailInput(newEmail.trim())
        }
    }

    fun sendOtp(email: String) {
        if (email.isBlank() || !email.contains("@")) {
            // show error in UI
            return
        }

        val generated = otpManager.generateOtp(email)
        analytics.logOtpGenerated(email)

        uiState = AuthUiState.OtpInput(
            email = email,
            timeLeftSeconds = 60,
            attemptsLeft = 3,
            error = "DEBUG OTP: $generated"
        )

        startOtpCountdown(email)
    }

    private fun startOtpCountdown(email: String) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var seconds = 60
            while (seconds > 0 && isActive) {
                delay(1000L)
                seconds--
                if (uiState is AuthUiState.OtpInput) {
                    uiState = (uiState as AuthUiState.OtpInput).copy(timeLeftSeconds = seconds)
                }
            }


            if (uiState is AuthUiState.OtpInput) {
                uiState = (uiState as AuthUiState.OtpInput).copy(
                    error = "OTP expired. Please resend.",
                    timeLeftSeconds = 0
                )
                otpManager.invalidateOtp(email)
            }
        }
    }

    fun verifyOtp(inputOtp: String) {
        val current = uiState
        if (current !is AuthUiState.OtpInput) return

        val result = otpManager.validateOtp(current.email, inputOtp)

        if (result.isSuccess) {
            analytics.logOtpValidationSuccess()
            sessionStartTime = System.currentTimeMillis()
            uiState = AuthUiState.LoggedIn(
                startTime = sessionStartTime,
                email = current.email
            )
        } else {
            val reason = result.exceptionOrNull()?.message ?: "Unknown error"
            analytics.logOtpValidationFailure(reason)

            val newAttempts = (current.attemptsLeft - 1).coerceAtLeast(0)
            val errorMsg = when {
                reason.contains("expired") -> "OTP expired"
                reason.contains("Max") -> "Max attempts reached. Resend OTP."
                else -> "Incorrect OTP ($newAttempts attempts left)"
            }

            uiState = current.copy(
                attemptsLeft = newAttempts,
                error = errorMsg
            )

            if (newAttempts == 0) {
                timerJob?.cancel()
            }
        }
    }

    fun resendOtp() {
        val current = uiState
        if (current !is AuthUiState.OtpInput) return

        otpManager.invalidateOtp(current.email)
        sendOtp(current.email)   // re-generates + resets attempts internally
    }

    fun logout() {
        analytics.logLogout()
        timerJob?.cancel()
        uiState = AuthUiState.EmailInput()
    }

    // Session timer (live duration)
    fun startSessionTimer(onTick: (String) -> Unit) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                val durationMs = System.currentTimeMillis() - sessionStartTime
                val minutes = durationMs / 60000
                val seconds = (durationMs % 60000) / 1000
                onTick(String.format("%02d:%02d", minutes, seconds))
                delay(1000L)
            }
        }
    }

    fun formatStartTime(): String {
        return SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            .format(Date(sessionStartTime))
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}