package com.game.tm.features.auth.data.entity

import kotlinx.serialization.Serializable
import java.util.UUID

/*
{
	"username":"shageldi123",
	"password":"QwertyWeb123",
	"fullname":"Shageldi Alyyev",
	"phone":"+99361298783",
	"email":"multiplatform00@gmail.com",
	"stream_id":"12345678"
}
 */
@Serializable
data class SignUpRequest(
    val username: String = "",
    val password: String = "",
    val fullname: String = "",
    val phone: String = "",
    val email: String = "",
    val stream_id: String = UUID.randomUUID().toString(),
)
