package com.koleychik.maindicki.additions

class CheckWordToFailedSymbols {

    private val masSymbols = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '!', '@', '$', '%', '*', '&', '^')

    public fun checkFailedSymbols(word : String): Boolean{

        if (word == ""){
            return true
        }

        for (i in word){
            for (symbols in masSymbols){
                if (i == symbols){
                    return true
                }
            }
        }
        return false
    }
}