package com.ssafy.pickit.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
fun validateBirthDate(birthDate: String): Boolean {
    if (birthDate.length != 8 || birthDate.toIntOrNull() == null) {
        return false
    }

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    return try {
        val date = LocalDate.parse(birthDate, formatter)
        val now = LocalDate.now()
        !date.isAfter(now) && date.isAfter(now.minusYears(120))
    } catch (e: DateTimeParseException) {
        false
    }
}