package com.example.validatorapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter.AllCaps
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.validatorapp.R
import com.example.validatorapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        mainViewModel = MainViewModel()

        binding.etPanNumber.setFilters(arrayOf(AllCaps(), LengthFilter(10)))


        binding.btnNext.setOnClickListener(this)
        binding.tvDontHavePan.setOnClickListener(this)

        binding.etPanNumber.addTextChangedListener(textWatcher)
        binding.etDay.addTextChangedListener(textWatcher)
        binding.etMonth.addTextChangedListener(textWatcher)
        binding.etYear.addTextChangedListener(textWatcher)

    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val panInput: String = binding.etPanNumber.text.toString().trim()
            val dayInput: Int? = binding.etDay.text.toString().trim().toIntOrNull()
            val monthInput: Int? = binding.etMonth.text.toString().trim().toIntOrNull()
            val yearInput: Int? = binding.etYear.text.toString().trim().toIntOrNull()

            if(binding.etPanNumber.text.toString().length == 10)
            {
                binding.etDay.requestFocus()
            }

            if(binding.etDay.text.toString().length == 2 && binding.etPanNumber.text.toString().length == 10)
            {
                binding.etMonth.requestFocus()
            }

            if(binding.etMonth.text.toString().length == 2 && binding.etPanNumber.text.toString().length == 10)
            {
                binding.etYear.requestFocus()
            }

            if (dayInput != null && monthInput != null && yearInput != null) {

                if (checkIfDDMMlenghthIs2(binding.etDay.text.toString().trim(), binding.etMonth.text.toString().trim()) &&
                    mainViewModel.isValidDate(dayInput, monthInput, yearInput) && mainViewModel.isUser18Older(yearInput, monthInput, dayInput)
                    && mainViewModel.isValidPanCardNo(panInput)
                ) {
                    binding.btnNext.isEnabled = true
                    hideKeyboard()
                }
                else {
                    binding.btnNext.isEnabled = false
                }
            }
        }
        override fun afterTextChanged(s: Editable) {}
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    fun checkIfDDMMlenghthIs2(day: String, month: String): Boolean {
        return (day.length == 2 && month.length == 2)
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

    override fun onClick(p0: View?) {
        when(p0?.id) {

            binding.btnNext.id -> {
                Toast.makeText(
                    this,
                    getString(R.string.details_submitted_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }

            binding.tvDontHavePan.id -> {
                finish()
            }
        }
    }
}