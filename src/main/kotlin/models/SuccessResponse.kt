package dev.robaldo.mir_delivery.models

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse (
    val message: String,
    val success: Boolean = true,
    val httpStatus: Int = 200
)