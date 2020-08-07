package com.koleychik.maindicki.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.koleychik.maindicki.models.WordModel

@Dao
interface WordDao {
    @Query("SELECT * FROM WordModel ORDER BY id DESC")
    fun getAll() : PagingSource<Int, WordModel>

    @Query("SELECT * FROM WordModel WHERE `check` = :bool")
    fun getAllSelect(bool: Boolean): List<WordModel>

    @Query("SELECT * FROM WordModel ORDER BY id DESC")
    fun getAllLiveData(): LiveData<List<WordModel>>

    @Query("SELECT * FROM WordModel ORDER BY id DESC")
    fun getAllList(): List<WordModel>

//    @Query("SELECT * FROM WordModel WHERE `check` = :bool")
//    fun getAllSelect(bool: Boolean): List<WordModel>

    @Query("UPDATE WordModel SET `check` = :bool WHERE id = :id")
    fun updateCheck(id: Int, bool: Boolean)

    @Query("UPDATE WordModel SET english = :eng, russian = :rus WHERE id = :id")
    fun updateById(id: Int, eng : String, rus : String)

    @Query("UPDATE WordModel SET `check` = :bool WHERE id = :id")
    fun selectById(id: Int, bool: Boolean)

    @Query("SELECT * FROM WordModel ORDER BY id DESC LIMIT :numberLim ")
    fun selectSameWords(numberLim: Int) : List<WordModel>

    @Query("UPDATE WordModel SET `check` = :bool")
    fun returnAllTo(bool: Boolean)

    @Query("UPDATE WordModel SET `check` = :bool")
    fun selectAll(bool: Boolean)

    @Query("DELETE FROM WordModel WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM WordModel ORDER BY id DESC LIMIT :startID,:lastId")
    fun get20Elements(startID: Int, lastId: Int): List<WordModel>

    @Query("SELECT COUNT(*) FROM WordModel")
    fun getSizeDB(): Int

    @Update
    fun update(wordModel: WordModel)

    @Insert
    fun insert(wordModel: WordModel)

    @Delete
    fun delete(wordModel: WordModel)
}