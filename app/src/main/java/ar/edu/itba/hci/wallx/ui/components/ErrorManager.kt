package ar.edu.itba.hci.wallx.ui.components

import androidx.compose.runtime.Composable
import ar.edu.itba.hci.wallx.R

@Composable
fun errorManager(error : String) : Int {
    return when (error) {
        "Missing cvu." -> R.string.error_cvu_missing
        "Invalid cvu." -> R.string.error_cvu_invalid
        "Missing alias." -> R.string.error_alias_missing
        "Invalid alias." -> R.string.error_alias_invalid
        "Missing card number." -> R.string.error_card_number_missing
        "Invalid card number. It must be 15, 16, or 19 digits." -> R.string.error_card_number_invalid
        "Missing CVV." -> R.string.error_card_cvv_missing
        "Invalid CVV. It must be 3 or 4 digits." -> R.string.error_card_cvv_invalid
        "Missing expiration date." -> R.string.error_card_date_missing
        "Invalid expiration date format. Valid format MM/YY." -> R.string.error_card_date_format
        "Card expired." -> R.string.error_card_expired
        "Missing full name." -> R.string.error_card_name_missing
        "Missing card type" -> R.string.error_card_type_missing
        "Invalid card type. Must be 'CREDIT' or 'DEBIT'." -> R.string.error_card_type_invalid
        "Invalid email. Valid format example@domain.com." -> R.string.error_email
        "Missing amount." -> R.string.error_amount_missing
        "Amount must be a number." -> R.string.error_amount_nan
        "Amount must be a positive number." -> R.string.error_amount_neg
        "Missing description." -> R.string.error_desc_missing
        "Missing page." -> R.string.error_page_missing
        "Page must be a number." -> R.string.error_page_nan
        "Page must be a positive number." -> R.string.error_page_neg
        "Missing page size." -> R.string.error_page_size_missing
        "Page size must be a number." -> R.string.error_page_size_nan
        "Page size must be a positive number." -> R.string.error_page_size_neg
        "Missing sort direction." -> R.string.error_sort_direction_missing
        "Invalid sort direction. Valid values are 'ASC' or 'DESC'." -> R.string.error_sort_direction_invalid
        "Missing code." -> R.string.error_code_missing
        "Missing token." -> R.string.error_token_missing
        "Missing password." -> R.string.error_password_missing
        "Invalid date format. Valid format YYYY-MM-DD." -> R.string.error_date_format
        "Invalid date." -> R.string.error_date_invalid
        "Missing email." -> R.string.error_email_missing
        "Year must be between 1900 and current year." -> R.string.error_date_year
        "User must be at least 13 years old." -> R.string.error_date_13
        "Password must be at least 6 characters long." -> R.string.error_password_length
        "Missing first name." -> R.string.error_first_name_missing
        "Missing last name." -> R.string.error_last_name_missing
        "Missing id identifier." -> R.string.error_id_missing
        "Invalid id identifier." -> R.string.error_id_invalid
        else -> R.string.error_desconocido
    }
}