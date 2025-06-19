package ar.edu.itba.hci.wallx.data.model

import java.util.Date

data class CompleteUser (
    var id: Int?,
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: Date?
)