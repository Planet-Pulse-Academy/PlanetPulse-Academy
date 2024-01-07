package com.ufovanguard.planetpulseacademy.foundation.common.validator

import com.ufovanguard.planetpulseacademy.R

class OtpValidator: Validator<String>() {

	override fun validate(input: String): ValidatorResult {
		return if (input.isBlank()) {
			ValidatorResult.Failure(ValidatorResult.asStringResource(R.string.otp_cannot_be_empty))
		} else ValidatorResult.Success
	}
}