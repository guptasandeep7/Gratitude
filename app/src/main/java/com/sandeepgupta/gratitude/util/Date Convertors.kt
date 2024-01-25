package com.sandeepgupta.gratitude.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun localDateToYYYYMMDDFormat(localDate: LocalDate): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return localDate.format(formatter).toLong()
}

fun localDateToText(date: LocalDate): String {
    return when (date) {
        LocalDate.now() -> {
            "Today"
        }

        LocalDate.now().minusDays(1) -> {
            "Yesterday"
        }

        else -> {
            "${date.month.name[0].uppercase()}${
                date.month.name.removeRange(0, 1).lowercase()
            }, ${date.dayOfMonth}${ordinal(date.dayOfMonth)}"
        }
    }
}


fun ordinal(number: Int): String {
    val suffixes = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    return when (number % 100) {
        11, 12, 13 -> number.toString() + "th"
        else -> suffixes[number % 10]
    }
}
