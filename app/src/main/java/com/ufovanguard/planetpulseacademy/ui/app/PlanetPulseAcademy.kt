package com.ufovanguard.planetpulseacademy.ui.app

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.ui.home.HomeScreen
import com.ufovanguard.planetpulseacademy.ui.login.LoginScreen
import com.ufovanguard.planetpulseacademy.ui.onboarding.OnboardingScreen
import com.ufovanguard.planetpulseacademy.ui.register.RegisterScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun PlanetPulseAcademy(
	viewModel: PlanetPulseAcademyViewModel
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	val modalBottomSheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		skipHalfExpanded = true
	)

	val bottomSheetNavigator = remember {
		BottomSheetNavigator(modalBottomSheetState)
	}

	val navController = rememberNavController(bottomSheetNavigator)

	ModalBottomSheetLayout(
		bottomSheetNavigator = bottomSheetNavigator,
		sheetBackgroundColor = MaterialTheme.colorScheme.surface,
		sheetContentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surface),
		scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
		sheetShape = MaterialTheme.shapes.large
	) {
		if (state.userCredential != null && state.userPreference != null) {
			NavHost(
				navController = navController,
				startDestination = when {
					state.userPreference!!.isFirstInstall -> Destinations.Onboarding.route.route
					!state.userCredential!!.isLoggedIn -> Destinations.Auth.route.route
					else -> Destinations.Main.route.route
				}
			) {
				navigation(
					startDestination = Destinations.Onboarding.onboarding.route,
					route = Destinations.Onboarding.route.route
				) {
					composable(Destinations.Onboarding.onboarding.route) { backEntry ->
						OnboardingScreen(
							viewModel = hiltViewModel(backEntry),
							navigateTo = { dest ->
								navController.navigate(dest.route)
							}
						)
					}
				}

				navigation(
					startDestination = Destinations.Auth.login.route,
					route = Destinations.Auth.route.route
				) {
					composable(Destinations.Auth.register.route) { backEntry ->
						RegisterScreen(
							viewModel = hiltViewModel(backEntry),
							navigateTo = { dest ->
								navController.navigate(dest.route)
							}
						)
					}

					composable(Destinations.Auth.login.route) { backEntry ->
						LoginScreen(
							viewModel = hiltViewModel(backEntry),
							navigateTo = { dest ->
								navController.navigate(dest.route)
							}
						)
					}
				}

				navigation(
					startDestination = Destinations.Main.home.route,
					route = Destinations.Main.route.route
				) {
					composable(Destinations.Main.home.route) { backEntry ->
						HomeScreen(
							viewModel = hiltViewModel(backEntry),
							navigateTo = { dest ->
								navController.navigate(dest.route)
							}
						)
					}

					composable(Destinations.Main.quiz.route) { backEntry ->
						HomeScreen(
							viewModel = hiltViewModel(backEntry),
							navigateTo = { dest ->
								navController.navigate(dest.route)
							}
						)
					}
				}
			}
		}
	}

}
