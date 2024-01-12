package com.ufovanguard.planetpulseacademy.foundation.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview(device = "spec:width=392.7dp,height=1050dp,dpi=440")
@Composable
private fun StageListPreview() {
	PlanetPulseAcademyTheme {
		StageList(
			modifier = Modifier
				.fillMaxSize()
		)
	}
}

@Composable
fun StageList(
	modifier: Modifier = Modifier
) {

	val nodeSize = 64.dp
	val zigzagCount = 4
	val offsetIncrement = nodeSize * 0.75f

	var listBounds by remember { mutableStateOf(Rect.Zero) }

	val context = LocalContext.current
	BoxWithConstraints(
		modifier = modifier
	) {
		LazyColumn(
			modifier = Modifier
				.matchParentSize()
				.onGloballyPositioned { coordinates ->
					listBounds = coordinates.boundsInParent()
				}
		) {
			items(50) { index ->
				Box(
					modifier = Modifier
						.offset {
							IntOffset(
								x = (listBounds.center.x / 2.5).toInt() + (offsetIncrement * (index % zigzagCount)).toPx().let {
									val currentX = it.toInt()

									// Calculate the zigzag pattern based on the row
									val row = index / zigzagCount
									val adjustedX = if (row % 2 == 0) {
										// If it's an even row, stay in the current position
										currentX
									} else {
										// If it's an odd row, adjust based on the zigzag pattern
										val zigzagIndex = zigzagCount - (index % zigzagCount)
										(offsetIncrement * zigzagIndex).toPx().toInt()
									}

									adjustedX
								},
								y = 0
							)
						}
						.size(nodeSize)
						.clip(CircleShape)
						.background(Color.Black)
						.clickable {

						}
				)
			}
		}
	}

}
