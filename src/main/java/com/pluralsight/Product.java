package com.pluralsight;

public class Product {
    private String sku;
    private String name;
    private double price;
    private String department;

    public Product(String sku, String name, double price, String department){
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.department = department;
    }

    public String getSku(){
        return sku;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public String getDepartment(){
        return department;
    }

    public void setSku(String sku){
        this.sku = sku;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSku(double price){
        this.price = price;
    }

    public void setDepartment(String department){
        this.department = department;
    }


}
