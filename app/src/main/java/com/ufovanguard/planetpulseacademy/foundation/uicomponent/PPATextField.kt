package com.ufovanguard.planetpulseacademy.foundation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun PPATextFieldPreview() {
	PlanetPulseAcademyTheme {
		PPATextField(
			value = TextFieldValue(""),
			onValueChange = {},
			placeholder = {
				Text("Placeholder")
			},
			leadingIcon = {
				Image(
					painter = painterResource(id = R.drawable.ic_app),
					contentDescription = null,
					modifier = Modifier
						.size(24.dp)
				)
			},
			modifier = Modifier
				.fillMaxWidth()
		)
	}
}

@Composable
fun PPATextField(
	value: TextFieldValue,
	onValueChange: (TextFieldValue) -> Unit,
	modifier: Modifier = Modifier,
	paddingValues: PaddingValues = PPATextFieldDefaults.contentPadding,
	shape: Shape = MaterialTheme.shapes.medium,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: TextStyle = TextStyle.Default.copy(
		color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surfaceContainer)
	),
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = false,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	minLines: Int = 1,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	onTextLayout: (TextLayoutResult) -> Unit = {},
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
	containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
	placeholder: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
) {

	Box(
		contentAlignment = Alignment.CenterStart,
		modifier = modifier
			.heightIn(min = PPATextFieldDefaults.minHeight)
			.clip(shape)
			.background(containerColor)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.padding(paddingValues)
		) {
			leadingIcon?.invoke()

			BasicTextField(
				value = value,
				onValueChange = onValueChange,
				modifier = modifier,
				enabled = enabled,
				readOnly = readOnly,
				textStyle = textStyle,
				keyboardOptions = keyboardOptions,
				keyboardActions = keyboardActions,
				singleLine = singleLine,
				maxLines = maxLines,
				minLines = minLines,
				visualTransformation = visualTransformation,
				onTextLayout = onTextLayout,
				interactionSource = interactionSource,
				cursorBrush = cursorBrush,
				decorationBox = { innerTextField ->
					Box(
						contentAlignment = Alignment.CenterStart
					) {
						if (placeholder != null && value.text.isEmpty()) {
							ProvideTextStyle(
								content = placeholder,
								value = textStyle.copy(
									color = textStyle.color.copy(alpha = 0.48f)
								)
							)
						}

						innerTextField()
					}
				}
			)

			trailingIcon?.invoke()
		}
	}
}

object PPATextFieldDefaults {

	val contentPadding = PaddingValues(12.dp)

	val minHeight = 56.dp

}
