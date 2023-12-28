package com.ufovanguard.planetpulseacademy.foundation.common.validator

import com.ufovanguard.planetpulseacademy.R

class NameValidator: Validator<String>() {

	override fun validate(input: String): ValidatorResult {
		if (input.isBlank()) return ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.name_cannot_be_empty))

		return ValidatorResult.Success
	}
}