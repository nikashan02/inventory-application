//Nikashan Thavaruban
//Nov. 5, 2019
//Product.java
//Product class with the properties of a product

import java.io.Serializable;

public class Product implements Serializable { //Serializable allows objects initialized to the class to be saved into .tmp files
    private String code;
    private String description;
    private int quantity;
    private int sales;

    //PRODUCT CONSTRUCTOR
    public Product(String code, String description, int quantity, int sales) {
        this. code = code;
        this.description = description;
        this.quantity = quantity;
        this.sales = sales;
    }

    //GETTERS
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public int getSales() { return sales; }

    //SETTERS
    public void setCode(String value) { code = value; }
    public void setDescription(String value) { description = value; }
    public void setQuantity(int value) { quantity = value; }
    public void setSales(int value) { sales = value; }
}