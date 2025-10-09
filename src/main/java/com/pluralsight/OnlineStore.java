package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class OnlineStore {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try(BufferedReader reader = new BufferedReader((new FileReader(fileName)))){
            String input;
            while((input = reader.readLine()) != null){
                String[] token = input.split("\\|");
                String sku = token[0];
                String name = token[1];
                double price = Double.parseDouble(token[2]);
                String department = token[3];
                inventory.add(new Product(sku, name, price, department));
            }
        }
        catch(IOException ex){
            System.err.println("file not found");
            ex.getStackTrace();
        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        for(Product product : inventory){
            System.out.println(product.displayStringProduct());
        }

        System.out.println("Enter id of the product");
        String id = scanner.nextLine();

        for(Product product : inventory){
            if(id.equalsIgnoreCase(product.getSku())){
                cart.add(product);
                System.out.println("Product added to cart!");
                break;
            }
        }
    }

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        double totalPrice = 0;
        System.out.println("Your cart: ");

        for(Product product : cart){
            System.out.println(product.displayStringCart());
            totalPrice += product.getPrice();
        }

        System.out.println("Do you want to checkout? Enter C to checkout and enter X to return.");
        String userInput = scanner.nextLine();
        if(userInput.equalsIgnoreCase("C")){
            checkOut(cart, totalPrice, scanner);
            return;
        }
        System.out.println("Returning to menu.");
    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {
        System.out.println("Are you sure you want to checkout? Type Y");
        System.out.println("If you want to remove an item, type R");
        String userInput = scanner.nextLine();

        boolean didExit = false;
        while(!didExit){
            switch(userInput.toUpperCase()) {
                case "R":
                    System.out.println("type the ID you want to remove: ");
                    String idInput = scanner.nextLine();
                    for (Product product : cart) {
                        if (product.getSku().equalsIgnoreCase(idInput)) {
                            cart.remove(product);
                            break;
                        }
                    }
                    System.out.println("Input is invalid");
                    break;
                case "Y":
                    System.out.println("Enter your payment.");
                    double userPaying = scanner.nextDouble();
                    scanner.nextLine();
                    double change = userPaying - totalAmount;
                    System.out.println("Here are your items bought: ");
                    for (Product product : cart) {
                        System.out.println(product.displayStringProduct() + "\n");
                    }
                    if (change != 0) System.out.println("Change: $" + change);
                    cart.clear();
                    didExit = true;
                    break;
                default:
                    System.out.println("Returning to menu");
                    didExit = true;
            }
        }
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        return null;
    }
}