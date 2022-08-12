package com.example.validatorapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.validatorapp.utils.CommonUtils
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.*
import javax.inject.Inject


class MainViewModel @Inject constructor(): ViewModel() {

    private val isValidPan: MutableLiveData<Boolean> = MutableLiveData()
    val _isValidPan: LiveData<Boolean> = isValidPan

    private val isValidDob: MutableLiveData<Boolean> = MutableLiveData()
    val _isValidDob: LiveData<Boolean> = isValidDob

    init {
        isValidPan.postValue(false)
        isValidDob.postValue(false)
    }

    fun validatePanCard(panData: String) {
        viewModelScope.launch {
            isValidPan.postValue(CommonUtils.isValidPanCardNo(panData))
        }
    }

    fun validateDob(day: String, month: String, year: String) {
        viewModelScope.launch {
                isValidDob.postValue(CommonUtils.isDobValid(day, month, year))
        }
    }
}