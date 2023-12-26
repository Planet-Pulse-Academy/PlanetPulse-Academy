package com.ufovanguard.planetpulseacademy.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper

@Composable
fun HomeScreen(
	viewModel: HomeViewModel,
	navigateTo: (Destination) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel
	) { scaffoldPadding ->
		HomeScreenContent(
			state = state,
			modifier = Modifier
				.fillMaxSize()
				.padding(scaffoldPadding)
		)
	}

}

@Composable
private fun HomeScreenContent(
	state: HomeState,
	modifier: Modifier = Modifier
) {

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
	) {
		Text("Home Screen")
	}

}
