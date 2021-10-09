package com.example.galaxy.rules

import android.util.Log
import kotlin.math.roundToInt

open class Rules {
    private val unrepeatableValue = charArrayOf('D', 'L', 'V')
    private val repeatableValue = charArrayOf('I', 'V', 'X', 'M')
    private var repeatableLiteralCount = getCountingRepeatableValue()
    private var unrepeatableLiteralCount = getCountingUnrepeatableValue()
    private val subtractedValue = mutableMapOf(
        1 to intArrayOf(5, 10),
        5 to intArrayOf(),
        10 to intArrayOf(50,100),
        50 to intArrayOf(),
        100 to intArrayOf(100, 1000),
        500 to intArrayOf(),
        1000 to intArrayOf()
    )
    private val charToNumericValue = mutableMapOf(
        'I' to 1,
        'V' to 5,
        'X' to 10,
        'L' to 50,
        'C' to 100,
        'D' to 500,
        'M' to 1000
    )

    private fun getCountingRepeatableValue(): HashMap<Char, Int> = hashMapOf(
        'I' to 0,
        'X' to 0,
        'C' to 0,
        'M' to 0
    )

    private fun getCountingUnrepeatableValue(): HashMap<Char, Int> = hashMapOf(
        'V' to 0,
        'L' to 0,
        'D' to 0
    )

    fun checkCountValidity(char: Char) {
        when {
            checkIfPresent(unrepeatableValue, char) -> {
                unrepeatableLiteralCount[char] = unrepeatableLiteralCount[char]!! + 1
                if (unrepeatableLiteralCount.containsValue(3)) {
                    Log.e("error", "V, L, D cannot be repeated")
                }
            }
            checkIfPresent(repeatableValue, char) -> {
                val threeChar = getKeyForValueContainThree()
                if (threeChar != '\u0000') {
                    when {
                        (char.equals(threeChar, true)) -> {
                            Log.e("error", "$char cannot repeat 4 times")
                        }
                        (isSmallerThanPrevious(char, threeChar)) -> {
                            repeatableLiteralCount[char] = repeatableLiteralCount[char]!! + 1
                            repeatableLiteralCount[threeChar] = 0
                        }
                    }
                } else {
                    repeatableLiteralCount[char] = repeatableLiteralCount[char]!! + 1
                }
            }
        }
    }

    fun subtractionLogic(nextDecimal: Float, currentDecimal: Float, lastNumber: Float): Float {
        return if (subtractedValue[currentDecimal.roundToInt()]!!.contains(lastNumber.roundToInt())) {
            nextDecimal - currentDecimal
        } else {
            nextDecimal + currentDecimal
        }
    }

    private fun checkIfPresent(arrayChar: CharArray, char: Char): Boolean {
        val result = arrayChar.find { it.equals(char, true) }

        return result != null
    }

    private fun isSmallerThanPrevious(char: Char, charThree: Char) =
        charToNumericValue[char]!! < charToNumericValue[charThree]!!

    private fun getKeyForValueContainThree(): Char {
        val key = '\u0000'
        repeatableLiteralCount.entries.forEach {
            if (it.value == 3) {
                return it.key
            }
        }
        return key
    }

    fun resetCounter() {
        repeatableLiteralCount = getCountingRepeatableValue()
        unrepeatableLiteralCount = getCountingUnrepeatableValue()
    }
}