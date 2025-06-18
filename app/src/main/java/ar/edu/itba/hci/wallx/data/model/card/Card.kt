package ar.edu.itba.hci.wallx.data.model.card

import java.util.Date

data class Card(
    val number: String,
    val cvv: String,
    val holderName: String,
    val expirationDate: Date
) {

    val brand: CardBrand
    val isNumberLengthValid: Boolean
    val isCvvLengthValid: Boolean

    init {
        brand = determineBrand(number)
        isNumberLengthValid = validateNumberLength(number, brand)
        isCvvLengthValid = validateCvvLength(cvv, brand)
    }

    private fun determineBrand(cardNumber: String): CardBrand {
        return when {
            (cardNumber.startsWith("5") || cardNumber.startsWith("2")) && cardNumber.length == 16 -> CardBrand.MASTERCARD
            cardNumber.startsWith("4") && cardNumber.length == 16 -> CardBrand.VISA
            cardNumber.startsWith("3") && cardNumber.length == 15 -> CardBrand.AMERICAN_EXPRESS
            cardNumber.startsWith("6") && cardNumber.length == 19 -> CardBrand.MAESTRO
            cardNumber.length == 19 -> CardBrand.OTHER // Default for 19 digits if not Maestro
            else -> CardBrand.INVALID
        }
    }

    private fun validateNumberLength(cardNumber: String, brand: CardBrand): Boolean {
        return when (brand) {
            CardBrand.MASTERCARD -> cardNumber.length == 16
            CardBrand.VISA -> cardNumber.length == 16
            CardBrand.AMERICAN_EXPRESS -> cardNumber.length == 15
            CardBrand.MAESTRO -> cardNumber.length == 19
            CardBrand.OTHER -> cardNumber.length == 19 // Assuming "Otras" also has 19 digits
            CardBrand.INVALID -> false // If brand couldn't be determined, length is considered invalid
        }
    }

    private fun validateCvvLength(cvv: String, brand: CardBrand): Boolean {
        return when (brand) {
            CardBrand.MASTERCARD, CardBrand.VISA, CardBrand.MAESTRO, CardBrand.OTHER -> cvv.length == 3
            CardBrand.AMERICAN_EXPRESS -> cvv.length == 4
            CardBrand.INVALID -> false // If brand couldn't be determined, CVV length is considered invalid
        }
    }

    fun getCardDetailsValidity(): Boolean {
        val determinedBrand = determineBrand(number)
        val isPanValid = validateNumberLength(number, determinedBrand)
        val isCvvValid = validateCvvLength(cvv, determinedBrand)

        return determinedBrand != CardBrand.INVALID && isPanValid && isCvvValid
    }
}