package com.example.validatorapp.views

import androidx.lifecycle.ViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.*


class MainViewModel: ViewModel() {

    private val MAX_VALID_YR: Int = 9999
    private val MIN_VALID_YR: Int = 1800

    fun isLeap(year: Int): Boolean {
        return year % 4 == 0 &&
                year % 100 != 0 ||
                year % 400 == 0
    }

    fun isValidDate(d: Int, m: Int, y: Int): Boolean {

        if (y > MAX_VALID_YR ||
            y < MIN_VALID_YR
        ) return false
        if (m < 1 || m > 12) return false
        if (d < 1 || d > 31) return false

        if (m == 2) {
            return if (isLeap(y)) d <= 29 else d <= 28
        }

        return if (m == 4 || m == 6 || m == 9 || m == 11
        ) d <= 30 else true
    }

    fun isValidPanCardNo(panCardNo: String?): Boolean {

        val regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"

        val p: Pattern = Pattern.compile(regex)

        if (panCardNo == null) {
            return false
        }

        val m: Matcher = p.matcher(panCardNo)

        return m.matches()
    }

    fun isUser18Older(year: Int, month: Int, day: Int): Boolean {
        val c1 = Calendar.getInstance()
        c1[year, month - 1, day, 0] = 0
        val c2 = Calendar.getInstance()
        var diff = c2[Calendar.YEAR] - c1[Calendar.YEAR]
        if (c1[Calendar.MONTH] > c2[Calendar.MONTH] ||
            c1[Calendar.MONTH] == c2[Calendar.MONTH] && c1[Calendar.DATE] > c2[Calendar.DATE]
        ) {
            diff--
        }
        return diff >= 18
    }
}