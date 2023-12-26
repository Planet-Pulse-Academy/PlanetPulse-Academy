package com.ufovanguard.planetpulseacademy.foundation.extension

import com.ufovanguard.planetpulseacademy.ProtoUserPreference
import com.ufovanguard.planetpulseacademy.data.model.UserPreference

fun ProtoUserPreference.toUserPreference(): UserPreference = UserPreference(isFirstInstall)
