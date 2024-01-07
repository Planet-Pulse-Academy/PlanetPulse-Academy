package com.ufovanguard.planetpulseacademy.foundation.extension

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

// TODO: Ada bug ketika user ngepaste text,
//  reproduce => misalnya text "abcdefg", select text dari range "d" sampe "g", habis itu paste text apapun yang panjang
fun TextFieldValue.preventSelection(oldValue: TextFieldValue): TextFieldValue {
	return copy(
		selection = if (selection.length > 1) oldValue.selection
		else TextRange(selection.start)
	)
}