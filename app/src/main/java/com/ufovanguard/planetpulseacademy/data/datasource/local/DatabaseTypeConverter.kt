package com.ufovanguard.planetpulseacademy.data.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ufovanguard.planetpulseacademy.data.model.Category
import com.ufovanguard.planetpulseacademy.data.model.Stage

object DatabaseTypeConverter {

	@TypeConverter
	fun stringListToJSON(list: List<String>): String = Gson().toJson(list)

	@TypeConverter
	fun stringListFromJSON(json: String): List<String> = Gson().fromJson(json, Array<String>::class.java).toList()

	@TypeConverter
	fun categoryListToJSON(list: List<Category>): String = Gson().toJson(list)

	@TypeConverter
	fun categoryListFromJSON(json: String): List<Category> = Gson().fromJson(json, Array<Category>::class.java).toList()

	@TypeConverter
	fun stageListToJSON(list: List<Stage>): String = Gson().toJson(list)

	@TypeConverter
	fun stageListFromJSON(json: String): List<Stage> = Gson().fromJson(json, Array<Stage>::class.java).toList()

}