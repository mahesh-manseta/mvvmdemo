package com.maheshmanseta.mvvm.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val NAME = "mahesh_preferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val USER_ID_PREF = Pair("user_id", "1")
    private val USER_TOKEN_PREF = Pair("user_token", "")
    private val USER_NAME_PREF = Pair("user_name", "Mahesh")
    private val USER_DATA_PREF = Pair("user_data", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var firstRun: Boolean
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }

    var userToken: String?
        get() = preferences.getString(USER_TOKEN_PREF.first, USER_TOKEN_PREF.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(USER_TOKEN_PREF.first, value)
        }

    var userId: String?
        get() = preferences.getString(USER_ID_PREF.first, USER_ID_PREF.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(USER_ID_PREF.first, value)
        }

    var userName: String?
        get() = preferences.getString(USER_NAME_PREF.first, USER_NAME_PREF.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(USER_NAME_PREF.first, value)
        }

    var userData: String?
        get() = preferences.getString(USER_DATA_PREF.first, USER_DATA_PREF.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(USER_DATA_PREF.first, value)
        }

}