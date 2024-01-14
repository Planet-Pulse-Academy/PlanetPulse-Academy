package com.ufovanguard.planetpulseacademy.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.extension.Zero
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.PPATextField

@Preview
@Composable
private fun AcademyScreenPreview() {
	PlanetPulseAcademyTheme {
		AcademyScreenContent(
			state = AcademyState(),
			modifier = Modifier
				.background(PPATheme.colorScheme.background)
				.fillMaxSize()
		)
	}
}

@Composable
fun AcademyScreen(
	viewModel: AcademyViewModel,
	onNavigateUp: () -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel,
		contentWindowInsets = WindowInsets.Zero
	) { scaffoldPadding ->
		AcademyScreenContent(
			state = state,
			onNavigateUp = onNavigateUp,
			onSearch = viewModel::search,
			onSearchQueryChange = viewModel::setSearchQuery,
			modifier = Modifier
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
				.fillMaxSize()
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AcademyScreenContent(
	state: AcademyState,
	modifier: Modifier = Modifier,
	onSearchQueryChange: (String) -> Unit = {},
	onNavigateUp: () -> Unit = {},
	onSearch: () -> Unit = {}
) {

	LazyColumn(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		item {
			val searchTextFieldTrailingIcon: @Composable () -> Unit = {
				IconButton(
					onClick = {
						onSearchQueryChange("")
					}
				) {
					Icon(
						imageVector = Icons.Rounded.Clear,
						contentDescription = null
					)
				}
			}

			CenterAlignedTopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color.Transparent
				),
				title = {
					Text(
						text = stringResource(id = R.string.discover),
						style = LocalTextStyle.current.copy(
							color = PPATheme.colorScheme.onBackground
						)
					)
				}
			)

			PPATextField(
				value = state.searchQuery,
				onValueChange = onSearchQueryChange,
				containerColor = PPATheme.colorScheme.onBackground,
				singleLine = true,
				keyboardOptions = KeyboardOptions(
					imeAction = ImeAction.Search
				),
				keyboardActions = KeyboardActions(
					onSearch = {
						onSearch()
					}
				),
				placeholder = {
					Text(
						stringResource(id = R.string.search)
					)
				},
				leadingIcon = {
					Icon(
						painter = painterResource(id = R.drawable.ic_search),
						contentDescription = null
					)
				},
				trailingIcon = if (state.searchQuery.isNotBlank()) {
					{ searchTextFieldTrailingIcon() }
				} else null,
				modifier = Modifier
					.fillMaxWidth(0.92f)
			)
		}

		item {
			Spacer(modifier = Modifier.height(16.dp))
		}
	}
}
