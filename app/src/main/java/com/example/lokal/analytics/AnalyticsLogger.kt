package com.example.lokal.analytics

import com.google.firebase.analytics.logEvent



import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class AnalyticsLogger(context: Context) {
    private val analytics: FirebaseAnalytics = Firebase.analytics

    fun logOtpGenerated(email: String) {
        analytics.logEvent("otp_generated") {
            param("email_domain", email.substringAfter("@"))
        }
    }

    fun logOtpValidationSuccess() {
        analytics.logEvent("otp_validation_success", null)
    }

    fun logOtpValidationFailure(reason: String) {
        analytics.logEvent("otp_validation_failure") {
            param("failure_reason", reason)
        }
    }

    fun logLogout() {
        analytics.logEvent("user_logout", null)
    }
}