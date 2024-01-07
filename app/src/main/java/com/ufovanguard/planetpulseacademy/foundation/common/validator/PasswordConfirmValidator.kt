package com.ufovanguard.planetpulseacademy.foundation.common.validator

import com.ufovanguard.planetpulseacademy.R

class PasswordConfirmValidator(private val otherPassword: String): Validator<String>() {

	override fun validate(input: String): ValidatorResult {
		return if (input != otherPassword) {
			ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.password_must_be_the_same))
		} else ValidatorResult.Success
	}
}