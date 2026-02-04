package com.example.lokal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lokal.analytics.AnalyticsLogger
import com.example.lokal.ui.*
import com.example.lokal.viewmodel.AuthViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {


    private lateinit var analyticsLogger: AnalyticsLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        analyticsLogger = AnalyticsLogger(this)

        setContent {
            val viewModel: AuthViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return AuthViewModel(
                            analytics = analyticsLogger
                        ) as T
                    }
                }
            )

            var screen by rememberSaveable { mutableStateOf("login") }

            MaterialTheme {
                when (screen) {
                    "login" -> LoginScreen(viewModel) {
                        screen = "otp"
                    }

                    "otp" -> OtpScreen(viewModel) {
                        screen = "session"
                    }

                    "session" -> SessionScreen(viewModel) {
                        screen = "login"
                    }
                }
            }
        }
    }
}
