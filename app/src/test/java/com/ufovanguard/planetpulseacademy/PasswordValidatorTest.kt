package com.ufovanguard.planetpulseacademy

import com.ufovanguard.planetpulseacademy.foundation.common.validator.PasswordValidator
import com.ufovanguard.planetpulseacademy.foundation.common.validator.ValidatorResult
import org.junit.jupiter.api.Test

class PasswordValidatorTest {

	private val passwordValidator = PasswordValidator()

	@Test
	fun `empty test` () {
		val result = passwordValidator.validate("")

		assert(result.isFailure && result.errMsg!! == ValidatorResult.asStringResource(R.string.password_cannot_be_empty))
	}

	@Test
	fun `password length is below the minimum` () {
		val result = passwordValidator.validate("W0ny")

		assert(result.isFailure && result.errMsg!! == ValidatorResult.asStringResource(R.string.below_min_length_exception_msg))
	}

	@Test
	fun `password not contain at least one digit` () {
		val result = passwordValidator.validate("Wonyoung")

		assert(result.isFailure && result.errMsg!! == ValidatorResult.asStringResource(R.string.digit_missing_exception_msg))
	}

	@Test
	fun `password not contain at least one uppercase letter` () {
		val result = passwordValidator.validate("w0nyoung")

		assert(result.isFailure && result.errMsg!! == ValidatorResult.asStringResource(R.string.uppercase_missing_exception_msg))
	}

	@Test
	fun `password not contain at least one lowercase letter` () {
		val result = passwordValidator.validate("W0NY0UNG")

		assert(result.isFailure && result.errMsg!! == ValidatorResult.asStringResource(R.string.lowercase_missing_exception_msg))
	}

	@Test
	fun `validation success`() {
		val result = passwordValidator.validate("W0ny0ung")

		assert(result.isSuccess)
	}

}