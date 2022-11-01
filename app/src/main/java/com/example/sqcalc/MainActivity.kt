package com.example.sqcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 10

class MainActivity : AppCompatActivity() {

    lateinit var seekBar: SeekBar
    lateinit var tvMtrlPercent: TextView
    lateinit var etLength: EditText
    lateinit var etWidth: EditText
    lateinit var tvSqFootage: TextView
    lateinit var tvTotalMtrlAmount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seekBar = findViewById(R.id.seekBartMtrl)
        tvMtrlPercent = findViewById(R.id.tvMtrlPercent)
        etLength = findViewById(R.id.etLength)
        etWidth = findViewById(R.id.etWidth)
        tvSqFootage = findViewById(R.id.tvSqFootage)
        tvTotalMtrlAmount = findViewById(R.id.tvTotalMtrlAmount)

        seekBar.progress = INITIAL_TIP_PERCENT // Initial Value for the Tip Percentage
        tvMtrlPercent.text = "$INITIAL_TIP_PERCENT%"



        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                // p1 is the progress, value received when user interacts with SeekBar
                Log.i(TAG, "onProgressChanged $p1")
                tvMtrlPercent.text = "$p1%"
                computeTipAndTotal()

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        etLength.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")

            }

        })

        etWidth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()
            }

        })
    }

    private fun computeTipAndTotal() {
        // Get the value of the base and tip percent
        if (etLength.text.toString().isEmpty()) {
            // Avoids empty String error when user deletes input
            tvSqFootage.text = ""
            tvTotalMtrlAmount.text = ""
            return
        }
        val baseAmount = etLength.text.toString().toDouble()
        val widthAmount = etWidth.text.toString().toDouble()

        val tipPercent = seekBar.progress

        val tipAmount = baseAmount * widthAmount
        val sqft = tipAmount * tipPercent /100
        val totalAmount = sqft + tipAmount

        tvSqFootage.text = "%.2f".format(tipAmount)
        tvTotalMtrlAmount.text = "%.2f".format(totalAmount)

    }
}