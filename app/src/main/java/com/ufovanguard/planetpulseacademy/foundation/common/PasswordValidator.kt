package com.ufovanguard.planetpulseacademy.foundation.common

import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.foundation.extension.containsDigit
import com.ufovanguard.planetpulseacademy.foundation.extension.containsLowercase
import com.ufovanguard.planetpulseacademy.foundation.extension.containsUppercase

class PasswordValidator: Validator<String>() {

	override fun validate(input: String): ValidatorResult {
		if (input.isBlank()) {
			return ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.password_cant_be_empty))
		}

		if (input.length < 8) {
			return ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.below_min_length_exception_msg))
		}

		if (!input.containsDigit()) {
			return ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.digit_missing_exception_msg))
		}

		if (!input.containsUppercase()) {
			return ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.uppercase_missing_exception_msg))
		}

		if (!input.containsLowercase()) {
			return ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.lowercase_missing_exception_msg))
		}

		return ValidatorResult.Success
	}
}