package com.example.validatorapp.utils

import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class CommonUtils {

    companion object {

        fun isLeap(year: Int): Boolean {
            return year % 4 == 0 &&
                    year % 100 != 0 ||
                    year % 400 == 0
        }

        fun isValidDate(d: Int, m: Int, y: Int): Boolean {

            if (y > Constants.MAX_VALID_YR ||
                y < Constants.MIN_VALID_YR
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

            val regex = Constants.PAN_REGEX

            val p: Pattern = Pattern.compile(regex)

            if (panCardNo == null) {
                return false
            }

            val m: Matcher = p.matcher(panCardNo)

            return m.matches()
        }

        fun isBeforeToday(day: Int, month: Int, year: Int): Boolean {
            val today = Calendar.getInstance()
            val myDate = Calendar.getInstance()
            myDate.set(year, month, day)
            return if (myDate.after(today))
                false
             else true
        }

        fun isDobValid(day: String, month: String, year: String): Boolean{
            val d: Int = day.toInt()
            val m: Int = month.toInt()
            val y: Int = year.toInt()

            return (isValidDate(d, m, y) && isBeforeToday(d, m - 1, y))
        }

    }
}