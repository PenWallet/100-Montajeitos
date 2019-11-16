package com.penwallet.cienmontajeitos.Entities;

import java.util.HashMap;
import java.util.Map;

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

    public double getTotalPriceItems(boolean useEuromaniaPrices)
    {
        double totalPrice = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            totalPrice += (useEuromaniaPrices ? entry.getKey().getPriceEuromania() : entry.getKey().getPrice()) * entry.getValue();
        }
        return totalPrice;
    }
}
