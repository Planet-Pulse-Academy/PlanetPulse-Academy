package com.ufovanguard.planetpulseacademy.ui.profile

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.common.LocalBottomBarPadding
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun ProfileScreenPreview() {
	PlanetPulseAcademyTheme {
		ProfileScreenContent(
			state = ProfileState(),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel,
	onNavigateUp: () -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel,
		contentWindowInsets = WindowInsets(0, 0, 0, 0)
	) { scaffoldPadding ->
		ProfileScreenContent(
			state = state,
			onNavigateUp = onNavigateUp,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
				.padding(bottom = LocalBottomBarPadding.current)
				.padding(scaffoldPadding)
		)
	}
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreenContent(
	state: ProfileState,
	modifier: Modifier = Modifier,
	onNavigateUp: () -> Unit = {}
) {

	Box(
		contentAlignment = Alignment.TopCenter,
		modifier = modifier
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(0.3f)
				.scale(scaleX = 1.6f, scaleY = 1f)
				.clip(RoundedCornerShape(bottomStartPercent = 100, bottomEndPercent = 100))
				.background(PPATheme.colorScheme.secondary)
		)

		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.statusBarsPadding()
		) {
			TopAppBar(
				title = {
					Text(stringResource(id = R.string.profile))
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color.Transparent,
					navigationIconContentColor = PPATheme.colorScheme.onBackground,
					titleContentColor = PPATheme.colorScheme.onBackground
				),
				windowInsets = WindowInsets(0, 0, 0, 0),
				navigationIcon = {
					IconButton(onClick = onNavigateUp) {
						Icon(
							painter = painterResource(id = R.drawable.ic_arrow_left),
							contentDescription = null
						)
					}
				}
			)

			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				modifier = Modifier
					.fillMaxWidth(0.92f)
			) {
				GlideImage(
					// TODO: Change with user profile image
					model = "https://pbs.twimg.com/media/F0Wrbt9X0AMwjqO?format=jpg&name=large",
					contentDescription = null,
					contentScale = ContentScale.Crop,
					loading = placeholder(ColorDrawable(Color.LightGray.toArgb())),
					modifier = Modifier
						.size(96.dp)
						.clip(CircleShape)
				)

				Column(
					modifier = Modifier
						.weight(1f)
				) {
					Text(
						text = state.userProfile?.name ?: "",
						style = PPATheme.typography.titleLarge.copy(
							fontWeight = FontWeight.Normal
						)
					)

					Text(
						text = "Indonesian",
						style = PPATheme.typography.titleLarge.copy(
							fontWeight = FontWeight.Normal
						)
					)
				}
			}
		}
	}
}
