package com.dyshiuk.eventapp.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

class GoogleSignInHelper(
    private val context: Context
) {
    suspend fun signIn(webClientId: String): String {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(webClientId)
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = try {
            credentialManager.getCredential(
                context = context,
                request = request
            )
        } catch (exception: NoCredentialException) {
            throw IllegalStateException("No Google account is available for sign-in", exception)
        }

        val credential = result.credential

        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                return googleIdTokenCredential.idToken
            } catch (e: GoogleIdTokenParsingException) {
                throw IllegalStateException("Invalid Google token response", e)
            }
        } else {
            throw IllegalStateException("Unexpected credential type")
        }
    }
}
