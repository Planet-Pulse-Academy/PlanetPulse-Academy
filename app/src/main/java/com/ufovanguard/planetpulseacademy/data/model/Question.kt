package com.ufovanguard.planetpulseacademy.data.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class Question(
	@SerializedName("_id") val id: String,
	@SerializedName("text") val text: String,
	@SerializedName("options") val options: List<String>,
	@SerializedName("correctOptionIndex") val correctOptionIndex: Int,
): Parcelable
