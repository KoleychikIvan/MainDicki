package com.koleychik.maindicki.Singletons;

import java.util.HashSet;

public class SingletonStyle {

    private static SingletonStyle instance = null;

    //    check if class was changing
    private boolean wasChanged = false;

    private int styleBg = 0, styleBtn = 0, styleTextColor = 1, styleSheep = 0;

    private int priceBg = 0;

    public int getPriceBg() {
        return priceBg;
    }

    public int getPriceBtn() {
        return priceBtn;
    }

    public int getPriceTextView() {
        return priceTextView;
    }

    public int getPriceSheep() {
        return priceSheep;
    }

    private int priceBtn = 0;
    private int priceTextView = 0;
    private int priceSheep = 0;

    public HashSet<String> setBg = new HashSet<>(styleBg), setBtn = new HashSet<>(styleBtn),
            setTextColor = new HashSet<>(styleTextColor), setSheep = new HashSet<>(styleSheep);

    public boolean isInSetBg(int number) {
        return setBg.contains(String.valueOf(number));
    }

    public boolean isInSetBtn(int number) {
        return setBtn.contains(String.valueOf(number));
    }

    public boolean isInSetTextColor(int number) {
        return setTextColor.contains(String.valueOf(number));
    }

    public boolean isInSetSheep(int number) {
        return setSheep.contains(String.valueOf(number));
    }

    public void buyBg(int number) {
        setBg.add(String.valueOf(number));
    }

    public void buyBtn(int number) {
        setBtn.add(String.valueOf(number));
    }

    public void buyTextColor(int number) {
        setTextColor.add(String.valueOf(number));
    }

    public void buySheep(int number) {
        setSheep.add(String.valueOf(number));
    }

    public boolean isWasChanged() {
        return wasChanged;
    }

    public void setWasChanged(boolean wasChanged) {
        this.wasChanged = wasChanged;
    }

    public int getStyleBg() {
        return styleBg;
    }

    public void setStyleBg(int styleBg) {
        this.styleBg = styleBg;
    }

    public int getStyleBtn() {
        return styleBtn;
    }

    public void setStyleBtn(int styleBtn) {
        this.styleBtn = styleBtn;
    }

    public int getStyleTextColor() {
        return styleTextColor;
    }

    public void setStyleTextColor(int styleTextColor) {
        this.styleTextColor = styleTextColor;
    }

    public int getStyleSheep() {
        return styleSheep;
    }

    public void setStyleSheep(int styleSheep) {
        this.styleSheep = styleSheep;
    }

    private SingletonStyle() {
    }

    public static SingletonStyle getInstance() {
        if (instance == null) {
            instance = new SingletonStyle();
        }
        return instance;
    }


}
