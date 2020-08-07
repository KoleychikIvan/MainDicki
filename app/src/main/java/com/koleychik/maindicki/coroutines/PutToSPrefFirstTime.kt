package com.koleychik.maindicki.coroutines

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PutToSPrefFirstTime(private val sPref : SharedPreferences) {
    fun putBooleanToSPref(sPrefName : String, bool : Boolean){
        CoroutineScope(Dispatchers.Main).launch{
            val editor: Editor = sPref.edit()
            editor.putBoolean(sPrefName, bool)
            editor.apply()
        }
    }
}