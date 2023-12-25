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
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.ui.login.LoginScreen

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
		if (state.userCredential != null) {
			NavHost(
				navController = navController,
				startDestination = if (state.userCredential!!.isLoggedIn) Destinations.home.route
				else Destinations.login.route,
			) {
				composable(Destinations.login.route) { backEntry ->
					LoginScreen(
						viewModel = hiltViewModel(backEntry),
						navigateTo = { dest ->
							navController.navigate(dest.route)
						}
					)
				}

				composable(Destinations.home.route) { backEntry ->

				}
			}
		}
	}

}
