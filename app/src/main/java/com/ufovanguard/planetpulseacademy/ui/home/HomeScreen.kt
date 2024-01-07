package com.ufovanguard.planetpulseacademy.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme

@Composable
fun HomeScreen(
	viewModel: HomeViewModel,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel
	) { scaffoldPadding ->
		HomeScreenContent(
			state = state,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
		)
	}

}

@Composable
private fun HomeScreenContent(
	state: HomeState,
	modifier: Modifier = Modifier
) {

	LazyColumn(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		item {
			Card(
				modifier = Modifier
					.fillMaxWidth(0.92f)
					.aspectRatio(2f / 1f)
			) {

			}
		}

		item {
			BoxWithConstraints(
				modifier = Modifier
					.fillMaxWidth(0.92f)
			) {
				LazyRow(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					modifier = Modifier
						.fillMaxWidth()
				) {
					items(4) {
						Card(
							modifier = Modifier
								.width(this@BoxWithConstraints.maxWidth / 1.4f)
								.height(96.dp)
						) {

						}
					}
				}
			}
		}

		items(6) {
			Card(
				modifier = Modifier
					.fillMaxWidth(0.92f)
					.aspectRatio(2f / 1f)
			) {

			}
		}
	}

}
