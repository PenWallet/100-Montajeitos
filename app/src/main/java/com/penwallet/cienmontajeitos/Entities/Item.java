package com.penwallet.cienmontajeitos.Entities;

public class Item {
    private int menuId;
    private String name;
    private double price;
    private double priceEuromania;

    public Item(int menuId, String name, double price, double priceEuromania) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.priceEuromania = priceEuromania;
    }

    public Item() {
        this.menuId = 0;
        this.name = "";
        this.price = 0;
        this.priceEuromania = 0;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceEuromania() {
        return priceEuromania;
    }

    public void setPriceEuromania(double priceEuromania) {
        this.priceEuromania = priceEuromania;
    }
}
