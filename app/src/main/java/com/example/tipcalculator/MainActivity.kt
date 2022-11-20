package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener { calculateTip() }
        binding.etCostOfService.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun calculateTip() {
        val costStr: String = binding.etCostOfService.text.toString()
        val cost = costStr.toDoubleOrNull()
        if(cost == null) {
            binding.tvTipAmount.text = ""
            return
        }
        val tipPercentage = when(binding.rgTipOptions.checkedRadioButtonId) {
            R.id.rbOpt20Percent -> 0.20
            R.id.rbOpt18Percent -> 0.18
            else -> 0.15
        }
        var tip = tipPercentage * cost
        if (binding.swRoundUp.isChecked)
            tip = kotlin.math.ceil(tip)

        // Display formatted value on scren
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvTipAmount.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}