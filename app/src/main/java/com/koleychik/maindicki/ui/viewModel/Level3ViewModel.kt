package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.koleychik.maindicki.helpLevel.HelpLevel3
import com.koleychik.maindicki.helpLevel.LevelHelper
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository

class Level3ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)

    private val context = application.applicationContext

    private val listEng = mutableListOf<String>()
    private val listRus = mutableListOf<String>()

    val word = MutableLiveData<String>()

//    val wordEng = MutableLiveData<String>()
    val wordRus = MutableLiveData<String>()
    var answerEng = ""

    val numberApple = MutableLiveData(0)

    val isFinish = MutableLiveData<Int>()

    //  if word1 == english
//    val isEnglish = MutableLiveData(false)

    private var haveHelp = false

    private var allHelps = 0

    private lateinit var listModel: MutableList<WordModel>

    private lateinit var levelHelper: LevelHelper

    private val helpLevel3 = HelpLevel3()

    fun makeItFirst() {
        makeLists()

        allHelps = listEng.size

        levelHelper = LevelHelper(listEng, listRus)
    }

    private fun makeCheck(){
        val res = levelHelper.checkLevel3(word.value!!.single())
        if (res != -1) {
            numberApple.value = numberApple.value?.plus(5)
        }
        else{
            numberApple.value = numberApple.value?.minus(5)
        }
        isFinish.value = res
    }

    fun makeOnClickForButton(text: String) {
        word.value = word.value + text
        if (word.value!!.length >= wordRus.value!!.length){
            makeCheck()
        }
    }

    fun giveNewWords() : MutableList<String> {
        word.value = ""
        haveHelp = false
        val values = levelHelper.getNewWordLevel3()
        val word: String = values[1]
        wordRus.value = word

        answerEng = values[0]
        return helpLevel3.makeMas(answerEng)
//        levelHelper.getMasOfWords(word)
//        HelpLevel2(word).keyBoard
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