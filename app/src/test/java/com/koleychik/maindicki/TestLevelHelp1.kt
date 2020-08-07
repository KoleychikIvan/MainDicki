package com.koleychik.maindicki

import com.koleychik.maindicki.helpLevel.LevelHelper
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class TestLevelHelp1 {

    private lateinit var levelHelp : LevelHelper

    @Before
    fun setUp(){
        val listEng = listOf("hello", "new", "main") as MutableList
        val listRus = listOf("привет", "новый", "главный") as MutableList
        levelHelp = LevelHelper(listEng = listEng, listRus = listRus)
    }

    @Test
    fun testGetNewWordIsLevel1(){
        levelHelp.isLevel1()
        val value = levelHelp.getNewWordIsLevel1()
        assertThat(value)
                .isNotNull
        assertThat(value.size).isEqualTo(4)
    }

    @Test
    fun testGetNewWord(){
        val value = levelHelp.getNewWord()
        assertThat(value)
                .isNotNull
        assertThat(value.size).isEqualTo(2)
    }

    //  check if answer is true
    @Test
    fun testCheckTrue(){
        levelHelp.getNewWord()
        assertThat(levelHelp.check(levelHelp.answer)).isEqualTo(0)
        levelHelp.isLevel1()
        assertThat(levelHelp.checkToEndLevel1(levelHelp.answer)).isEqualTo(0)
    }

//  check if answer is false
    @Test
    fun testCheckFalse(){
        levelHelp.getNewWord()
        assertThat(levelHelp.check("another Word")).isEqualTo(-1)
        assertThat(levelHelp.checkToEndLevel1("another Word")).isEqualTo(-1)
    }

    //  check if it was las word
    @Test
    fun testCheckFinish(){
        val newLevelHelper = LevelHelper(listOf("now") as MutableList, listOf("сейчас") as MutableList)

        newLevelHelper.getNewWord()

        assertThat(newLevelHelper.check("now")).isEqualTo(1)
    }

    @Test
    fun testMixLevel1(){
        levelHelp.isLevel1()
        val list = levelHelp.mixLevel1(levelHelp.getNewWordIsLevel1())

        val set = HashSet<String>()
        list.forEach {
            assertThat(set.contains(it)).isFalse()
            set.add(it)
        }
    }


}