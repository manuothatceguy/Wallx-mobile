package ar.edu.itba.hci.wallx.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleCard(
    var id: Int? = null,
    var number: String? = null
)
