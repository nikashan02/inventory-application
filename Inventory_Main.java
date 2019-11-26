//Nikashan Thavaruban
//Nov. 5, 2019
//Inventory_Main.java
//Inventory program that can create and allow users to modify inventory lists

import java.util.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class Inventory_Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String args[]) {

        ArrayList<Product> products = new ArrayList<Product>(); //Products ArrayList made of Product Objects
        boolean proceed = false;
        int option = 0;
        File userFile = new File("users.txt");         //Users file
        File passwordFile = new File("passwords.txt"); //Passwords file
        FileWriter userFW;
        FileWriter passwordFW;
        BufferedWriter userBW = null;
        BufferedWriter passwordBW = null;
        String username = null;
        String password = null;

        try { //Create FileWriter and BufferWriter objects for username and password files
            userFW = new FileWriter(userFile, true);
            passwordFW = new FileWriter(passwordFile, true);
            userBW = new BufferedWriter(userFW);
            passwordBW = new BufferedWriter(passwordFW);
        } 
        catch (IOException e) {
            System.out.println(e);
        }
        
        System.out.println("WELCOME to the INVENTORY PROGRAM\n");
        System.out.println("");
        System.out.println("Please choose from the following: ");
        System.out.println("");
        System.out.println("1.) Login");
        System.out.println("2.) Create new user");
        System.out.println("");

        while (proceed == false) { //Gets input to see if user wants to login or create new user
            try {
                System.out.print("Enter an option: ");
                option = input.nextInt();
            } 
            catch (Exception e) {
                Option_Methods.invalidEntry();
            }
            if (option == 1 || option == 2) {
                proceed = true;
            } else {
                Option_Methods.invalidEntry();
            }
        }

        if (option == 2) { //Creates new user if option 2 is selected
            String[] userPass = Option_Methods.createUser(); //Gets array of username and password that user has inputted
            String hashedPass;
            try {
                userBW.write(userPass[0]); //Writes username to "users.txt" file
                userBW.write("\n");
                hashedPass = Option_Methods.hashPassword(userPass[1]); //Hashes user inputted password
                passwordBW.write(hashedPass); //Writes hashed password to "passwords.txt" file
                passwordBW.write("\n");
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                FileOutputStream fos = new FileOutputStream(userPass[0]); //Creates inventory .tmp file for specific user
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(products); //Writes products ArrayList object to .tmp file
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("");
        }
        try {
            userBW.close();
            passwordBW.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        Scanner checkUser = null;
        Scanner checkPassword = null;

        try {
            checkUser = new Scanner(userFile); //Creates Scanner object that can read "users.txt" file
            checkPassword = new Scanner(passwordFile); //Creates Scanner object that can read "passwords.txt" file
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        boolean foundPassword = false;
        boolean foundUser = false;

        System.out.println("");
        System.out.println("LOGIN");

        while (foundUser == false) { //Login section
            System.out.print("Enter your username: ");
            username = input.next(); //Asks user for username
            System.out.println("");
            int userLineNumber = 0;
            while (checkUser.hasNextLine()) { 
                userLineNumber++;
                String userLine = checkUser.nextLine();
                if (username.equals(userLine)) { //Checks if username is in the "users.txt" file
                    foundUser = true;
                    System.out.print("Enter your password: ");
                    password = input.next(); //Prompts user for password if username entered is valid
                    System.out.println("");
                    String hashedEntered = null;
                    int passLineNumber = 0;
                    try {
                        hashedEntered = Option_Methods.hashPassword(password); //Hashes user entered password
                    } 
                    catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    while (checkPassword.hasNextLine()) {
                        passLineNumber++;
                        String passwordLine = checkPassword.nextLine();
                        if (passwordLine.equals(hashedEntered) && passLineNumber == userLineNumber) { //Allows user to access
                            foundPassword = true;                                                     //their inventory file
                                                                                                      //if the password is correct
                            
                            //-------------------------LOAD INVENTORY-----------------------------

                            try {
                                FileInputStream fis = new FileInputStream(username);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                    
                                products = (ArrayList) ois.readObject();
                    
                                ois.close();
                                fis.close();
                            } 
                            catch (IOException ioe) {
                                ioe.printStackTrace();
                                return;
                            } 
                            catch (ClassNotFoundException c) {
                                System.out.println("File could not be loaded");
                                c.printStackTrace();
                                return;
                            }
                            //--------------------END OF INVENTORY LOADING------------------------

                            boolean done = false;
                            int choice = 0;        

                            while (done == false) { //Loops through inventory options until user wants to quit
                                Option_Methods.printOptions();

                                choice = Option_Methods.chooseOption(); //Gets user input for what they would like to do
                                
                                if (choice == 1) {
                                    Option_Methods.displayInventory(products);
                                }
                                else if (choice == 2) {
                                    Option_Methods.displayDetails(products);
                                }
                                else if (choice == 3) {
                                    products = Option_Methods.addProduct(products);
                                }
                                else if (choice == 4) {
                                    System.out.println("");
                                    Option_Methods.displayDetails(products);
                                    products = Option_Methods.removeProduct(products);
                                }
                                else if (choice == 5) {
                                    System.out.println("");
                                    Option_Methods.displayDetails(products);
                                    products = Option_Methods.editProduct(products);
                                }
                                else if (choice == 6) {
                                    Option_Methods.displayLessTen(products);
                                }
                                else if (choice == 7) {
                                    Option_Methods.search(products);
                                }
                                else if (choice == 8) { //Saves current state of inventory to user's .tmp file
                                    try {
                                        FileOutputStream fos = new FileOutputStream(username);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(products);
                                        oos.close();
                                        fos.close();
                                    } 
                                    catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("Successfully saved changes");
                                }
                                else if (choice == 9) {
                                    //-------------------------Auto Save--------------------------------
                                    try {
                                        FileOutputStream fos = new FileOutputStream(username);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(products);
                                        oos.close();
                                        fos.close();
                                    } 
                                    catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //--------------------Auto Save Complete----------------------------

                                    System.out.println("");
                                    System.out.println("Thank you for using the INVENTORY program");
                                    System.out.println("");
                                    done = true;
                                }
                            }
                            break;
                        }
                    }
                    if (foundPassword != true) {
                        System.out.println("INCORRECT PASSWORD");
                        System.out.println("");
                    }
                    break;
                }
            }
            if (foundUser == false) {
                System.out.println("USER NOT FOUND");
                System.out.println("");
            }
        }
    }
}