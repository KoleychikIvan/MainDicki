package com.koleychik.maindicki.helpLevel;

import java.util.ArrayList;
import java.util.Random;

public class HelpLevel2{

//    char[] alfabet_rus = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    char[] alfabet_eng = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private String word;

    public HelpLevel2(String word) {
        this.word = word;
    }

    private Random random = new Random();

    public char[] getKeyBoard() {
        if(word.length() < 4){
            char[] mas_res_layout_1 = {'1', '1', '1', '1', '1'};
            return MakeButton(mas_res_layout_1);
        }
        else if (word.length() < 9){
            char[] mas_res_layout_2 = {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1'};
            return MakeButton(mas_res_layout_2);
        }
        else{
            char[] mas_res_layout_3 = {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'};
            return MakeButton(mas_res_layout_3);
        }
    }

    private char[] MakeButton(char[] mas) {
        int size_mas_btn_text = mas.length;
        int rand_2;
        char[] word_array = word.toCharArray();
        ArrayList<Integer> Int_list = new ArrayList<>();

        for (int i = 0; i < size_mas_btn_text; i++) {
            Int_list.add(i);
        }

        for (char i : word_array) {
            rand_2 = random.nextInt(Int_list.size());
            mas[Int_list.get(rand_2)] = i;
            Int_list.remove(rand_2);
        }

        for (int i = 0; i < size_mas_btn_text; i++) {
            if (mas[i] == '1') {
                rand_2 = random.nextInt(alfabet_eng.length);
                mas[i] = alfabet_eng[rand_2];
            }
        }
        return mas;
    }
}
