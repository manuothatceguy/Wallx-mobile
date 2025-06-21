package ar.edu.itba.hci.wallx.data.model

data class Card(
    var id: Int,
    var type: String,
    var number: String,
    var expirationDate: String,
    var fullName: String
) {
}