package com.ufovanguard.planetpulseacademy.ui.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.extension.slidingLineTransition
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import kotlinx.coroutines.launch

@Preview(showSystemUi = true, device = "id:pixel_3a_xl")
@Composable
private fun OnboardingScreenPreview() {
	PlanetPulseAcademyTheme {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		) {
			OnboardingScreenContent(
				state = OnboardingState(),
				navigateTo = { _, _ ->},
				modifier = Modifier
					.fillMaxWidth(0.82f)
					.fillMaxHeight()
			)
		}
	}
}

//@Preview
@Composable
private fun OnboardingPagePreview() {
	PlanetPulseAcademyTheme {
		OnboardingPage(
			title = "Lorem pisum dolor sit amet",
			imageRes = R.drawable.ic_app,
			modifier = Modifier
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun OnboardingScreen(
	viewModel: OnboardingViewModel,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(viewModel = viewModel) { scaffoldPadding ->
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		) {
			OnboardingScreenContent(
				state = state,
				navigateTo = navigateTo,
				modifier = Modifier
					.fillMaxWidth(0.82f)
					.fillMaxHeight()
					.padding(scaffoldPadding)
			)
		}
	}

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingScreenContent(
	state: OnboardingState,
	modifier: Modifier = Modifier,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit
) {

	val coroutineScope = rememberCoroutineScope()

	val pagerState = rememberPagerState(
		pageCount = { 3 }
	)

	val constraintSet = ConstraintSet {
		val (
			pager,
			pagerIndicator,
			buttons
		) = createRefsFor(
			"pager",
			"pagerIndicator",
			"buttons"
		)

		constrain(pager) {
			top.linkTo(parent.top)
			bottom.linkTo(pagerIndicator.top)

			centerHorizontallyTo(parent)
		}

		constrain(pagerIndicator) {
			top.linkTo(pager.bottom)
			bottom.linkTo(buttons.top)

			centerHorizontallyTo(parent)
		}

		constrain(buttons) {
			bottom.linkTo(parent.bottom)
			top.linkTo(pagerIndicator.bottom)

			centerHorizontallyTo(pager)
		}
	}

	ConstraintLayout(
		constraintSet = constraintSet,
		modifier = modifier
	) {
		HorizontalPager(
			state = pagerState,
			modifier = Modifier
				.layoutId("pager")
		) { page ->
			when (page) {
				0 -> OnboardingPage(
					title = "Lorem pisum dolor sit amet",
					imageRes = R.drawable.ic_app,
					modifier = Modifier
				)
				1 -> OnboardingPage(
					title = "Lorem pisum dolor sit amet",
					imageRes = R.drawable.ic_app,
					modifier = Modifier
				)
				2 -> OnboardingPage(
					title = "Lorem pisum dolor sit amet",
					imageRes = R.drawable.ic_app,
					modifier = Modifier
				)
			}
		}

		BoxWithConstraints(
			contentAlignment = Alignment.CenterStart,
			modifier = Modifier
				.layoutId("pagerIndicator")
		) {
			val spacing = 16.dp
			val width = maxWidth / pagerState.pageCount - (spacing / 2)
			val distance = with(LocalDensity.current) {
				width.plus(spacing).toPx()
			}

			Row(
				horizontalArrangement = Arrangement.spacedBy(spacing),
				modifier = Modifier
					.fillMaxWidth()
					.layoutId("pagerIndicator")
			) {
				for (i in 0 until pagerState.pageCount) {
					Box(
						modifier = Modifier
							.size(
								width = width,
								height = 8.dp
							)
							.background(
								shape = CircleShape,
								color = PPATheme.colorScheme.onBackground.copy(0.5f)
							)
					)
				}
			}

			Box(
				modifier = Modifier
					.slidingLineTransition(pagerState, distance)
					.size(
						width = width,
						height = 8.dp
					)
					.background(
						shape = CircleShape,
						color = PPATheme.colorScheme.onBackground
					)
			)
		}

		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.layoutId("buttons")
		) {
			Button(
				shape = MaterialTheme.shapes.medium,
				colors = ButtonDefaults.buttonColors(
					containerColor = PPATheme.colorScheme.button
				),
				onClick = {
					if (pagerState.currentPage == pagerState.pageCount - 1) {
						navigateTo(Destinations.Auth.route) {
							popUpTo(Destinations.Onboarding.route.route) {
								inclusive = true
							}
						}
					} else {
						coroutineScope.launch {
							pagerState.animateScrollToPage(
								page = pagerState.currentPage + 1,
								animationSpec = tween(512)
							)
						}
					}
				},
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(
					text = stringResource(
						id = if (pagerState.currentPage == pagerState.pageCount - 1) R.string.sign_in
						else R.string.next
					)
				)
			}

			TextButton(
				shape = MaterialTheme.shapes.medium,
				colors = ButtonDefaults.textButtonColors(
					contentColor = PPATheme.colorScheme.onBackground
				),
				onClick = {
					// if current page >= page count - 1, navigate to register screen
					// otherwise skip to last page
					if (pagerState.currentPage >= pagerState.pageCount - 1) {
						// Add login screen to back stack before navigate to register screen
						navigateTo(Destinations.Auth.login) {
							popUpTo(Destinations.Onboarding.onboarding.route) {
								inclusive = true
							}
						}

						navigateTo(Destinations.Auth.register, null)
					} else {
						coroutineScope.launch {
							pagerState.animateScrollToPage(pagerState.pageCount - 1)
						}
					}
				},
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(
					text = stringResource(
						id = if (pagerState.currentPage < pagerState.pageCount - 1) R.string.skip
						else R.string.sign_up
					)
				)
			}
		}
	}
}

@Composable
private fun OnboardingPage(
	title: String,
	imageRes: Int,
	modifier: Modifier = Modifier
) {

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp),
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
			style = MaterialTheme.typography.titleLarge.copy(
				fontWeight = FontWeight.Bold,
				color = PPATheme.colorScheme.onBackground
			)
		)
	}
}
