package com.ufovanguard.planetpulseacademy.ui.app

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ufovanguard.planetpulseacademy.data.Destinations

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun PlanetPulseAcademy() {

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
		NavHost(
			navController = navController,
			startDestination = Destinations.home.route,
		) {
			composable(Destinations.home.route) { backEntry ->

			}
		}
	}

}
