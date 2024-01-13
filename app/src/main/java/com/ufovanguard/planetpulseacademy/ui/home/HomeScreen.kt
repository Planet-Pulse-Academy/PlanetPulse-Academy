package com.ufovanguard.planetpulseacademy.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun HomeScreenPreview() {
	PlanetPulseAcademyTheme {
		HomeScreenContent(
			state = HomeState(),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

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
			navigateTo = navigateTo,
			modifier = Modifier
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
				.fillMaxSize()
		)
	}

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HomeScreenContent(
	state: HomeState,
	modifier: Modifier = Modifier,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit = { _, _ ->}
) {

	LazyColumn(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		item {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(
					text = stringResource(id = R.string.hi_name, state.userCredential?.name ?: ""),
					style = PPATheme.typography.titleLarge,
					modifier = Modifier
						.padding(start = 16.dp)
						.weight(1f)
				)

				GlideImage(
					// TODO: Change with user profile image
					model = "https://image.tmdb.org/t/p/w235_and_h235_face/bzDpatBvlRXZQAqOdcKj5SDQmWg.jpg",
					contentDescription = null,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.padding(16.dp)
						.clip(CircleShape)
						.size(48.dp)
						.clickable {
							navigateTo(Destinations.Main.profile, null)
						}
				)
			}
		}

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
		
		item { 
			Row(
				modifier = Modifier
					.fillMaxWidth(0.92f)
			) {
				Text(
					text = stringResource(id = R.string.academy),
					style = PPATheme.typography.titleLarge
				)
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
