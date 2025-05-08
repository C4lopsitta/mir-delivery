package dev.robaldo.mir_delivery.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String,
    val httpStatus: Int = 200,
    val details: String? = null,
    @Contextual
    val exception: Exception? = null
)
