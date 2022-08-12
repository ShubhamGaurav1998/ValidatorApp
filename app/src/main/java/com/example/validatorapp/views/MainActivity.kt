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
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.validatorapp.MyApplication
import com.example.validatorapp.R
import com.example.validatorapp.databinding.ActivityMainBinding
import com.example.validatorapp.viewModels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as MyApplication).applicationComponent.inject(this)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        binding.etPanNumber.setFilters(arrayOf(AllCaps(), LengthFilter(10)))

        binding.btnNext.setOnClickListener(this)
        binding.tvDontHavePan.setOnClickListener(this)

        binding.etPanNumber.addTextChangedListener(textWatcher)
        binding.etDay.addTextChangedListener(textWatcher)
        binding.etMonth.addTextChangedListener(textWatcher)
        binding.etYear.addTextChangedListener(textWatcher)

        setObservers()
    }

    private fun setObservers() {
        mainViewModel._isValidDob.observe(this, Observer {
            enableOrDisableNextButton()
        })

        mainViewModel._isValidPan.observe(this, Observer {
            enableOrDisableNextButton()
        })
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val panInput: String = binding.etPanNumber.text.toString().trim()
            val dayInput: String = binding.etDay.text.toString().trim()
            val monthInput: String = binding.etMonth.text.toString().trim()
            val yearInput: String = binding.etYear.text.toString().trim()

            changeFocusBetweenInputFields(panInput, dayInput, monthInput, yearInput)

            if (dayInput.length == 2 && monthInput.length == 2 && yearInput.length == 4 && panInput.length == 10) {

                mainViewModel.validateDob(dayInput, monthInput, yearInput)
                mainViewModel.validatePanCard(panInput)

            }
        }
        override fun afterTextChanged(s: Editable) {}
    }

    fun enableOrDisableNextButton(){
        if(mainViewModel._isValidDob.value == true && mainViewModel._isValidPan.value == true){
            binding.btnNext.isEnabled = true
            hideKeyboard()
        } else {
            binding.btnNext.isEnabled = false
        }
    }

    private fun changeFocusBetweenInputFields(panInput: String, dayInput: String, monthInput: String, yearInput: String) {
        if(panInput.length == 10)
        {
            binding.etDay.requestFocus()
        }

        if(dayInput.length == 2)
        {
            binding.etMonth.requestFocus()
        }

        if(monthInput.length == 2 && dayInput.length == 2)
        {
            binding.etYear.requestFocus()
        }

        if(yearInput.length == 4 && panInput.length != 10)
        {
            binding.etPanNumber.requestFocus()
        }
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
                lifecycleScope.launch {
                    delay(2000)
                    finish()
                }
            }

            binding.tvDontHavePan.id -> {
                finish()
            }
        }
    }
}