package com.penwallet.cienmontajeitos.Entities;

import java.util.HashMap;

public class Person {
    private HashMap<Item, Integer> items;
    private String name;

    public Person(String name, HashMap<Item, Integer> items) {
        this.items = items;
        this.name = name;
    }

    public Person() {
        this.items = new HashMap<>();
        this.name = "";
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Item, Integer> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
