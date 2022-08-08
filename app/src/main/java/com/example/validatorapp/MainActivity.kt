package com.example.validatorapp

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.validatorapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        mainViewModel = MainViewModel()

        binding.etPanNumber.setFilters(arrayOf<InputFilter>(AllCaps()))


        binding.btnNext.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.details_submitted_successfully),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        binding.tvDontHavePan.setOnClickListener {
            finish()
        }

        binding.etPanNumber.addTextChangedListener(textWatcher)
        binding.etDay.addTextChangedListener(textWatcher)
        binding.etMonth.addTextChangedListener(textWatcher)
        binding.etYear.addTextChangedListener(textWatcher)

    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val panInput: String = binding.etPanNumber.getText().toString().trim()
            val dayInput: Int? = binding.etDay.getText().toString().trim().toIntOrNull()
            val monthInput: Int? = binding.etMonth.getText().toString().trim().toIntOrNull()
            val yearInput: Int? = binding.etYear.getText().toString().trim().toIntOrNull()

            if (dayInput != null && monthInput != null && yearInput != null) {

                if (mainViewModel.isValidDate(dayInput, monthInput, yearInput)
                    && mainViewModel.isValidPanCardNo(panInput)
                ) {
                    binding.btnNext.setBackgroundColor(getColor(R.color.purple_200))
                    binding.btnNext.isEnabled = true
                }
                else {
                    binding.btnNext.setBackgroundColor(getColor(R.color.strokeColor))
                    binding.btnNext.isEnabled = false
                }
            }
        }
        override fun afterTextChanged(s: Editable) {}
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setMessage(this.getString(R.string.quit_confirmation_message))
            .setCancelable(false)
            .setPositiveButton(
                this.getString(R.string.yes)
            ) { dialog, id -> this@MainActivity.finish() }
            .setNegativeButton(this.getString(R.string.no), null)
            .show()
    }
}