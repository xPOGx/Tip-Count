package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        binding.tipOptions.setOnCheckedChangeListener { _, checkedId -> showCustomEditView(checkedId) }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.customPriceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun calculateTip() {
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_fifteen_percent -> 0.15
            R.id.option_ten_percent -> 0.10
            else -> { customPercent() }
        }
        var tip = cost * tipPercentage
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        displayTip(tip)
    }

    private fun customPercent(): Double {
        val percent = binding.customPriceEditText.text.toString().toDoubleOrNull()
        if (percent == null || percent < 1) {
            return 0.0
        }
        return percent / 100
    }

    private fun displayTip(amount: Double) {
        if (amount < 0.01) {
            binding.tipResult.text = getString(R.string.tip_amount_start)
        } else {
            val formattedTip = NumberFormat.getCurrencyInstance(Locale("uk", "UA")).format(amount)
            binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        }
    }

    private fun showCustomEditView(checkedId: Int) {
        if (checkedId == R.id.option_custom) {
            binding.customPrice.visibility = View.VISIBLE
        } else {
            if (binding.customPrice.visibility == View.VISIBLE) {
                binding.customPrice.visibility = View.INVISIBLE
                binding.customPriceEditText.setText("")
            }
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}