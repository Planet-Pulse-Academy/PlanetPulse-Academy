package com.ufovanguard.planetpulseacademy.data.datasource.local

import com.ufovanguard.planetpulseacademy.data.model.LessonCategory

object LocalLessonCategoryDataProvider {

	val category1 = LessonCategory(
		id = "65994540766cbbec96ca299d",
		name = "Environment",
		description = "Environment"
	)

	val category2 = LessonCategory(
		id = "65994540766cbbec96ca299d",
		name = "Life on land",
		description = "Life on land"
	)

	val category3 = LessonCategory(
		id = "65994540766cbbec96ca299d",
		name = "Life below water",
		description = "Life below water"
	)

	val values = listOf(
		category1,
		category2,
		category3,
	)

}