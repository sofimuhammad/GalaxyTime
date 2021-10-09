package com.example.galaxy.util

import android.util.Log
import java.lang.StringBuilder

object InputUtil {
    val romawiValueMapping = HashMap<String, String>()
    val romawiIntMapping = HashMap<String, Float>()
    val questionMapping = HashMap<String, String>()
    val questionMapping2 = mutableListOf<String>()
    val unknownValues: MutableList<String> = mutableListOf()
    val metalValues = HashMap<String, Float>()

    fun processSentences(sentences: String) {
        val sen = sentences.split(regex = Regex("((?<=:)|(?=:))|( )"))
        when {
            sentences.endsWith("?") -> questionMapping2.add(sentences)
//            sentences.endsWith("?") -> questionMapping[sentences] = ""
            sen.size == 3 && sen[1].equals("is", true) -> {
                romawiValueMapping[sen[0]] = sen[sen.size-1]
            }
            sentences.endsWith("credits", true) -> unknownValues.add(sentences)
        }
    }

    fun mapRomanToInt() {
        romawiValueMapping.entries.forEach {
            val intValue = RomanDecimal().romanToDecimal(it.value)
            romawiIntMapping[it.key] = intValue
        }
        mapUnknownValues()
    }

    private fun mapUnknownValues() {
        unknownValues.forEach {
            decodeUnknownQuery(it)
        }
    }

    private fun decodeUnknownQuery(query: String) {
        val arrayQuery = query.split(regex = Regex("((?<=:)|(?=:))|( )")).toTypedArray()
        var index = 0
        var creditValue = 0

        var element = ""
        var arrayString = arrayOf<String>()
        for (i in arrayQuery.indices) {
            if (arrayQuery[i].equals("credits", true)) {
                creditValue = arrayQuery[i - 1].toInt()
            }
            if (arrayQuery[i].equals("is", true)) {
                index = i-1
                element = arrayQuery[i - 1]
            }
            arrayString = arrayQuery.copyOfRange(0, index)
        }

        val stringBuilder = StringBuilder()
        for (i in arrayString.indices) {
            stringBuilder.append(romawiValueMapping[arrayString[i]])
        }

        val valueElementDecimal = RomanDecimal().romanToDecimal(stringBuilder.toString())
        Log.e("query", "element => $element : ${creditValue.div(valueElementDecimal)}")
        metalValues[element] = creditValue.div(valueElementDecimal)
    }
}