package com.dyshiuk.eventapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.dyshiuk.eventapp.auth.GoogleSignInHelper
import com.dyshiuk.eventapp.auth.TokenStorage
import com.dyshiuk.eventapp.navigation.AppNavigation
import com.dyshiuk.eventapp.network.EventDto
import com.dyshiuk.eventapp.network.GoogleLoginRequest
import com.dyshiuk.eventapp.network.LoginRequest
import com.dyshiuk.eventapp.network.RetrofitClient
import com.dyshiuk.eventapp.screens.ErrorScreen
import com.dyshiuk.eventapp.screens.LoadingScreen
import com.dyshiuk.eventapp.ui.theme.EventAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var tokenStorage: TokenStorage
    private lateinit var googleSignInHelper: GoogleSignInHelper

    private var isLoggedIn by mutableStateOf(false)
    private var isLoading by mutableStateOf(false)
    private var errorMessage by mutableStateOf<String?>(null)
    private var events by mutableStateOf<List<EventDto>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenStorage = TokenStorage(this)
        googleSignInHelper = GoogleSignInHelper(this)

        setContent {
            EventAppTheme {
                when {
                    isLoading -> {
                        LoadingScreen()
                    }

                    errorMessage != null -> {
                        ErrorScreen(
                            message = errorMessage!!,
                            onRetryClick = {
                                errorMessage = null
                                if (tokenStorage.getToken() != null) {
                                    tryAutoLogin()
                                } else {
                                    performGoogleLogin()
                                }
                            }
                        )
                    }

                    else -> {
                        AppNavigation(
                            isLoggedIn = isLoggedIn,
                            events = events,
                            onGoogleLoginClick = { performGoogleLogin() },
                            onEmailLoginClick = { email, password ->
                                performEmailLogin(email, password)
                            },
                            onLogoutClick = { logout() }
                        )
                    }
                }
            }
        }

        tryAutoLogin()
    }

    private fun tryAutoLogin() {
        val token = tokenStorage.getToken() ?: return

        isLoading = true
        errorMessage = null

        lifecycleScope.launch {
            try {
                val eventPage = RetrofitClient.api.getEvents(
                    authorization = "Bearer $token"
                )

                events = eventPage.content
                isLoggedIn = true
            } catch (e: Exception) {
                tokenStorage.clearToken()
                isLoggedIn = false
                errorMessage = "Auto login failed: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun performGoogleLogin() {
        isLoading = true
        errorMessage = null

        lifecycleScope.launch {
            try {
                val googleIdToken = googleSignInHelper.signIn(
                    webClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID
                )

                val loginResponse = RetrofitClient.api.googleLogin(
                    GoogleLoginRequest(idToken = googleIdToken)
                )

                tokenStorage.saveToken(loginResponse.accessToken)

                val eventPage = RetrofitClient.api.getEvents(
                    authorization = "Bearer ${loginResponse.accessToken}"
                )

                events = eventPage.content
                isLoggedIn = true

            } catch (e: Exception) {
                errorMessage = "Google login failed: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun performEmailLogin(email: String, password: String) {
        isLoading = true
        errorMessage = null

        lifecycleScope.launch {
            try {
                val loginResponse = RetrofitClient.api.login(
                    LoginRequest(
                        email = email,
                        password = password,
                        deviceId = "android-client",
                        deviceType = "android"
                    )
                )

                tokenStorage.saveToken(loginResponse.accessToken)
                val eventPage = RetrofitClient.api.getEvents(
                    authorization = "Bearer ${loginResponse.accessToken}"
                )

                events = eventPage.content
                isLoggedIn = true
            } catch (e: Exception) {
                errorMessage = "Sign in failed: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun logout() {
        tokenStorage.clearToken()
        isLoggedIn = false
        events = emptyList()
        errorMessage = null
    }
}
