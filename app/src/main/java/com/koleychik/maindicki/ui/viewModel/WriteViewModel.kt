package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.koleychik.maindicki.additions.CheckWordToFailedSymbols
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WriteViewModel(application: Application): AndroidViewModel(application) {
    private val context : Context = application.applicationContext
    private val checkWordToFailedSymbols = CheckWordToFailedSymbols()

    val liveDataListSize = MutableLiveData<Int>(0)

    lateinit var list: List<WordModel>

    private val repository = WordRepository(application)

    fun getAllList() = viewModelScope.launch {
        list = repository.getAllList()
    }

    fun getSizeDb() = repository.getSizeDB()

    fun insert(wordModel: WordModel) = CoroutineScope(Dispatchers.IO).launch {
        repository.insert(wordModel)
    }

    fun makeCheckWordToFailed(text : String): Boolean{
        return checkWordToFailedSymbols.checkFailedSymbols(text)
    }



}