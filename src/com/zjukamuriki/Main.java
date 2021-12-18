package com.zjukamuriki;

import java.util.Arrays;
import java.util.Random;
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

        Random random = new Random();
        int randomAccountId = random.nextInt(1000000000);
        int randomChecksum = random.nextInt(10);
        int randomCardPin = random.nextInt(10000);
        final int cardLength = 16;

        long[] cardId = new long[cardLength];
        cardId [0] = 4;
        cardId [cardLength - 1] = randomChecksum;

        String tempAccountId = Integer.toString(randomAccountId);
        for (int i = 6; i < cardLength - 1 ; i++) {
            cardId[i] = tempAccountId.charAt(i - 6) - '0';
        }

        String tempCardPin = Integer.toString(randomCardPin);
        int[] cardPin = new int[tempCardPin.length()];
        for(int j = 0; j < tempCardPin.length(); j++) {
            cardPin[j] = tempCardPin.charAt(j) - '0';
        }

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        for (long cardIdValue : cardId) {
            System.out.print(cardIdValue);
        }
        System.out.println();
        System.out.println("Your card PIN:");
        for (long cardPinValue : cardPin) {
            System.out.print(cardPinValue);
        }
        System.out.println();


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
