package com.ufovanguard.planetpulseacademy.data.model


import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// TODO: Create category table, and use database relation
@Parcelize
@Immutable
data class Category(
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
): Parcelable