package com.mahozi.sayed.comparisist.products.create

import com.mahozi.sayed.comparisist.ValidationResult

class ProductFormValidator() {

    fun validateProductName(productName: String): ValidationResult {

        return ValidationResult(productName).isEmpty()
    }

    fun validateBrandName(brandName: String): ValidationResult{

        return ValidationResult(brandName).isEmpty()
    }

    fun validateSize(size: String): ValidationResult{

        return ValidationResult(size).isEmpty().isEmpty()

    }

    fun validateUnit(unit: String): ValidationResult{

        return ValidationResult(unit).isEmpty()

    }

    fun validateStoreName(storeName: String): ValidationResult{

        return ValidationResult(storeName).isEmpty()
    }

    fun validateQuantity(quantityAsString: String): ValidationResult{

        return ValidationResult(quantityAsString).isEmpty().isDecimal()
    }


    fun validatePrice(priceAsString: String): ValidationResult{

        return ValidationResult(priceAsString).isEmpty().isDecimal()
    }
}