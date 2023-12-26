package com.ufovanguard.planetpulseacademy.foundation.extension

import com.google.gson.Gson

fun <T> String.fromJson(klass: Class<T>): T {
	return Gson().fromJson(this, klass)
}

fun String.containsDigit(): Boolean {
	for (c in this) if (c.isDigit()) return true
	return false
}

fun String.containsUppercase(): Boolean {
	for (c in this) if (c.isUpperCase()) return true
	return false
}

fun String.containsLowercase(): Boolean {
	for (c in this) if (c.isLowerCase()) return true
	return false
}
