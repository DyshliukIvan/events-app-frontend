package com.dyshiuk.eventapp.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EventAppApi {

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("api/auth/refresh")
    suspend fun refresh(
        @Header("X-Refresh-Token") refreshToken: String
    ): AuthResponse

    @GET("api/events")
    suspend fun getEvents(
        @Header("Authorization") authorization: String
    ): EventPageResponse

    @POST("api/auth/google")
    suspend fun googleLogin(
        @Body request: GoogleLoginRequest
    ): AuthResponse
}