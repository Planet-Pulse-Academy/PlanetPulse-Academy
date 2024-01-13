package com.ufovanguard.planetpulseacademy.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.datasource.local.LocalQuestionDataProvider
import com.ufovanguard.planetpulseacademy.data.model.SelectableQuestionOption
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun QuizScreenPreview() {
	PlanetPulseAcademyTheme {
		QuizScreenContent(
			state = QuizState(
				selectableQuestionOptions = LocalQuestionDataProvider.values.map { SelectableQuestionOption(it) },
				currentQuestion = SelectableQuestionOption(LocalQuestionDataProvider.values[0]),
				currentQuestionIndex = 0
			),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun QuizScreen(
	viewModel: QuizViewModel,
	onNavigateUp: () -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel,
		contentWindowInsets = WindowInsets(0)
	) { scaffoldPadding ->
		QuizScreenContent(
			state = state,
			onNavigateUp = onNavigateUp,
			onQuestionChanged = viewModel::changeQuestion,
			onSelectedOptionChanged = viewModel::changeSelectedQuestionOption,
			modifier = Modifier
				.background(PPATheme.colorScheme.background)
				.fillMaxSize()
				.padding(scaffoldPadding)
		)
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizScreenContent(
	state: QuizState,
	modifier: Modifier = Modifier,
	onSelectedOptionChanged: (index: Int) -> Unit = {},
	onQuestionChanged: (index: Int) -> Unit = {},
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
				title = {},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color.Transparent,
					navigationIconContentColor = PPATheme.colorScheme.onBackground,
					titleContentColor = PPATheme.colorScheme.onBackground
				),
				windowInsets = WindowInsets(0),
				navigationIcon = {
					IconButton(onClick = onNavigateUp) {
						Icon(
							painter = painterResource(id = R.drawable.ic_arrow_left),
							contentDescription = null
						)
					}
				}
			)

			LevelBar(
				questionCount = state.selectableQuestionOptions.size,
				onClick = onQuestionChanged,
				currentQuestionIndex = state.currentQuestionIndex,
				modifier = Modifier
					.fillMaxWidth(0.92f)
					.clip(MaterialTheme.shapes.small)
					.background(PPATheme.colorScheme.onBackground)
			)

			QuizCard(
				selectableQuestionOption = state.currentQuestion,
				onSelectedOptionChanged = onSelectedOptionChanged,
				modifier = Modifier
					.fillMaxWidth(0.92f)
			)

			QuestionNavigationButton(
				questionCount = state.selectableQuestionOptions.size,
				currentQuestionIndex = state.currentQuestionIndex,
				onQuestionChanged = onQuestionChanged,
				onFinish = {},
				modifier = Modifier
					.fillMaxWidth(0.92f)
			)
		}
	}

}

@Composable
private fun LevelBar(
	questionCount: Int,
	currentQuestionIndex: Int,
	modifier: Modifier = Modifier,
	onClick: (index: Int) -> Unit
) {

	val lazyListState = rememberLazyListState()
	val firstItemIndex by remember {
		derivedStateOf {
			lazyListState.firstVisibleItemIndex
		}
	}

	val lastItem by remember {
		derivedStateOf {
			lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
		}
	}

	LaunchedEffect(currentQuestionIndex, lastItem, firstItemIndex) {
		lastItem?.let { lastVisibleItem ->
			when (currentQuestionIndex) {
				in (firstItemIndex + 1)..<lastVisibleItem.index -> {
					// Do Nothing
				}
				else -> lazyListState.animateScrollToItem(currentQuestionIndex)
			}
		}
	}

	LazyRow(
		state = lazyListState,
		modifier = modifier
	) {
		items(questionCount) { i ->
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.size(32.dp)
					.background(
						if (currentQuestionIndex == i) PPATheme.colorScheme.primary
						else Color.Transparent
					)
					.clickable {
						onClick(i)
					}
			) {
				Text(
					text = (i + 1).toString(),
					style = PPATheme.typography.titleMedium.copy(
						color = PPATheme.colorScheme.inverseOnBackground
					)
				)
			}
		}
	}
}

