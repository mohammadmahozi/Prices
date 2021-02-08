package com.mahozi.sayed.comparisist.prices.create

import com.mahozi.sayed.comparisist.ValidationResult

class PriceFormValidator() {

    fun validateQuantity(quantityAsString: String): ValidationResult{

      return ValidationResult(quantityAsString).isEmpty().isDecimal()
    }


    fun validatePrice(priceAsString: String): ValidationResult{

        return ValidationResult(priceAsString).isEmpty().isDecimal()
    }


}