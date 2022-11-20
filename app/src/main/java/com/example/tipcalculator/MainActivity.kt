package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener { calculateTip() }
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
}