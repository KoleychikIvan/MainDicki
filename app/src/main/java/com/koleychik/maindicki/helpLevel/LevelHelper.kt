package com.koleychik.maindicki.helpLevel

import java.lang.Exception
import java.util.*
import kotlin.collections.HashSet


class LevelHelper(private val listEng: MutableList<String>, private val listRus: MutableList<String>) {

//  for level1
    private lateinit var secondListEnd: MutableList<String>
    private lateinit var secondListRus: MutableList<String>
    private var isLevel1 = false

//  for level3
    private var setLevel3 = HashSet<Char>()
    private var count = 0

    private val random = Random()
    private var rand_num1 = 0
//    private var rand_num2 = 0
    lateinit var answer : String

    fun giveFirstLetter() : String{
        return answer[0].toString()
    }

    fun check(value : String): Int{
        deleteWord(listEng, listRus)
        if (listEng.size == 0) return 1
        if (value == answer){
            return 0
        }
        return -1
    }

    fun checkToEndLevel1(value: String) : Int{
        deleteWord(listEng, listRus)
        if (listEng.size == 0){
            return 1
        }
        if (value == answer){
            return 0
        }
        return -1
    }

    fun checkLevel3(letter : Char): Int{
        count++
        if (setLevel3.contains(letter)){
            if (answer.length == count){
                return 1
            }
            return 0
        }
        return -1
    }

    fun getNewWordLevel3(): MutableList<String>{
        count = 0
        rand_num1 = random.nextInt(listEng.size)
        setLevel3.addAll(listEng[rand_num1].toMutableList())
        answer = listEng[rand_num1]
        return mutableListOf(listEng[rand_num1], listRus[rand_num1])
    }

    fun isLevel1(){
//        isLevel1 = true
        secondListEnd = mutableListOf()
        secondListRus = mutableListOf()

        secondListEnd.addAll(listEng)
        secondListRus.addAll(listRus)
    }

    fun getNewWordIsLevel1() : MutableList<String>{
//        Log.d("level_Help", "start")
        rand_num1 = random.nextInt(listEng.size)
        answer = listEng[rand_num1]
        val list = mutableListOf<String>()
        list.add(listEng[rand_num1])
        list.add(listRus[rand_num1])
        list.addAll(get2Word(rand_num1))
        return list
    }

    private fun get2Word(num0 : Int) : List<String> {
//        Log.d("level_Help", "get2Word start")
        while (true){
            val num1 = random.nextInt(secondListEnd.size)
//            Log.d("level_Help", "${listEng.size}")
//            Log.d("level_Help", "${secondListEnd[num1]}  ${listEng[num0]}")
            if (secondListEnd[num1] != listEng[num0]){
                while (true){
                    val num2 = random.nextInt(secondListEnd.size)
//                    Log.d("level_Help", "${secondListEnd.size}")
                    if (num1 != num2 && secondListEnd[num2] != listEng[num0]) {
                        return listOf(secondListEnd[num1], secondListEnd[num2])
                    }
                }
            }
        }
    }

    fun mixLevel1(list : MutableList<String>): MutableList<String>{
        val num1 = random.nextInt(3)
        while (true) {
            val num2 = random.nextInt(3)
            if (num2 == num1) continue
            while (true){
                val num3 = random.nextInt(3)
                if (num3 != num2 && num3 != num1){
                    return mutableListOf(list[num1], list[num2], list[num3])
                }
            }
        }
    }

    private fun deleteWord(listEng: MutableList<String>, listRus: MutableList<String>){
        try {
            listEng.removeAt(rand_num1)
            listRus.removeAt(rand_num1)
        }
        catch (e : Exception){}
    }

    fun getNewWord(): MutableList<String>{
        rand_num1 = random.nextInt(listEng.size)
        answer = listEng[rand_num1]
        return mutableListOf(answer, listRus[rand_num1])
    }
}