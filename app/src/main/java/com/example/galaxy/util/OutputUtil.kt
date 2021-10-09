package com.example.galaxy.util

import android.util.Log
import java.util.regex.Pattern

object OutputUtil {

    fun processReplyQuestion(listen: (String) -> Unit) {
        //val lastIndex = InputUtil.questionMapping.entries.toMutableList().lastIndex
        val lastIndex = InputUtil.questionMapping2.lastIndex

        InputUtil.questionMapping2.forEachIndexed { index, it ->
            Log.e("error", it)
            if (lastIndex == index) {
                processReply(it, listen)
            }
        }
    }

    private fun processReply(query: String, listen: (String) -> Unit) {
        when {
            query.toLowerCase().startsWith("how much") -> findValueOfRoman(query, listen)
            query.toLowerCase().startsWith("how many") -> findValueOfElement(query, listen)
        }
    }

    private fun findValueOfRoman(query: String, listen: (String) -> Unit) {
        if (isValidInput(query)) {
            val tokenValue = splitQuery(query)
            val tokenValueToRoman = arrayListOf<String>()

            tokenValue.forEach {
                if (it != null) {
                    InputUtil.romawiValueMapping[it]?.let { it1 -> tokenValueToRoman.add(it1) }
                } else {
                    Log.e("errorRoman", "I have no idea what you are talking about")
                    listen("I have no idea what you are talking about")
                }
            }
            val romanDecimal = RomanDecimal().romanToDecimal(tokenValueToRoman.toString())
            if (romanDecimal != 0f) {
                tokenValue.add("is")
                tokenValue.add(romanDecimal.toString())
                Log.e("query", outputFormatter(tokenValue))
                listen(outputFormatter(tokenValue))
            } else {
                Log.e("errorRoman", "I have no idea what you are talking about")
                listen("I have no idea what you are talking about")
            }
        } else {
            Log.e("error", "I have no idea what you are talking about")
            listen("I have no idea what you are talking about")
        }
    }

    private fun findValueOfElement(query: String, listen: (String) -> Unit) {
        if (isValidInput(query)) {
            val tokenValue = splitQuery(query)
            val tokenValueToRoman = arrayListOf<String>()
            var element = ""

            tokenValue.forEach {
                if (it != null) {
                    when {
                        InputUtil.romawiValueMapping[it] != null -> {
                            InputUtil.romawiValueMapping[it]?.let { it1 -> tokenValueToRoman.add(it1) }
                        }
                        InputUtil.metalValues[it] != null -> {
                            element = it
                        }
                        else -> {
                            Log.e("error", "$query : I have no idea what you are talking about")
                            listen(" : I have no idea what you are talking about")
                        }
                    }
                }
            }

            val romanDecimal = RomanDecimal().romanToDecimal(tokenValueToRoman.toString()) * InputUtil.metalValues[element]!!
            tokenValue.add("is")
            tokenValue.add(romanDecimal.toString())
            tokenValue.add("Credits")
            Log.e("query", outputFormatter(tokenValue))
            listen(outputFormatter(tokenValue))
        } else {
            Log.e("error", "$query : I have no idea what you are talking about")
            listen("I have no idea what you are talking about")
        }
    }

    private fun outputFormatter(output: MutableList<String?>) =
        output.toString().replace(",","")
            .replace("[", "")
            .replace("]", "")

    private fun isValidInput(query: String) = !(Pattern.compile("[$&+,:;=@#|]").matcher(query).find())

    private fun splitQuery(query: String): MutableList<String?> {
        val queryArray = query.split(Regex("((?<=:)|(?=:))|( )")).toTypedArray()
        var startIndex = 0
        var endIndex = 0
        queryArray.forEachIndexed { index, s ->
            when (s) {
                "is", "Is", "IS", "iS" -> {
                    startIndex = index+1
                }
                "?" -> {
                    endIndex = index
                }
            }
        }

        val array = queryArray.copyOf(queryArray.size)
        return array.copyOfRange(startIndex, endIndex).toMutableList()
    }
}