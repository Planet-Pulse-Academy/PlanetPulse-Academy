package com.ufovanguard.planetpulseacademy.data.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson

object DatabaseTypeConverter {

	@TypeConverter
	fun stringListToJSON(list: List<String>): String = Gson().toJson(list)

	@TypeConverter
	fun stringListFromJSON(json: String): List<String> = Gson().fromJson(json, Array<String>::class.java).toList()

}