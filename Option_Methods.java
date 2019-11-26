//Nikashan Thavaruban
//Nov. 5, 2019
//Option_Methods.java
//Stores methods used in main inventory program

import java.util.*;
import java.security.*;

public class Option_Methods {
    static Scanner sc = new Scanner(System.in);

    public static String[] createUser(){ //Gets user input for username and password for new user
        System.out.print("Please enter a username: ");
        String enteredUser = sc.next();
        System.out.println("");
        System.out.print("Please enter a password: ");
        String enteredPass = sc.next();
        System.out.print("");
        System.out.println("New user created");
        System.out.println("");
        return new String[] {enteredUser, enteredPass}; //Returns username and password as an array
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException{ //Password hasher
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(password.getBytes());
        byte[] b = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b1 : b) {
            sb.append(Integer.toHexString(b1 & 0xff).toString());
        }
        return sb.toString(); //Returns hashed password
    }

    public static void invalidEntry() {
        System.out.println("INVALID ENTRY");
    }

    public static void printOptions() {
        System.out.println("");
        System.out.println("Please choose from one of the following options:");
        System.out.println("1.) List current inventory");
        System.out.println("2.) List product detail");
        System.out.println("3.) Add new product");
        System.out.println("4.) Remove product from inventory");
        System.out.println("5.) Edit product");
        System.out.println("6.) List products of quantity 10 or less");
        System.out.println("7.) Search");
        System.out.println("8.) Save changes");
        System.out.println("9.) Quit");
        System.out.println("");
    }

    public static int chooseOption() { //Gets user input for what they want to do with their inventory
        boolean proceed = false;
        int option = 0;

        while (proceed == false) {
            try {
                System.out.print("Choose an option: ");
                option = sc.nextInt();
                if (option>0 && option<10) {
                    System.out.println("");
                    proceed = true;
                }
                else {
                    invalidEntry();
                }
            }
            catch(Exception e) {
                invalidEntry();
                sc.nextLine();
            }
        }
        return option; //Returns option as an integer
    }

    public static void displayInventory(ArrayList<Product> inventory) { //Prints formatted description and quantity of products in inventory
        System.out.format("%15s%11s", "Description", "Quantity");
        System.out.println("");
        if (inventory.size() != 0) {
            for (Product product : inventory) {
                System.out.format("%15s%11d", product.getDescription(), product.getQuantity());
                System.out.println("");
            }
        }
    }

    public static void displayDetails(ArrayList<Product> inventory) { //Prints formatted code, description, quantity and sales of products
        System.out.format("%10s%22s%14s%10s", "Code", "Description", "Quantity", "Sales");
        System.out.println("");
        if (inventory.size() != 0) {
            for (Product product : inventory) {
                System.out.format("%10s%22s%14d%10d", product.getCode(), product.getDescription(), product.getQuantity(), product.getSales());
                System.out.println("");
            }
        }
    }

    public static ArrayList<Product> addProduct(ArrayList<Product> inventory) { //Adds new product to inventory
        boolean proceed = false;
        boolean goodCode = true;
        String cod = null;
        String desc = null;
        int quant = 0;
        int sal = 0;
        System.out.println("");
        while (proceed == false) {
            try {
                System.out.print("Enter the product code: ");
                cod = sc.next();  //Gets product code of new product user wants to add
                goodCode = true;
                for (Product product : inventory) { //Checks if product code already exists in inventory
                    if (product.getCode().equals(cod)) {
                        goodCode = false;
                        break;
                    }
                }
                if (goodCode == false) {
                    invalidEntry();
                }
                else {
                    System.out.println("Enter the name of the product: ");
                    sc.nextLine();
                    desc = sc.nextLine();  //Gets name of new product
                    System.out.print("Enter the quantity: ");
                    quant = sc.nextInt(); //Gets quantity of new product
                    System.out.print("Enter the sales: ");
                    sal = sc.nextInt(); //Gets sales of new product
                    System.out.println("");
                    proceed = true;
                }
            }
            catch (Exception e) {
                invalidEntry();
                sc.nextLine();
            }
        }

        inventory.add(new Product(cod, desc, quant, sal)); //Adds new product to inventory
        System.out.println("Product successfully added to inventory");
        System.out.println("");
        return inventory; //Returns updated product inventory
    }

    public static ArrayList<Product> removeProduct(ArrayList<Product> inventory) { //Removes product from inventory
        boolean proceed = false;
        String cod = null;
        boolean inside = false;
        System.out.println("");
        if (inventory.size() != 0) { //Makes sure product inventory is not empty
            while (proceed == false) {
                try {
                    System.out.print("Enter the product code of the product you would like to remove: ");
                    cod = sc.next(); //Gets product code of product user would like to remove
                    for (int x = 0; x<=inventory.size()-1; x++) { //Checks if product code exists in inventory
                        if (cod.equals(inventory.get(x).getCode())) {
                            inside = true;
                            break;
                        }
                    }
                    if (inside == true) {
                        proceed = true;
                    }
                    else {
                        invalidEntry();
                    }
                }
                catch (Exception e) {
                    invalidEntry();
                }
            }
            for (int x = 0; x<=inventory.size()-1; x++) { //Removes product from inventory
                if (cod.equals(inventory.get(x).getCode())) {
                    inventory.remove(x);
                    break;
                }
            }
            System.out.println("Successfully removed product");
            System.out.println("");
        }
        else {
            System.out.println("Inventory is empty, please add an item");
            System.out.println("");
        }
        return inventory; //Returns updated inventory
    }

