package com.ufovanguard.planetpulseacademy.foundation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun PPATextFieldPreview() {
	PlanetPulseAcademyTheme {
		PPATextField(
			value = TextFieldValue(""),
			isError = true,
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
			supportingText = {
				Text("Name cannot be empty")
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
	isError: Boolean = false,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: TextStyle = PPATextFieldDefaults.textStyle,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = false,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	minLines: Int = 1,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	onTextLayout: (TextLayoutResult) -> Unit = {},
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	cursorBrush: Brush = SolidColor(PPATheme.colorScheme.primary),
	containerColor: Color = PPATheme.colorScheme.primaryContainer,
	supportingText: @Composable (() -> Unit)? = null,
	placeholder: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
) {

	PPATextFieldContainer(
		paddingValues = paddingValues,
		shape = shape,
		isError = isError,
		containerColor = containerColor,
		supportingText = supportingText,
		trailingIcon = trailingIcon,
		leadingIcon = leadingIcon,
		textField = {
			BasicTextField(
				value = value,
				onValueChange = onValueChange,
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
					DecorationBox(
						value = value.text,
						textStyle = textStyle,
						placeholder = placeholder,
						innerTextField = innerTextField,
						modifier = Modifier
							.fillMaxWidth()
					)
				},
				modifier = modifier
					.weight(1f)
			)
		}
	)
}

@Composable
fun PPATextField(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	paddingValues: PaddingValues = PPATextFieldDefaults.contentPadding,
	shape: Shape = MaterialTheme.shapes.medium,
	isError: Boolean = false,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: TextStyle = PPATextFieldDefaults.textStyle,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = false,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	minLines: Int = 1,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	onTextLayout: (TextLayoutResult) -> Unit = {},
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	cursorBrush: Brush = SolidColor(PPATheme.colorScheme.primary),
	containerColor: Color = PPATheme.colorScheme.primaryContainer,
	contentColor: Color = PPATheme.colorScheme.contentColorFor(containerColor),
	supportingText: @Composable (() -> Unit)? = null,
	placeholder: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
) {

	val mTextStyle = remember(textStyle, contentColor) {
		textStyle.copy(
			color = contentColor
		)
	}

	PPATextFieldContainer(
		shape = shape,
		isError = isError,
		modifier = modifier,
		paddingValues = paddingValues,
		containerColor = containerColor,
		contentColor = contentColor,
		supportingText = supportingText,
		trailingIcon = trailingIcon,
		leadingIcon = leadingIcon,
		textField = {
			BasicTextField(
				value = value,
				onValueChange = onValueChange,
				enabled = enabled,
				readOnly = readOnly,
				textStyle = mTextStyle,
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
					DecorationBox(
						value = value,
						textStyle = mTextStyle,
						placeholder = placeholder,
						innerTextField = innerTextField,
						modifier = Modifier
							.fillMaxWidth()
					)
				},
				modifier = Modifier
					.weight(1f)
			)
		}
	)
}

@Composable
private fun PPATextFieldContainer(
	modifier: Modifier = Modifier,
	paddingValues: PaddingValues = PPATextFieldDefaults.contentPadding,
	shape: Shape = MaterialTheme.shapes.medium,
	isError: Boolean = false,
	containerColor: Color = PPATheme.colorScheme.primaryContainer,
	contentColor: Color = PPATheme.colorScheme.contentColorFor(containerColor),
	supportingText: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
	textField: @Composable RowScope.() -> Unit
) {
	Column(modifier = modifier) {
		Box(
			contentAlignment = Alignment.CenterStart,
			modifier = Modifier
				.heightIn(min = PPATextFieldDefaults.minHeight)
				.clip(shape)
				.background(containerColor)
				.border(
					width = 1.dp,
					color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
					shape = shape
				)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				modifier = Modifier
					.padding(paddingValues)
					.fillMaxWidth()
			) {
				CompositionLocalProvider(LocalContentColor provides contentColor) {
					leadingIcon?.invoke()
				}

				textField()

				CompositionLocalProvider(LocalContentColor provides contentColor) {
					trailingIcon?.invoke()
				}
			}
		}

		if (supportingText != null && isError) {
			ProvideTextStyle(
				content = supportingText,
				value = PPATheme.typography.labelMedium.copy(
					color = contentColor
				)
			)
		}
	}
}

@Composable
private fun DecorationBox(
	value: String,
	textStyle: TextStyle,
	modifier: Modifier = Modifier,
	innerTextField: @Composable () -> Unit,
	placeholder: @Composable (() -> Unit)?
) {
	Box(
		contentAlignment = Alignment.CenterStart,
		modifier = modifier
	) {
		if (placeholder != null && value.isEmpty()) {
			ProvideTextStyle(
				content = placeholder,
				value = textStyle.copy(
					color = textStyle.color.copy(alpha = 0.7f)
				)
			)
		}

		innerTextField()
	}
}

object PPATextFieldDefaults {

	val textStyle: TextStyle
		@Composable
		get() = PPATheme.typography.bodyMedium.copy(
			color = PPATheme.colorScheme.inverseOnBackground
		)

	val contentPadding = PaddingValues(
		vertical = 4.dp,
		horizontal = 12.dp
	)

	val minHeight = 56.dp

}
