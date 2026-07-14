# EventApp Android

An Android client for browsing events and signing in with Google. The backend is maintained in a separate project and must be accessible through its REST API.

## Requirements

- Android Studio with Embedded JDK 21
- Android SDK 36.1
- An emulator with Google Play or a physical Android device
- A running EventApp backend

## Running the app

1. Open the repository root in Android Studio.
2. Select `Gradle JDK: Embedded JDK`.
3. Run `Sync Project with Gradle Files`.
4. Run the `app` configuration on an emulator or physical device.

By default, the app connects to `http://10.0.2.2:8080/`. From the Android Emulator, this address maps to the host computer's `localhost`.

## Configuration

You can override these values in your user-level `~/.gradle/gradle.properties` file or the local `gradle.properties` file:

```properties
EVENTAPP_API_BASE_URL=http://10.0.2.2:8080/
GOOGLE_WEB_CLIENT_ID=your-web-client-id.apps.googleusercontent.com
```

For a physical device, set the backend URL to an address accessible from the local network, such as `http://192.168.1.10:8080/`.

Google Sign-In requires OAuth configuration for the `com.dyshiuk.eventapp` application ID and the SHA-1 fingerprint of the signing certificate.

## Verification

```powershell
$env:JAVA_HOME="E:\AndroidStudio\jbr"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\gradlew.bat test assembleDebug
```

The debug APK is generated in `app/build/outputs/apk/debug/`.
