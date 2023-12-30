package com.ufovanguard.planetpulseacademy.ui.quiz

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun QuizScreenPreview() {
	PlanetPulseAcademyTheme {
		QuizScreenContent(
			state = QuizState(),
			modifier = Modifier
				.fillMaxSize()
		)
	}
}

@Composable
fun QuizScreen(
	viewModel: QuizViewModel,
	onNavigateUp: () -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel
	) { scaffoldPadding ->
		QuizScreenContent(
			state = state,
			modifier = Modifier
				.fillMaxSize()
				.padding(scaffoldPadding)
		)
	}

}

@Composable
private fun QuizScreenContent(
	state: QuizState,
	modifier: Modifier = Modifier
) {



}
