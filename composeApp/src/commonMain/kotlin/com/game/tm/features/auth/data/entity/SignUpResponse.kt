package com.game.tm.features.auth.data.entity

import kotlinx.serialization.Serializable

/*
{
	"email": "multiplatform00@gmail.com",
	"fullname": "Shageldi Alyyev",
	"password": "QwertyWeb123",
	"phone": "+99361298783",
	"stream_id": "12345678",
	"username": "shageldi123",
	"id": 2,
	"deleted": false,
	"clientType": "NONE",
	"created_at": "2024-07-02T17:29:17.681Z",
	"updated_at": "2024-07-02T17:29:17.681Z",
	"redirect": "sign-in"
}

 */
@Serializable
data class SignUpResponse(
    val email: String,
    val fullname: String,
    val password: String,
    val phone: String,
    val stream_id: String,
    val username: String,
    val id: Int,
    val deleted: Boolean,
    val clientType: String,
    val created_at: String,
    val updated_at: String,
    val redirect: String,
)
