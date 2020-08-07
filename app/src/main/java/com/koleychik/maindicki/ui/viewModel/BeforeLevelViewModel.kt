package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeforeLevelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)

    lateinit var list: List<WordModel>
    val listWords = mutableListOf<MutableList<String>>()

    val allModels = Pager(PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200)){
        repository.getAll()
    }.flow

    fun makeItFirst(){
        getList()
        makeReturnAllTo()
    }

    private fun makeReturnAllTo() = CoroutineScope(Dispatchers.IO).launch{
        repository.returnAllTo(false)
    }

    fun selectAll(bool: Boolean) = CoroutineScope(Dispatchers.IO).launch {
        repository.selectAll(bool)
    }

    fun selectSameWords(number : Int) = CoroutineScope(Dispatchers.IO).launch {
        repository.selectSameWords(number, true)
    }

    fun setCheck(id : Int, bool : Boolean) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateCheck(id, bool)
    }

    fun getCount() = repository.getSizeDB()

    private fun getList() {
        list = repository.getAllList()
        var lastDate = ""
        var count = 0
        for (i in list) {
            count++
            var newList = mutableListOf<String>()
            if (lastDate != i.time) {
                lastDate = i.time
                newList.add(lastDate)
                listWords.add(newList)
                newList = mutableListOf()
            }
            newList.add(count.toString())
            newList.add(i.english)
            newList.add(i.russian)
            newList.add("0")
            newList.add(i.id.toString())

            Log.d("list", "$count ${i.english} ${i.russian}")

            listWords.add(newList)
        }
    }
}