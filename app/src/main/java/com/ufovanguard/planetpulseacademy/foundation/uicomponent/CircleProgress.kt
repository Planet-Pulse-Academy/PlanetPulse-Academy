package com.ufovanguard.planetpulseacademy.foundation.uicomponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Circle progress with center content
 *
 * @param progressAngle from `0f` until `360f`
 */
@Composable
fun CircleProgress(
	progressAngle: () -> Float,
	modifier: Modifier = Modifier,
	width: Dp = 8.dp,
	progressColor: Color = MaterialTheme.colorScheme.primary,
	progressBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
	centerContent: @Composable () -> Unit
) {

	val density = LocalDensity.current

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
	) {
		centerContent()

		Canvas(
			modifier = Modifier
				.fillMaxSize()
				.padding(8.dp),
			onDraw = {
				drawArc(
					color = progressBackgroundColor,
					startAngle = -90f,
					sweepAngle = 360f,
					useCenter = false,
					style = Stroke(width = with(density) { width.toPx() })
				)

				drawArc(
					color = progressColor,
					startAngle = -90f,
					sweepAngle = progressAngle(),
					useCenter = false,
					style = Stroke(
						width = with(density) { width.toPx() },
						cap = StrokeCap.Round
					)
				)
			}
		)
	}
}
