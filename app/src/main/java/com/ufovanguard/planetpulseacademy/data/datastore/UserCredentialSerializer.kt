package com.ufovanguard.planetpulseacademy.data.datastore

import androidx.datastore.core.Serializer
import com.ufovanguard.planetpulseacademy.ProtoUserCredential
import java.io.InputStream
import java.io.OutputStream

object UserCredentialSerializer: Serializer<ProtoUserCredential> {

	override val defaultValue: ProtoUserCredential
		get() = ProtoUserCredential()

	override suspend fun readFrom(input: InputStream): ProtoUserCredential {
		return ProtoUserCredential.ADAPTER.decode(input)
	}

	override suspend fun writeTo(t: ProtoUserCredential, output: OutputStream) {
		ProtoUserCredential.ADAPTER.encode(output, t)
	}


}
