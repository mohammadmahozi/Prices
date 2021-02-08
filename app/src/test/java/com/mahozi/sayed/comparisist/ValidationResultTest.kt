package com.mahozi.sayed.comparisist

import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector

/**
 *Test class for @link ValidationResult
 */

class ValidationResultTest {

    @JvmField
    @Rule
    var collector = ErrorCollector()

    @Test
    fun isDecimalWithIsValidTrue(){

        collector.checkThat("Empty", ValidationResult("").isDecimal().isValid, `is`(false))
        collector.checkThat("space char", ValidationResult(" ").isDecimal().isValid, `is`(false))
        collector.checkThat("Int", ValidationResult("1546").isDecimal().isValid, `is`(true))
        collector.checkThat("Double", ValidationResult("15.46").isDecimal().isValid, `is`(true))
        collector.checkThat("negative", ValidationResult("-15").isDecimal().isValid, `is`(false))
        collector.checkThat("characters", ValidationResult("dgd").isDecimal().isValid, `is`(false))
        collector.checkThat("character and numbers", ValidationResult("12a").isDecimal().isValid, `is`(false))
        collector.checkThat("point only", ValidationResult(".").isDecimal().isValid, `is`(false))
        collector.checkThat("point and number to right", ValidationResult("0.124").isDecimal().isValid, `is`(true))
        collector.checkThat("point and number to left", ValidationResult("12.").isDecimal().isValid, `is`(false))

    }

    @Test
    fun isDecimalWithIsValidFalse(){

        val validationResult = ValidationResult("").isEmpty().isDecimal()
        assert(validationResult.errorMessage == ValidationResult.EMPTY_FIELD_ERROR)
    }


    @Test
    fun isEmptyWithIsValidTrue(){

        collector.checkThat("Chars", ValidationResult("1").isEmpty().isValid, `is`(true))
        collector.checkThat("Empty", ValidationResult("").isEmpty().isValid, `is`(false))
        collector.checkThat("space char", ValidationResult(" ").isEmpty().isValid, `is`(false))

    }


    @Test
    fun isEmptyWithIsValidFalse(){

        val validationResult = ValidationResult("aa").isDecimal().isEmpty()
        //error message should not be changed since it not decimal and isValid false
        assert(validationResult.errorMessage == ValidationResult.NOT_NUMBER_ERROR)
    }




}