package com.dyshiuk.eventapp.network

data class LoginRequest(
    val email: String,
    val password: String,
    val deviceId: String,
    val deviceType: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val deviceId: String,
    val deviceType: String
)

data class AuthResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String
)