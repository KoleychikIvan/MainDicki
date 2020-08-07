package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.koleychik.maindicki.helpLevel.LevelHelper
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository

class Level1ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)

    private val listEng = mutableListOf<String>()
    private val listRus = mutableListOf<String>()

    val wordRus = MutableLiveData<String>()
    val wordAllWords = MutableLiveData<MutableList<String>>()

    val numberApple = MutableLiveData(0)

    private var haveHelp = false

    private var allHelps = 0

    private lateinit var listModel: MutableList<WordModel>

    private lateinit var levelHelper: LevelHelper

    fun makeItFirst() {
        makeLists()

        allHelps = listEng.size

        levelHelper = LevelHelper(listEng, listRus)
        levelHelper.isLevel1()

        giveNewWords()
    }

    fun makeOnClickForButton(text: String): Int {
        val res = levelHelper.checkToEndLevel1(text)

        if (res != 1)   giveNewWords()
        if (res != -1) {
            numberApple.value = numberApple.value?.plus(1)
        }
        else{
            numberApple.value = numberApple.value?.minus(1)
        }
        return res
    }

    private fun giveNewWords() {
        haveHelp = false
        val values = levelHelper.getNewWordIsLevel1()
        wordRus.value = values[1]
        wordAllWords.value = levelHelper.mixLevel1(mutableListOf(values[0], values[2], values[3]))
    }


    fun getAnswer(): String {
        return levelHelper.answer
    }

    private fun makeLists() {
        listModel = repository.getAllSelect(true) as MutableList

        for (i in listModel) {
            listEng.add(i.english)
            listRus.add(i.russian)
        }
    }

}