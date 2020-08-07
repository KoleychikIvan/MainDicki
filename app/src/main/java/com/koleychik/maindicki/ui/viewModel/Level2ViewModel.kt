package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.koleychik.maindicki.R
import com.koleychik.maindicki.helpLevel.HelpLevel2
import com.koleychik.maindicki.helpLevel.LevelHelper
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Level2ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)

    private val listEng = mutableListOf<String>()
    private val context = application.applicationContext

    private val listRus = mutableListOf<String>()

    val word = MutableLiveData<String>()

    val wordRus = MutableLiveData<String>()

    val numberApple = MutableLiveData(0)
    private val valueHelps = MutableLiveData<Int>()

    private var haveHelp = false

    private var allHelps = 0

    private lateinit var listModel: MutableList<WordModel>

    private lateinit var levelHelper: LevelHelper

    fun makeCheck(): Int {
        val res = levelHelper.check(word.value!!)
        if (res != -1) {
            numberApple.value = numberApple.value?.plus(3)
        }
        else{
            numberApple.value = numberApple.value?.minus(3)
        }
        return res
    }

    fun makeItFirst() {
        makeLists()

        allHelps = listEng.size

        levelHelper = LevelHelper(listEng, listRus)

    }

    fun giveHelp(listBtn: MutableList<Button>) {
        if (checkHelp()) {
            haveHelp = true

            val letter = levelHelper.giveFirstLetter()
        }
    }

    fun checkHelp(): Boolean {
        valueHelps.value = valueHelps.value?.plus(1)
        return valueHelps.value!! <= listRus.size || haveHelp
    }

    fun makeOnClickForButton(text: String) {
        word.value = word.value + text
    }

    fun giveNewWords() : CharArray{
        word.value = ""
        haveHelp = false
        val values = levelHelper.getNewWord()
        val word: String = values[1]
        wordRus.value = word

//        levelHelper.getMasOfWords(word)
        return HelpLevel2(values[0]).keyBoard
    }

    fun getAnswer(): String {
        return levelHelper.answer
    }

    private fun makeLists() = CoroutineScope(Dispatchers.Main).launch {
        listModel = repository.getAllSelect(true) as MutableList

        for (i in listModel) {
            listEng.add(i.english)
            listRus.add(i.russian)
        }
    }

}