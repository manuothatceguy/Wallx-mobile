package ar.edu.itba.hci.wallx.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkError(
    val message: String
)