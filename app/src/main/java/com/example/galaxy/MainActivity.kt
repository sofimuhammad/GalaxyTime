package com.example.galaxy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.galaxy.util.InputUtil
import com.example.galaxy.util.OutputUtil

class MainActivity : AppCompatActivity() {

    private val arraySentences: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<AppCompatButton>(R.id.btn_galaxy)
        val input = findViewById<AppCompatEditText>(R.id.et_input)
        val textResult = findViewById<AppCompatTextView>(R.id.tv_result)

        button.setOnClickListener {
            if (input.text.toString().isEmpty()) {
                Toast.makeText(this, "Tidak Boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                val isFind = arraySentences.find { it == input.text.toString() }
                if (isFind == null) {
                    arraySentences.add(input.text.toString())
                    InputUtil.processSentences(arraySentences[arraySentences.lastIndex])
                    InputUtil.mapRomanToInt()
                    OutputUtil.processReplyQuestion {
                        Log.e("queryP", it)
                        textResult.text = it
                    }
                    input.setText("")
                    /*OutputUtil.getListener {
                        Log.e("queryP", it)
                        textResult.text = it
                    }*/
                } else {
                    Toast.makeText(this, "${input.text.toString()} sudah ada", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}