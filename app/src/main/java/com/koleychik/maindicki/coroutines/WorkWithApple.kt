package com.koleychik.maindicki.coroutines

import android.content.SharedPreferences
import com.koleychik.maindicki.additions.Keys
import kotlinx.coroutines.*

class WorkWithApple(private val sPref: SharedPreferences) {
    fun getApple() = sPref.getString(Keys.GET_APPLE, "0")

    fun saveApple(apple: String) = CoroutineScope(Dispatchers.Main).launch {
        val editor = sPref.edit()
        editor.putString(Keys.GET_APPLE, apple)
        editor.apply()
    }
}