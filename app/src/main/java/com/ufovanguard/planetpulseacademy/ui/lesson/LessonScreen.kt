package com.ufovanguard.planetpulseacademy.ui.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun LessonScreenPreview() {

	PlanetPulseAcademyTheme {
		LessonScreenContent(
			state = LessonState(),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun LessonScreen(
	viewModel: LessonViewModel,
	onNavigateUp: () -> Unit,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel
	) { scaffoldPadding ->
		LessonScreenContent(
			state = state,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
		)
	}
}

@Composable
private fun LessonScreenContent(
	state: LessonState,
	modifier: Modifier = Modifier
) {


}
