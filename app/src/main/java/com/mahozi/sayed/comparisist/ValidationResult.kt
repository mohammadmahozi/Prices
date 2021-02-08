package com.mahozi.sayed.comparisist


/**
 * Chain validation until one fails.
 */
class ValidationResult (input: String){

    private val input = input.trim()

    var isValid = true
        private set

    lateinit var errorMessage: String
        private set


    fun isEmpty(): ValidationResult{

        //only check if the previous validation succeeded or this is the first validation
        if (isValid && input == ""){
            isValid = false
            errorMessage = EMPTY_FIELD_ERROR
        }

        return this
    }


    fun isDecimal(): ValidationResult{

        //only check if the previous validation succeeded or this is the first validation
        if (isValid && !input.matches("^[0-9]*\\.?[0-9]+\$".toRegex())){

            isValid = false
            errorMessage = NOT_NUMBER_ERROR
        }

        return this
    }


    companion object{

        const val EMPTY_FIELD_ERROR = "This field cannot be empty"
        const val NOT_NUMBER_ERROR = "Only numbers allowed"
    }

}