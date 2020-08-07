package com.koleychik.maindicki.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WordModel(val russian : String,
                val english : String,
                var check: Boolean,
                val time : String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}