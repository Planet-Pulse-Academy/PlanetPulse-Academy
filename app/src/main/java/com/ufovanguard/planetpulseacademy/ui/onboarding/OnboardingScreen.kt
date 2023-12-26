package com.ufovanguard.planetpulseacademy.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import kotlinx.coroutines.launch

@Preview(showSystemUi = true, device = "id:pixel_3a_xl")
@Composable
private fun OnboardingScreenPreview() {
	PlanetPulseAcademyTheme {
		OnboardingScreenContent(
			state = OnboardingState(),
			navigateTo = {},
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.background)
		)
	}
}

@Preview
@Composable
private fun OnboardingPagePreview() {
	PlanetPulseAcademyTheme {
		OnboardingPage(
			text = "Lorem pisum dolor sit amet",
			title = "Lorem pisum",
			imageRes = R.drawable.ic_app,
			modifier = Modifier
				.background(MaterialTheme.colorScheme.background)
		)
	}
}

@Composable
fun OnboardingScreen(
	viewModel: OnboardingViewModel,
	navigateTo: (Destination) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(viewModel = viewModel) { scaffoldPadding ->
		OnboardingScreenContent(
			state = state,
			navigateTo = navigateTo,
			modifier = Modifier
				.fillMaxSize()
				.padding(scaffoldPadding)
		)
	}

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingScreenContent(
	state: OnboardingState,
	modifier: Modifier = Modifier,
	navigateTo: (Destination) -> Unit
) {

	val coroutineScope = rememberCoroutineScope()

	val pagerState = rememberPagerState(
		pageCount = { 3 }
	)

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly,
		modifier = modifier
			.padding(16.dp)
	) {
		LinearProgressIndicator(
			progress = { pagerState.currentPage / pagerState.pageCount.toFloat() },
			modifier = Modifier
				.fillMaxWidth()
		)

		HorizontalPager(state = pagerState) { page ->
			when (page) {
				0 -> OnboardingPage(
					text = "Lorem pisum dolor sit amet",
					title = "Lorem",
					imageRes = R.drawable.ic_app,
					modifier = Modifier
						.weight(1f)
				)
				1 -> OnboardingPage(
					text = "Lorem pisum dolor sit amet",
					title = "Lorem pisum",
					imageRes = R.drawable.ic_app,
					modifier = Modifier
						.weight(1f)
				)
				2 -> OnboardingPage(
					text = "Lorem pisum dolor sit amet",
					title = "Lorem pisum",
					imageRes = R.drawable.ic_app,
					modifier = Modifier
						.weight(1f)
				)
			}
		}

		Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
			Button(
				onClick = {
					if (pagerState.currentPage == pagerState.pageCount - 1) {
						navigateTo(Destinations.Auth.route)
					} else {
						coroutineScope.launch {
							pagerState.animateScrollToPage(pagerState.currentPage + 1)
						}
					}
				}
			) {
				Text(if (pagerState.currentPage == pagerState.pageCount - 1) "Login" else "Next")
			}

			if (pagerState.currentPage < pagerState.pageCount - 1) {
				TextButton(
					onClick = {
						coroutineScope.launch {
							pagerState.animateScrollToPage(pagerState.pageCount - 1)
						}
					}
				) {
					Text("Skip")
				}
			}
		}
	}
}

@Composable
private fun OnboardingPage(
	text: String,
	title: String,
	imageRes: Int,
	modifier: Modifier = Modifier
) {

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly,
		modifier = modifier
	) {
		Image(
			painter = painterResource(id = imageRes),
			contentDescription = null,
			modifier = Modifier
				.fillMaxWidth()
				.aspectRatio(1f / 1f)
		)

		Text(
			text = title,
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.titleMedium.copy(
				color = LocalContentColor.current
			)
		)

		Text(
			text = text,
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.bodyMedium.copy(
				color = LocalContentColor.current
			)
		)
	}
}
