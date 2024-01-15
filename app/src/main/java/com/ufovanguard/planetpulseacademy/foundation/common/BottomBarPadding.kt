package com.ufovanguard.planetpulseacademy.foundation.common

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Return bottom bar height if present, otherwise return 0 dp
 */
val LocalBottomBarPadding: ProvidableCompositionLocal<Dp> = compositionLocalOf { 0.dp }

