package com.dyshiuk.eventapp.network

data class EventDto(
    val id: Long,
    val title: String,
    val description: String?,
    val startDate: String,
    val endDate: String,
    val location: String?,
    val capacity: Int?,
    val type: String,
    val status: String,
    val creatorId: Long?,
    val creatorName: String?,
    val organizationId: Long?,
    val organizationName: String?,
    val registrationCount: Long?,
    val followCount: Long?,
    val createdAt: String?,
    val updatedAt: String?
)

data class EventPageResponse(
    val content: List<EventDto>,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int
)