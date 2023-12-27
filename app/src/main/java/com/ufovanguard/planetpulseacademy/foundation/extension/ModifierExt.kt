package com.ufovanguard.planetpulseacademy.foundation.extension

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * @param distance the width of the indicator + the spacing
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.slidingLineTransition(pagerState: PagerState, distance: Float) =
	graphicsLayer {
		val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
		translationX = scrollPosition * distance
	}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.wormTransition(
	pagerState: PagerState,
	color: Color
) = drawBehind {
		val distance = size.width + 10.dp.roundToPx()
		val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
		val wormOffset = (scrollPosition % 1) * 2

		val xPos = scrollPosition.toInt() * distance
		val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
		val tail = xPos + size.width + 1f.coerceAtMost(wormOffset) * distance

		val worm = RoundRect(
			head, 0f, tail, size.height,
			CornerRadius(50f)
		)

		val path = Path().apply { addRoundRect(worm) }
		drawPath(path = path, color = color)
	}
