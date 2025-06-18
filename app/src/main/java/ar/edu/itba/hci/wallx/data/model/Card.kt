package ar.edu.itba.hci.wallx.data.model

import java.util.Date

data class Card(
    var id: Int,
    var type: String,
    var number: String,
    var expirationDate: Date,
    var fullName: String
) {
}