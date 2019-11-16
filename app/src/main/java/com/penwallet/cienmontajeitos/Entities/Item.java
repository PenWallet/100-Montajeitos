package com.penwallet.cienmontajeitos.Entities;

import com.penwallet.cienmontajeitos.MenuItemType;

public class Item {
    private int menuId;
    private String name;
    private double price;
    private double priceEuromania;
    private MenuItemType menuItemType;

    public Item(int menuId, String name, double price, double priceEuromania, MenuItemType menuItemType) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.priceEuromania = priceEuromania;
        this.menuItemType = menuItemType;
    }

    public Item() {
        this.menuId = 0;
        this.name = "";
        this.price = 0;
        this.priceEuromania = 0;
        this.menuItemType = MenuItemType.MONTADITO;
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

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(MenuItemType menuItemType) {
        this.menuItemType = menuItemType;
    }
}
