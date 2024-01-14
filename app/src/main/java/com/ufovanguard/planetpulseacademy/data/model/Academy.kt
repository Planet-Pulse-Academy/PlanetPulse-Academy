package com.ufovanguard.planetpulseacademy.data.model


import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
@Entity(tableName = "academy")
data class Academy(
    @SerializedName("categories")
	@ColumnInfo(name = "categories")
    val categories: List<String>,

    @SerializedName("createdAt")
	@ColumnInfo(name = "createdAt")
    val createdAt: String,

    @SerializedName("description")
	@ColumnInfo(name = "description")
    val description: String,

    @SerializedName("_id")
	@ColumnInfo(name = "_id")
	@PrimaryKey
    val id: String,

    @SerializedName("id_lesson")
	@ColumnInfo(name = "id_lesson")
    val idLesson: String,

    @SerializedName("photo_url")
	@ColumnInfo(name = "photo_url")
    val photoUrl: String,

    @SerializedName("public_id")
	@ColumnInfo(name = "public_id")
    val publicId: String,

    @SerializedName("title")
	@ColumnInfo(name = "title")
    val title: String,

    @SerializedName("updatedAt")
	@ColumnInfo(name = "updatedAt")
    val updatedAt: String
): Parcelable