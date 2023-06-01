package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.tipOptions.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.option_custom) {
                binding.customPrice.visibility = View.VISIBLE
            } else {
                if (binding.customPrice.visibility == View.VISIBLE) {
                    binding.customPrice.visibility = View.INVISIBLE
                    binding.customPrice.setText("")
                }
            }
        }
    }

    private fun calculateTip() {
        val cost = binding.costOfService.text.toString().toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_fifteen_percent -> 0.15
            R.id.option_ten_percent -> 0.10
            else -> {
                val percent = binding.customPrice.text.toString().toDoubleOrNull()
                if (percent == null || percent < 1) {
                    displayTip(0.0)
                    return
                }
                percent / 100
            }
        }
        var tip = cost * tipPercentage
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(amount: Double) {
        if (amount < 0.01) {
            binding.tipResult.text = getString(R.string.tip_amount_start)
        } else {
            val formattedTip = NumberFormat.getCurrencyInstance(Locale("uk", "UA")).format(amount)
            binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        }
    }
}