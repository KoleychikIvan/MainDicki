package com.koleychik.maindicki.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.koleychik.maindicki.data.dao.WordDao
import com.koleychik.maindicki.data.database.WordDatabase
import com.koleychik.maindicki.models.WordModel
import kotlinx.coroutines.*

class WordRepository(application: Application) {
    private var wordDao : WordDao

    init {
        val database: WordDatabase = WordDatabase.getInstance(
                application.applicationContext
        )!!
        wordDao = database.wordDao()
    }

    fun getAll() : PagingSource<Int, WordModel>{
        return wordDao.getAll()
    }

    suspend fun deleteById(id : Int){
        wordDao.deleteById(id)
    }

    suspend fun updateById(id: Int, eng : String, rus : String){
        wordDao.updateById(id, eng, rus)
    }

    suspend fun selectSameWords(id : Int, bool: Boolean){
        val list = wordDao.selectSameWords(id)
        for (i in list){
            wordDao.selectById(i.id, true)
        }
    }

    suspend fun returnAllTo(bool: Boolean){
        wordDao.returnAllTo(bool)
    }

    suspend fun selectAll(bool: Boolean){
        wordDao.selectAll(bool)
    }

    suspend fun selectById(id : Int, bool: Boolean){
        wordDao.selectById(id, bool)
    }

    suspend fun getAllLiveData(): LiveData<List<WordModel>> = wordDao.getAllLiveData()

    fun getAllList() : List<WordModel> = wordDao.getAllList()

    fun getAllSelect(bool : Boolean): List<WordModel> = wordDao.getAllSelect(bool)


    suspend fun updateCheck(id : Int, bool : Boolean) {
        wordDao.updateCheck(id, bool)
    }

    suspend fun update(wordModel: WordModel) {
        wordDao.update(wordModel)
    }

    suspend fun insert(wordModel: WordModel) {
        wordDao.insert(wordModel)
    }

    suspend fun delete(wordModel: WordModel) {
        wordDao.delete(wordModel)
    }

    fun getSizeDB() = wordDao.getSizeDB()
}
