package com.ufovanguard.planetpulseacademy.foundation.base.ui

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.anafthdev.comdeo.foundation.extension.toast
import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent.DismissCurrentSnackbar.parse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.withContext

/**
 * Kerangka dasar untuk screen
 *
 * @param contentWindowInsets window insets to be passed to [content] slot via [PaddingValues]
 * params. Scaffold will take the insets into account from the top/bottom only if the [topBar]/
 * [bottomBar] are not present, as the scaffold expect [topBar]/[bottomBar] to handle insets
 * instead. Any insets consumed by other insets padding modifiers or [consumeWindowInsets] on a
 * parent layout will be excluded from [contentWindowInsets].
 *
 * @author kafri8889
 */
@Composable
fun <STATE: Parcelable> BaseScreenWrapper(
	viewModel: BaseViewModel<STATE>,
	modifier: Modifier = Modifier,
	contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
	containerColor: Color = MaterialTheme.colorScheme.background,
	onEvent: (UiEvent) -> Unit = {},
	topBar: @Composable () -> Unit = {},
	bottomBar: @Composable () -> Unit = {},
	floatingActionButton: @Composable () -> Unit = {},
	content: @Composable (scaffoldPadding: PaddingValues) -> Unit
) {

	val context = LocalContext.current

	val hostState = remember { SnackbarHostState() }

	LaunchedEffect(Unit) {
		viewModel.uiEvent.filterNotNull().collectLatest { event ->
			withContext(Dispatchers.Main) { onEvent(event) }
			when (event) {
				is UiEvent.DismissCurrentSnackbar -> hostState.currentSnackbarData?.dismiss()
				is UiEvent.ShowSnackbar -> {
					val result = hostState.showSnackbar(
						message = event.message.parse(context),
						actionLabel = event.actionLabel?.parse(context),
						withDismissAction = event.withDismissAction,
						duration = event.duration
					)

					viewModel.sendEventResult(
						when (result) {
							Dismissed -> UiEventResult.Dismissed(event)
							ActionPerformed -> UiEventResult.ActionPerformed(event)
						}
					)
				}

				is UiEvent.ShowToast -> {
					context.toast(event.message.parse(context), event.length)
				}
			}
		}
	}

	Scaffold(
		topBar = topBar,
		bottomBar = bottomBar,
		containerColor = containerColor,
		contentWindowInsets = contentWindowInsets,
		floatingActionButton = floatingActionButton,
		modifier = modifier,
		snackbarHost = {
			SnackbarHost(hostState = hostState)
		}
	) { scaffoldPadding ->
		content(scaffoldPadding)
	}

}
