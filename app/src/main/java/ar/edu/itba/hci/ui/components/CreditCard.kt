package ar.edu.itba.hci.ui.components

import java.util.Date

enum class CreditCardBrand {
    MASTERCARD,
    VISA,
    AMERICAN_EXPRESS,
    MAESTRO,
    OTHER,
    INVALID // Added to represent a number that doesn't match any known format
}

class CreditCard(
    val number: String,
    val cvv: String,
    val holderName: String,
    val expirationDate: Date
) {

    val brand: CreditCardBrand
    val isNumberLengthValid: Boolean
    val isCvvLengthValid: Boolean

    init {
        brand = determineBrand(number)
        isNumberLengthValid = validateNumberLength(number, brand)
        isCvvLengthValid = validateCvvLength(cvv, brand)
    }

    private fun determineBrand(cardNumber: String): CreditCardBrand {
        return when {
            (cardNumber.startsWith("5") || cardNumber.startsWith("2")) && cardNumber.length == 16 -> CreditCardBrand.MASTERCARD
            cardNumber.startsWith("4") && cardNumber.length == 16 -> CreditCardBrand.VISA
            cardNumber.startsWith("3") && cardNumber.length == 15 -> CreditCardBrand.AMERICAN_EXPRESS
            cardNumber.startsWith("6") && cardNumber.length == 19 -> CreditCardBrand.MAESTRO
            cardNumber.length == 19 -> CreditCardBrand.OTHER // Default for 19 digits if not Maestro
            else -> CreditCardBrand.INVALID
        }
    }

    private fun validateNumberLength(cardNumber: String, brand: CreditCardBrand): Boolean {
        return when (brand) {
            CreditCardBrand.MASTERCARD -> cardNumber.length == 16
            CreditCardBrand.VISA -> cardNumber.length == 16
            CreditCardBrand.AMERICAN_EXPRESS -> cardNumber.length == 15
            CreditCardBrand.MAESTRO -> cardNumber.length == 19
            CreditCardBrand.OTHER -> cardNumber.length == 19 // Assuming "Otras" also has 19 digits
            CreditCardBrand.INVALID -> false // If brand couldn't be determined, length is considered invalid
        }
    }

    private fun validateCvvLength(cvv: String, brand: CreditCardBrand): Boolean {
        return when (brand) {
            CreditCardBrand.MASTERCARD, CreditCardBrand.VISA, CreditCardBrand.MAESTRO, CreditCardBrand.OTHER -> cvv.length == 3
            CreditCardBrand.AMERICAN_EXPRESS -> cvv.length == 4
            CreditCardBrand.INVALID -> false // If brand couldn't be determined, CVV length is considered invalid
        }
    }

    fun getCardDetailsValidity(): CardValidationResult {
        val determinedBrand = determineBrand(number)
        val isPanValid = validateNumberLength(number, determinedBrand)
        val isCvvValid = validateCvvLength(cvv, determinedBrand)

        return CardValidationResult(
            brand = determinedBrand,
            isNumberLengthCorrect = isPanValid,
            isCvvLengthCorrect = isCvvValid
        )
    }
}

data class CardValidationResult(
    val brand: CreditCardBrand,
    val isNumberLengthCorrect: Boolean,
    val isCvvLengthCorrect: Boolean
)