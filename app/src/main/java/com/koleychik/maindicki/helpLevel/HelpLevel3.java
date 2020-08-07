package com.koleychik.maindicki.helpLevel;

import java.util.ArrayList;
import java.util.Random;

public class HelpLevel3 {

    char[] alfabet_eng = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    Random random = new Random();

    public ArrayList<String> makeMas(String word){
        int resLength = word.length() + 5;
        char[] mas = word.toCharArray();
        char[] res = new char[resLength];
        ArrayList<Character> listLetter = new ArrayList<>();
        ArrayList<Integer> listPlace = new ArrayList<>();

        for (int i = 0; i < word.length(); i++){
            listPlace.add(i);
        }

        for (char i : mas){
            listLetter.add(i);
        }

//       add to list res
        for (int i = 0 ; i < word.length(); i++){
            int indexListLetter = random.nextInt(listLetter.size());
            int indexListPlace = random.nextInt(listPlace.size());
            res[listPlace.get(indexListPlace)] = listLetter.get(indexListLetter);
            listLetter.remove(indexListLetter);
            listPlace.remove(indexListPlace);
        }

        for (int i = 0; i < res.length; i++){
            if (res[i] == 0){
                res[i] = isInMas(mas);
            }
        }

        ArrayList<String> final_res = new ArrayList<>();

        for (char i : res){
            final_res.add(String.valueOf(i));
        }
        return final_res;
    }

    @SuppressWarnings("all")
    private char isInMas(char[] mas){
        while (true){
            char letter = alfabet_eng[random.nextInt(alfabet_eng.length)];
            for (char i : mas){
                if (i == letter) continue;
            }
            return letter;
        }
    }

}
