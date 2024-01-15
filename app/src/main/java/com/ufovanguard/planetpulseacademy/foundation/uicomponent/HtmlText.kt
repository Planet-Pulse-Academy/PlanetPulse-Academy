package com.ufovanguard.planetpulseacademy.foundation.uicomponent

import android.text.TextUtils
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme

/**
 * Use this if [text] contains html tags
 */
@Composable
fun HtmlText(
	text: String,
	modifier: Modifier = Modifier,
	maxLines: Int = Int.MAX_VALUE,
	style: TextStyle = PPATheme.typography.bodyMedium
) {

	val density = LocalDensity.current

	AndroidView(
		modifier = modifier,
		factory = { context -> TextView(context) },
		update = {
			it.setLines(maxLines)
			it.ellipsize = TextUtils.TruncateAt.END
			it.textAlignment = when (style.textAlign) {
				TextAlign.Start -> TextView.TEXT_ALIGNMENT_TEXT_START
				TextAlign.End -> TextView.TEXT_ALIGNMENT_TEXT_END
				TextAlign.Center -> TextView.TEXT_ALIGNMENT_CENTER
				else -> TextView.TEXT_ALIGNMENT_TEXT_START
			}

			it.textSize = with(density) { style.fontSize.toPx() }
			it.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
		}
	)
}
