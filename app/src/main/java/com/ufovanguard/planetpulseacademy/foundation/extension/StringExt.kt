package com.ufovanguard.planetpulseacademy.foundation.extension

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
