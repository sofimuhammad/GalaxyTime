package com.example.galaxy.util

import com.example.galaxy.rules.Rules

class RomanDecimal: Rules() {
    fun romanToDecimal(roman: String): Float {
        var currentDecimal = 0f
        var lastDecimal = 0f
        val romanReversed = roman.reversed().toCharArray()

        for(i in roman.indices) {
            when (val char = romanReversed[i]) {
                'I', 'i' -> {
                    super.checkCountValidity(char)
                    currentDecimal = processDecimal(1f, lastDecimal, currentDecimal)
                    lastDecimal = 1f
                }
                'V', 'v' -> {
                    super.checkCountValidity(char)
                    currentDecimal = processDecimal(5f, lastDecimal, currentDecimal)
                    lastDecimal = 5f
                }
                'X','x' -> {
                    super.checkCountValidity(char)
                    currentDecimal = processDecimal(10f, lastDecimal, currentDecimal)
                    lastDecimal = 10f
                }
                'L', 'l' -> {
                    super.checkCountValidity(char)
                    currentDecimal = processDecimal(50f, lastDecimal, currentDecimal)
                    lastDecimal = 50f
                }
                'C', 'c' -> {
                    super.checkCountValidity(char)
                    currentDecimal = processDecimal(100f, lastDecimal, currentDecimal)
                    lastDecimal = 100f
                }
                'D', 'd' -> {
                    super.checkCountValidity(char)
                    currentDecimal = processDecimal(500f, lastDecimal, currentDecimal)
                    lastDecimal = 500f
                }
                'M','m' -> {
                    super.checkCountValidity(char)
                    currentDecimal = processDecimal(1000f, lastDecimal, currentDecimal)
                    lastDecimal = 1000f
                }
            }
        }

        super.resetCounter()
        return currentDecimal
    }

    private fun processDecimal(currentDecimal: Float, lastNumber: Float, nextDecimal: Float): Float {
        return if (lastNumber > currentDecimal) {
            super.subtractionLogic(nextDecimal, currentDecimal, lastNumber)
        } else {
            nextDecimal + currentDecimal
        }
    }
}