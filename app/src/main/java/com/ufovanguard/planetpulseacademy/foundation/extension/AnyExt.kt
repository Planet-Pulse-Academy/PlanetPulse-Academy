package com.ufovanguard.planetpulseacademy.foundation.extension

import com.google.gson.Gson

fun Any.toJson(): String = Gson().toJson(this)
