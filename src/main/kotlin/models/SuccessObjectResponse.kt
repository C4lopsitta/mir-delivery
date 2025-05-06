package dev.robaldo.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessObjectResponse<T>(
    val message: String,
    @SerialName("object")
    val item: T,
    val httpStatus: Int = 201,
    val success: Boolean = true
)
