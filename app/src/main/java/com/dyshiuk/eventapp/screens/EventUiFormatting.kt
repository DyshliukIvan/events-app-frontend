package com.dyshiuk.eventapp.screens

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

private val eventDateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d · h:mm a", Locale.getDefault())
private val eventEndTimeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())

internal fun formatEventDateRange(start: String, end: String): String {
    val startDate = parseEventDate(start)
    val endDate = parseEventDate(end)

    if (startDate == null) return start
    if (endDate == null) return startDate.format(eventDateFormatter)

    return if (startDate.toLocalDate() == endDate.toLocalDate()) {
        "${startDate.format(eventDateFormatter)} – ${endDate.format(eventEndTimeFormatter)}"
    } else {
        "${startDate.format(eventDateFormatter)} – ${endDate.format(eventDateFormatter)}"
    }
}

internal fun eventDateBadge(value: String): Pair<String, String> {
    val date = parseEventDate(value) ?: return "EVENT" to "•"
    val month = date.format(DateTimeFormatter.ofPattern("MMM", Locale.getDefault())).uppercase()
    return month to date.dayOfMonth.toString()
}

private fun parseEventDate(value: String): LocalDateTime? {
    return try {
        OffsetDateTime.parse(value).toLocalDateTime()
    } catch (_: DateTimeParseException) {
        try {
            LocalDateTime.parse(value)
        } catch (_: DateTimeParseException) {
            null
        }
    }
}

internal fun String.toDisplayLabel(): String =
    lowercase(Locale.getDefault())
        .replace('_', ' ')
        .split(' ')
        .filter { it.isNotBlank() }
        .joinToString(" ") { word -> word.replaceFirstChar { it.titlecase(Locale.getDefault()) } }
        .ifBlank { "Event" }
