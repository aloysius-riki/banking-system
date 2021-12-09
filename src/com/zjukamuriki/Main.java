package com.zjukamuriki;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //long cardNumber = 4_000_004_938_320_895L;
        //long cardPin = 1234;

        homePage();

    }

    public static void homePage() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        int menuItem = scanner.nextInt();

        if (menuItem == 1) {
            createAccount();
        }

        if (menuItem == 2) {
            loginToAccount();
        }

        if (menuItem == 0) {
            exitProgram();
        }

    }

    public static void createAccount() {

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println("4000004938320895");
        System.out.println("Your card PIN:");
        System.out.println("1234");

        homePage();
    }

    public static void loginToAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your card number:");
        long cardNumberEntered  = scanner.nextLong();
        System.out.println("Enter your PIN:");
        long cardPinEntered  = scanner.nextLong();

        if (cardPinEntered == 1234 && cardNumberEntered == 4000004938320895L) {
            System.out.println("You have successfully logged in!");
            accountPage();
        } else {
            System.out.println("Wrong card number or PIN!");
            homePage();
        }

    }



    public static void accountPage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
        int menuItem = scanner.nextInt();

        if (menuItem == 1) {
            accountBalance();
        }

        if (menuItem == 2) {
            logOut();
        }

        if (menuItem == 0) {
            exitProgram();
        }
    }

    public static void logOut() {
        System.out.println("You have successfully logged out!");
        homePage();

    }

    public static void accountBalance() {
        //get value from array
        System.out.println("Balance : 0");
        accountPage();
    }

    public static void exitProgram() {
        System.out.println("Bye!");
    }

}