    public static ArrayList<Product> editProduct(ArrayList<Product> inventory) { //Edits a property of a product in inventory
        boolean proceed = false;
        String cod = null;
        int option = 0;
        int position = 0;
        boolean inside = false;
        String desc = null;
        int quant = 0;
        int sal = 0;
        String newCod = null;
        boolean check = false;
        System.out.println("");
        if (inventory.size() != 0) { //Makes sure inventory is not empty
            while (proceed == false) {
                try {
                    System.out.print("Enter the product code of the product you would like to edit: ");
                    cod = sc.next(); //Gets product code of product user would like to edit
                    for (int x = 0; x<=inventory.size()-1; x++) {
                        if (cod.equals(inventory.get(x).getCode())) { //Checks if product code exists in inventory
                            position = x;
                            inside = true;
                            break;
                        }
                    }
                    if (inside == true) {
                        while (proceed == false) {
                            System.out.println("");
                            System.out.println("1.) Change product code");
                            System.out.println("2.) Change product name");
                            System.out.println("3.) Change product quantity");
                            System.out.println("4.) Change product sales");
                            System.out.println("5.) Exit this section");
                            System.out.println("");
                            try {
                                System.out.print("Enter your option: ");
                                option = sc.nextInt(); //Asks user what they would like to edit or if they would like to exit
                                System.out.println("");
                                if (option == 1) { //Changes product code
                                    System.out.print("Enter the new prodcuct code: ");
                                    newCod = sc.next();
                                    System.out.println("");
                                    for (int x = 0; x<=inventory.size()-1; x++) {
                                        if (newCod.equals(inventory.get(x).getCode())) {
                                            check = true;
                                            break;
                                        }
                                    }
                                    if (check == true) {
                                        invalidEntry();
                                    }
                                    else {
                                        inventory.get(position).setCode(newCod);
                                        System.out.println("");
                                        System.out.println("Successfully edited product");
                                        proceed = true;
                                    }
                                }
                                else if (option == 2) { //Edits product name
                                    System.out.println("Enter the product name: ");
                                    sc.nextLine();
                                    desc = sc.nextLine();
                                    inventory.get(position).setDescription(desc);
                                    System.out.println("");
                                    System.out.println("Successfully edited product");
                                    proceed = true;
                                }
                                else if (option == 3) { //Edits product quantity
                                    System.out.print("Enter the product quantity");
                                    quant = sc.nextInt();
                                    inventory.get(position).setQuantity(quant);
                                    System.out.println("");
                                    System.out.println("Successfully edited product");
                                    proceed = true;
                                }
                                else if (option == 4) { //Edits product sales
                                    System.out.println("Enter the product sales: ");
                                    sal = sc.nextInt();
                                    inventory.get(position).setSales(sal);
                                    System.out.println("");
                                    System.out.println("Successfully edited product");
                                    proceed = true;
                                }
                                else if (option == 5) { //Leaves this section
                                    proceed = true;
                                }
                                else {
                                    invalidEntry();
                                }
                            }
                            catch (Exception e) {
                                invalidEntry();
                                sc.nextLine();
                            }
                        }
                    }
                    else {
                        invalidEntry();
                    }
                }
                catch (Exception e) {
                    invalidEntry();
                }
            }
        }
        else {
            System.out.println("The inventory is empty, please add a product");
        }
        System.out.println("");
        return inventory;
    }
    public static void displayLessTen(ArrayList<Product> inventory) { //Displays formatted list of products of quantity less than ten
        System.out.format("%7s%20s%11s%10s", "Code", "Description", "Quantity", "Sales");
        System.out.println("");
        if (inventory.size() != 0) {
            for (Product product : inventory) {
                if  (product.getQuantity()<11) {
                    System.out.format("%7s%20s%11d%10d", product.getCode(), product.getDescription(), product.getQuantity(), product.getSales());
                    System.out.println("");
                }
            }
        }
    }

    public static void search(ArrayList<Product> inventory) { //Searches through inventory using user-inputted keyword
        System.out.println("Enter the keyword: ");
        sc.nextLine();
        String keyword = sc.nextLine(); //Gets user input for search keyword
        System.out.println("");
        System.out.format("%10s%22s%14s%10s", "Code", "Description", "Quantity", "Sales");
        System.out.println("");
        if (inventory.size() != 0) { //Makes sure inventory is not empty
            for (Product product : inventory) { //Prints formatted list of products including the search keyword
                if (product.getCode().contains(keyword) || product.getDescription().contains(keyword)
                    || Integer.toString(product.getQuantity()).contains(keyword) ||
                    Integer.toString(product.getSales()).contains(keyword)) {
                        System.out.format("%10s%22s%14d%10d", product.getCode(), product.getDescription(), product.getQuantity(), product.getSales());
                        System.out.println("");
                }
            }
        }
        System.out.println("");
    }
}