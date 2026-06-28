package com.dyshiuk.eventapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dyshiuk.eventapp.network.EventDto
import com.dyshiuk.eventapp.screens.EventDetailsScreen
import com.dyshiuk.eventapp.screens.EventListScreen
import com.dyshiuk.eventapp.screens.HomeScreen
import com.dyshiuk.eventapp.screens.LoginScreen

@Composable
fun AppNavigation(
    isLoggedIn: Boolean,
    events: List<EventDto>,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val navController = rememberNavController()
    val startDestination = if (isLoggedIn) "home" else "login"

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                onLoginClick = onLoginClick
            )
        }

        composable("home") {
            HomeScreen(
                onLogoutClick = {
                    onLogoutClick()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onEventsClick = {
                    navController.navigate("events")
                }
            )
        }

        composable("events") {
            EventListScreen(
                events = events,
                onEventClick = { eventId ->
                    navController.navigate("event_details/$eventId")
                }
            )
        }

        composable(
            route = "event_details/{eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getLong("eventId") ?: 0L
            val selectedEvent = events.find { it.id == eventId }

            EventDetailsScreen(
                event = selectedEvent,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
