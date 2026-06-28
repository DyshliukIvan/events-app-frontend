# EventApp Android

Android client for EventApp, a mobile event discovery application. The app signs users in with Google, stores a JWT access token, and loads events from the Spring Boot backend.

## Tech Stack

- Kotlin
- Jetpack Compose
- Android Navigation Compose
- Retrofit + Gson
- Android Credential Manager
- Google ID sign-in

## Backend

The app expects the backend API to run locally at:

```text
http://10.0.2.2:8080/
```

`10.0.2.2` maps the Android emulator to `localhost` on the host machine.

Backend repository:

```text
https://github.com/DyshliukIvan/events-app-backend
```

## Main Flow

```text
Login with Google
  -> POST /api/auth/google
  -> receive accessToken
  -> GET /api/events
  -> show event list and event details
```

## Run

Open this project in Android Studio and run the `app` configuration on an emulator.

For local API calls, start the backend first.
