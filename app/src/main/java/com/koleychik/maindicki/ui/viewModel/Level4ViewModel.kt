package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.koleychik.maindicki.helpLevel.LevelHelper
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository

class Level4ViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val repository = WordRepository(application)

    private lateinit var listModel : MutableList<WordModel>

    private var listEng = mutableListOf<String>()
    private var listRus = mutableListOf<String>()

    val numberApple = MutableLiveData(0)

    val wordRus = MutableLiveData<String>()
    val wordEng = MutableLiveData<String>()

    private lateinit var levelHelper : LevelHelper

    fun makeItFirst() {
        makeLists()

        Log.d("level4Test", listEng.size.toString())


        levelHelper = LevelHelper(listEng, listRus)
    }

    fun makeCheck(word : String): Int {
        return levelHelper.check(word)
    }

    fun getNewWord(){

        val values = levelHelper.getNewWord()

        wordEng.value = values[0]
        wordRus.value = values[1]

        Log.d("level4Test", wordEng.value!!)
        Log.d("level4Test", wordRus.value!!)

    }

    fun getAnswer() = levelHelper.answer

    private fun makeLists(){
        listModel = repository.getAllSelect(true) as MutableList

        Log.d("level4Test", listModel.size.toString())

        for (i in listModel) {
            listEng.add(i.english)
            listRus.add(i.russian)
        }
    }

    fun makeSkip() : Int {
        if (levelHelper.check("") == 1) return 1
        getNewWord()
        return 0
    }

}