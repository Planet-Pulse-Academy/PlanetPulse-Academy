package com.ufovanguard.planetpulseacademy.foundation.common.validator

import com.ufovanguard.planetpulseacademy.R

class EmailValidator: Validator<String>() {

	override fun validate(input: String): ValidatorResult {
		if (input.isBlank()) return ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.email_cannot_be_empty))

		// ^: matches beginning of the string
		// []: matches any character in this set
		// \w: matches any word (alphanumeric and underscore)
		// -: matches "-" character
		// \\: escaped character
		// .: matches "." character
		// +: matches 1 or more of the preceding token.
		// @: matches "@" character

		// Source: https://regexr.com/3e48o
		val regex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$".toRegex()

		return if (regex.matches(input)) ValidatorResult.Success
		else ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.invalid_email))
	}
}