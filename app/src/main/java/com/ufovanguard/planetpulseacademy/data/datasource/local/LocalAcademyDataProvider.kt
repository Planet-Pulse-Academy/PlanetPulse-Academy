package com.ufovanguard.planetpulseacademy.data.datasource.local

import com.ufovanguard.planetpulseacademy.data.model.Academy

object LocalAcademyDataProvider {

	val academy1 = Academy(
		id = "65a28279e95f2da9ec4bfd7d",
		title = "Lingkungan Hidup",
		idLesson = "65a28279e95f2da9ec4bfd7d",
		publicId = "",
		description = "Pada materi kali ini, kita akan berkenalanan tentang berbagai jenis lingkungan hidup yang ada di sekitar kita. Selain itu, teman-teman juga akan dijelaskan tentang fungsi dari adanya lingkungan hidup. Tapi sebelumnya mari kenali dulu pengertian dari lingkungan hidup, yuk!",
		photoUrl = "https://res.cloudinary.com/dwptxkhx6/image/upload/v1705320869/pulse/lessons/5f677eecdb5276be25199c8aaf9655a2_gwbcvx.jpg",
		createdAt = "2024-01-13T12:30:49.207Z",
		updatedAt = "2024-01-15T12:14:31.418Z",
		categories = listOf(LocalLessonCategoryDataProvider.category1.name),
	)

	val values = listOf(
		academy1
	)

}