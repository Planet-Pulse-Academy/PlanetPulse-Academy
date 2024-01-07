package com.ufovanguard.planetpulseacademy.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun ProfileScreenPreview() {
	PlanetPulseAcademyTheme {
		ProfileScreenContent(
			state = ProfileState(),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel,
	onNavigateUp: () -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel
	) { scaffoldPadding ->
		ProfileScreenContent(
			state = state,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
		)
	}
}

@Composable
private fun ProfileScreenContent(
	state: ProfileState,
	modifier: Modifier = Modifier
) {

}
