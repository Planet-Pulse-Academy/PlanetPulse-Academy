package com.ufovanguard.planetpulseacademy.foundation.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme

@Composable
fun LessonCategoryBox(
	name: String,
	modifier: Modifier = Modifier
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.clip(MaterialTheme.shapes.small)
			.background(PPATheme.colorScheme.primary)
	) {
		Text(
			text = name,
			style = PPATheme.typography.labelSmall.copy(
				color = PPATheme.colorScheme.onPrimary
			),
			modifier = Modifier
				.padding(
					vertical = 4.dp,
					horizontal = 8.dp
				)
		)
	}
}
