package com.game.tm.features.auth.presentation.viewmodel

import com.game.tm.features.auth.data.entity.SignInResponse
import com.russhwolf.settings.Settings

class AuthSettings(
    private val settings: Settings
) {
    fun saveToken(token: String) {
        settings.putString("user_token", token)
    }

    fun saveFullName(name: String) {
        settings.putString("fullName", name)
    }

    fun saveEmail(email: String) {
        settings.putString("user_email", email)
    }

    fun savePhone(phone: String) {
        settings.putString("user_phone", phone)
    }

    fun saveClientType(type: String) {
        settings.putString("user_type", type)
    }

    fun saveStreamId(id: String) {
        settings.putString("user_stream_id", id)
    }

    fun saveId(id: Int) {
        settings.putInt("user_id", id)
    }

    fun saveUsername(username: String) {
        settings.putString("username", username)
    }

    fun savePassword(password: String) {
        settings.putString("password", password)
    }

    fun saveCreatedAt(date: String) {
        settings.putString("created_at", date)
    }

    fun saveUser(user: SignInResponse) {
        saveId(user.id)
        saveCreatedAt(user.created_at)
        savePassword(user.password)
        saveUsername(user.username)
        saveEmail(user.email)
        saveFullName(user.fullname)
        savePhone(user.phone)
        saveStreamId(user.stream_id)
        saveToken(user.token)
        saveClientType(user.clientType)
    }

    fun logout() {
        saveId(0)
        saveCreatedAt("")
        savePassword("")
        saveUsername("")
        saveEmail("")
        saveFullName("")
        savePhone("")
        saveStreamId("")
        saveToken("")
        saveClientType("")
    }

    fun getUserInfo(): SignInResponse {
        return SignInResponse(
            email = settings.getString("user_email", ""),
            fullname = settings.getString("fullName", ""),
            password = settings.getString("password", ""),
            phone = settings.getString("user_phone", ""),
            stream_id = settings.getString("user_stream_id", ""),
            username = settings.getString("username", ""),
            id = settings.getInt("user_id", 0),
            deleted = false,
            clientType = settings.getString("user_type", ""),
            created_at = settings.getString("created_at", ""),
            updated_at = settings.getString("created_at", ""),
            token = settings.getString("user_token", ""),
        )
    }

    fun checkAccess(type: String): Boolean {
        return if(getUserInfo().clientType == "BUISNESS") {
            true
        } else if(getUserInfo().clientType == type) {
            true
        } else if(getUserInfo().clientType == "ADVANCED") {
            type == "BASIC" || type == "ADVANCED"
        } else {
            false
        }
    }
}