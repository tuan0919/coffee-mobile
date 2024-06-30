package com.nlu.packages.inventory;

import java.util.ArrayList;

public class FakeDetailData {
    ArrayList<String[]> list = new ArrayList<>();

    public FakeDetailData(){
        String[] milkToppingList = {"Whole Milk", "Small Milk"};
        String[] sweetList = {"Sugar", "Salt"};
        String[] decafList = {"Regular", "No"};
        list.add(milkToppingList);
        list.add(sweetList);
        list.add(decafList);
    }

    public ArrayList<String[]> getList() {
        return list;
    }

    public void setList(ArrayList<String[]> list) {
        this.list = list;
    }
}
