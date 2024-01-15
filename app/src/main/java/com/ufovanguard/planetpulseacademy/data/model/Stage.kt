package com.ufovanguard.planetpulseacademy.data.model


import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class Stage(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("difficulty")
    val difficulty: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_lesson")
    val idLesson: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
): Parcelable