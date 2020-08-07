package com.koleychik.maindicki.coroutines

import android.content.SharedPreferences
import android.util.Log
import com.koleychik.maindicki.Singletons.SingletonStyle
import com.koleychik.maindicki.additions.Keys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PutToSPrefStyle(private val sPref : SharedPreferences) {

    private val singleton = SingletonStyle.getInstance()

    fun putToSPref() = CoroutineScope(Dispatchers.Default).launch {

        val editor = sPref.edit()

        editor.putInt(Keys.STYLE_BG, singleton.styleBg)
        editor.putInt(Keys.STYLE_BTN, singleton.styleBtn)
        editor.putInt(Keys.STYLE_TV, singleton.styleTextColor)
        editor.putInt(Keys.STYLE_SHEEP, singleton.styleSheep)
        editor.putStringSet(Keys.STYLE_BG_SET, singleton.setBg.toSet())
        editor.putStringSet(Keys.STYLE_BTN_SET, singleton.setBtn.toSet())
        editor.putStringSet(Keys.STYLE_TV_SET, singleton.setTextColor.toSet())
        editor.putStringSet(Keys.STYLE_SHEEP_SET, singleton.setSheep.toSet())

        editor.apply()
    }

    fun getStyle(){
        singleton.styleBg = sPref.getInt(Keys.STYLE_BG, 0)
        singleton.styleBtn = sPref.getInt(Keys.STYLE_BTN, 0)
        singleton.styleTextColor = sPref.getInt(Keys.STYLE_TV, 2)
        singleton.styleSheep = sPref.getInt(Keys.STYLE_SHEEP, 1)


        singleton.setBg = sPref.getStringSet(Keys.STYLE_BG_SET, HashSet<String>()) as HashSet<String>
        singleton.setBtn = sPref.getStringSet(Keys.STYLE_BTN_SET, HashSet<String>()) as HashSet<String>
        singleton.setTextColor = sPref.getStringSet(Keys.STYLE_TV_SET, HashSet<String>()) as HashSet<String>
        singleton.setSheep = sPref.getStringSet(Keys.STYLE_SHEEP_SET, HashSet<String>()) as HashSet<String>
    }

}