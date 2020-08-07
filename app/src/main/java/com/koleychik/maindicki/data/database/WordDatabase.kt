package com.koleychik.maindicki.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.koleychik.maindicki.data.dao.WordDao
import com.koleychik.maindicki.models.WordModel

@Database(entities = [WordModel::class], version = 1)
abstract class WordDatabase : RoomDatabase(){

    abstract fun wordDao() : WordDao

    companion object{
        private var instance: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase? {
            if (instance == null) {
                synchronized(WordDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                                context.applicationContext,
                                WordDatabase::class.java,
                                "WordDatabase_DB"
                        ).allowMainThreadQueries().build()
                    }
                    return instance
                }
            }
            return instance
        }
    }
}