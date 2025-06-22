package ar.edu.itba.hci.wallx.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id : Int,
    var firstName : String,
    var lastName : String
)
