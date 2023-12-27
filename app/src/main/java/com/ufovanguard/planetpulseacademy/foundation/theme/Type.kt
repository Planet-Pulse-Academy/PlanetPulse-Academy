package com.ufovanguard.planetpulseacademy.foundation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ufovanguard.planetpulseacademy.R

val Baloo = FontFamily(
//	Font(R.font.inter_thin, FontWeight.Thin),
//	Font(R.font.inter_extra_light, FontWeight.ExtraLight),
//	Font(R.font.inter_light, FontWeight.Light),
	Font(R.font.baloo_regular, FontWeight.Normal),
//	Font(R.font.inter_medium, FontWeight.Medium),
//	Font(R.font.inter_semi_bold, FontWeight.SemiBold),
//	Font(R.font.inter_bold, FontWeight.Bold),
//	Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
)

val Typography = Typography(
	displayLarge = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 57.sp,
		lineHeight = 64.sp,
		letterSpacing = (-0.25).sp,
	),
	displayMedium = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 45.sp,
		lineHeight = 52.sp,
		letterSpacing = 0.sp,
	),
	displaySmall = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 36.sp,
		lineHeight = 44.sp,
		letterSpacing = 0.sp,
	),
	headlineLarge = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 32.sp,
		lineHeight = 40.sp,
		letterSpacing = 0.sp,
	),
	headlineMedium = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 28.sp,
		lineHeight = 36.sp,
		letterSpacing = 0.sp,
	),
	headlineSmall = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 24.sp,
		lineHeight = 32.sp,
		letterSpacing = 0.sp,
	),
	titleLarge = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 22.sp,
		lineHeight = 28.sp,
		letterSpacing = 0.sp,
	),
	titleMedium = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.Medium,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.1.sp,
	),
	titleSmall = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.1.sp,
	),
	labelLarge = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.1.sp,
	),
	bodyLarge = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp,
	),
	bodyMedium = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.25.sp,
	),
	bodySmall = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.W400,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.4.sp,
	),
	labelMedium = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.Medium,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp,
	),
	labelSmall = TextStyle(
		fontFamily = Baloo,
		fontWeight = FontWeight.Medium,
		fontSize = 11.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp,
	),
)

val PPATypography: Typography
	@Composable
	get() = Typography.copy(
		displayLarge = Typography.displayLarge.copy(color = LocalPPAContentColor.current),
		displayMedium = Typography.displayMedium.copy(color = LocalPPAContentColor.current),
		displaySmall = Typography.displaySmall.copy(color = LocalPPAContentColor.current),
		headlineLarge = Typography.headlineLarge.copy(color = LocalPPAContentColor.current),
		headlineMedium = Typography.headlineMedium.copy(color = LocalPPAContentColor.current),
		headlineSmall = Typography.headlineSmall.copy(color = LocalPPAContentColor.current),
		titleLarge = Typography.titleLarge.copy(color = LocalPPAContentColor.current),
		titleMedium = Typography.titleMedium.copy(color = LocalPPAContentColor.current),
		titleSmall = Typography.titleSmall.copy(color = LocalPPAContentColor.current),
		labelLarge = Typography.labelLarge.copy(color = LocalPPAContentColor.current),
		bodyLarge = Typography.bodyLarge.copy(color = LocalPPAContentColor.current),
		bodyMedium = Typography.bodyMedium.copy(color = LocalPPAContentColor.current),
		bodySmall = Typography.bodySmall.copy(color = LocalPPAContentColor.current),
		labelMedium = Typography.labelMedium.copy(color = LocalPPAContentColor.current),
		labelSmall = Typography.labelSmall.copy(color = LocalPPAContentColor.current),
	)
