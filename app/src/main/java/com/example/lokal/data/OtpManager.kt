package com.example.lokal.data


import kotlin.random.Random

data class OtpData(
    val otp: String,
    val generatedAt: Long = System.currentTimeMillis(),
    var attempts: Int = 0
)

class OtpManager {
    private val otpStore = mutableMapOf<String, OtpData>()

    fun generateOtp(email: String): String {
        val otp = (100000..999999).random().toString()
        otpStore[email] = OtpData(otp = otp)
        return otp
    }

    fun validateOtp(email: String, inputOtp: String): Result<Unit> {
        val data = otpStore[email] ?: return Result.failure(Exception("No OTP found"))

        val now = System.currentTimeMillis()
        if (now - data.generatedAt > 60_000) {
            otpStore.remove(email)
            return Result.failure(Exception("OTP expired"))
        }

        if (data.attempts >= 3) {
            return Result.failure(Exception("Max attempts exceeded"))
        }

        data.attempts++

        return if (inputOtp == data.otp) {
            otpStore.remove(email)   // success → clear
            Result.success(Unit)
        } else {
            Result.failure(Exception("Incorrect OTP"))
        }
    }

    fun invalidateOtp(email: String) {
        otpStore.remove(email)
    }

    fun resetAttempts(email: String) {
        otpStore[email]?.attempts = 0
    }

    fun hasOtp(email: String): Boolean = otpStore.containsKey(email)
}