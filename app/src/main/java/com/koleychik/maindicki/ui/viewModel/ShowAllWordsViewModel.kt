package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShowAllWordsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)

    lateinit var listLiveData: MutableLiveData<List<WordModel>>

//    private lateinit var list: List<WordModel>
//    val listWords = mutableListOf<MutableList<String>>()

    val allModels = Pager(PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200)){
        repository.getAll()
    }.flow

//    fun makeItFirst(){
//        getList()
//    }

//    fun makeClearAll() = CoroutineScope(Dispatchers.Default).launch {
//        for ( i in list){
//            repository.delete(i)
//        }
//    }

//    private fun getList() {
//        list = repository.getAllList()
//        var lastDate = ""
//        var count = 0
//        for (i in list) {
//            count++
//            var newList = mutableListOf<String>()
//            if (lastDate != i.time) {
//                lastDate = i.time
//                newList.add(lastDate)
//                listWords.add(newList)
//                newList = mutableListOf()
//            }
//            newList.add(i.id.toString())
//            newList.add(i.english)
//            newList.add(i.russian)
//            newList.add(count.toString())
//
//            Log.d("list", "$count ${i.english} ${i.russian}")
//
//            listWords.add(newList)
//        }
//    }

    fun delete(id : Int) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteById(id)
    }

    fun updateById(id : Int, english : String, russian : String) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateById(id = id, eng = english, rus = russian)
    }

}