@Composable
fun QuizCard(
	selectableQuestionOption: SelectableQuestionOption?,
	modifier: Modifier = Modifier,
	containerColor: Color = PPATheme.colorScheme.onBackground,
	onSelectedOptionChanged: (index: Int) -> Unit
) {

	Card(
		shape = MaterialTheme.shapes.medium,
		colors = CardDefaults.cardColors(
			containerColor = containerColor
		),
		modifier = modifier
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.padding(16.dp)
		) {
			Text(
				text = selectableQuestionOption?.question?.text ?: "",
				style = PPATheme.typography.bodyLarge.copy(
					color = PPATheme.colorScheme.inverseOnBackground
				)
			)

			if (selectableQuestionOption?.question?.options?.isNotEmpty() == true) {
				for (i in selectableQuestionOption.question.options.indices) {
					val isSelected = selectableQuestionOption.selectedOptionIndex == i

					key(selectableQuestionOption.question.options[i]) {
						Box(
							contentAlignment = Alignment.CenterStart,
							modifier = Modifier
								.fillMaxWidth()
								.clip(MaterialTheme.shapes.small)
								.background(
									if (isSelected) PPATheme.colorScheme.primary
									else Color.Transparent
								)
								.border(
									width = 1.dp,
									shape = MaterialTheme.shapes.small,
									color = if (isSelected) Color.Transparent
									else MaterialTheme.colorScheme.outline
								)
								.clickable {
									onSelectedOptionChanged(if (isSelected) -1 else i)
								}
						) {
							Text(
								text = selectableQuestionOption.question.options[i],
								style = PPATheme.typography.bodyMedium.copy(
									color = if (isSelected) PPATheme.colorScheme.onBackground
									else PPATheme.colorScheme.inverseOnBackground
								),
								modifier = Modifier
									.padding(16.dp)
							)
						}
					}
				}
			}
		}
	}

}

@Composable
private fun QuestionNavigationButton(
	questionCount: Int,
	currentQuestionIndex: Int,
	modifier: Modifier = Modifier,
	onQuestionChanged: (index: Int) -> Unit,
	onFinish: () -> Unit
) {

	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		Box(
			modifier = Modifier
				.weight(0.5f)
		) {
			if (currentQuestionIndex > 0) {
				TextButton(
					shape = MaterialTheme.shapes.small,
					contentPadding = ButtonDefaults.TextButtonWithIconContentPadding,
					colors = ButtonDefaults.textButtonColors(
						contentColor = PPATheme.colorScheme.onBackground
					),
					onClick = {
						onQuestionChanged(currentQuestionIndex - 1)
					},
					modifier = Modifier
						.fillMaxWidth()
				) {
					Icon(
						painter = painterResource(id = R.drawable.ic_arrow_left),
						contentDescription = null,
						tint = PPATheme.colorScheme.onBackground,
						modifier = Modifier
							.size(ButtonDefaults.IconSize)
					)

					Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

					Text(text = stringResource(id = R.string.previous))
				}
			}
		}

		Box(
			modifier = Modifier
				.weight(0.5f)
		) {
			Button(
				shape = MaterialTheme.shapes.small,
				contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
				colors = ButtonDefaults.buttonColors(
					containerColor = PPATheme.colorScheme.button
				),
				onClick = {
					if (currentQuestionIndex >= questionCount - 1) onFinish()
					else onQuestionChanged(currentQuestionIndex + 1)
				},
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(
					text = stringResource(
						id = if (currentQuestionIndex < questionCount - 1) R.string.next
						else R.string.finish
					)
				)

				if (currentQuestionIndex < questionCount - 1) {
					Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

					Icon(
						painter = painterResource(id = R.drawable.ic_arrow_right),
						contentDescription = null,
						tint = PPATheme.colorScheme.onBackground,
						modifier = Modifier
							.size(ButtonDefaults.IconSize)
					)
				}
			}
		}
	}
}
