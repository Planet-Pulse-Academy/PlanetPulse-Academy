package com.ufovanguard.planetpulseacademy.foundation.extension

import com.ufovanguard.planetpulseacademy.ProtoUserCredential
import com.ufovanguard.planetpulseacademy.data.model.UserCredential

fun ProtoUserCredential.toUserCredential(): UserCredential = UserCredential(id, name, email, token)
