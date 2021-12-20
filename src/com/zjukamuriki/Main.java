package com.zjukamuriki;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int[] cardPin = new int[4];
    private static final long[] cardNumber = new long[16];

    public static void main(String[] args) {

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

        //long[] cardNumber = new long[cardLength];
        cardNumber[0] = 4;
        cardNumber[cardLength - 1] = randomChecksum;

        String tempAccountId = Integer.toString(randomAccountId);
        for (int i = 6; i < cardLength - 1 ; i++) {
            cardNumber[i] = tempAccountId.charAt(i - 6) - '0';
        }

        String tempCardPin = Integer.toString(randomCardPin);
        //int[] cardPin = new int[tempCardPin.length()];
        for(int j = 0; j < tempCardPin.length(); j++) {
            cardPin[j] = tempCardPin.charAt(j) - '0';
        }

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        for (long cardNumberValue : cardNumber) {
            System.out.print(cardNumberValue);
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
        try {
            String stringCardNumberEntered  = scanner.next();
            long[] cardNumberEntered = new long[cardNumber.length];
            for(int i = 0; i < cardNumber.length; i++) {
                cardNumberEntered[i]  = stringCardNumberEntered.charAt(i) - '0';
            }

            System.out.println("Enter your PIN:");
            String stringCardPinEntered  = scanner.next();
            int[] cardPinEntered = new int[cardPin.length];
            for(int j = 0; j < cardPin.length; j++) {
                cardPinEntered[j]  = stringCardPinEntered.charAt(j) - '0';
            }

            if (Arrays.equals(cardPinEntered, cardPin) && Arrays.equals(cardNumberEntered, cardNumber)) {
                System.out.println("You have successfully logged in!");
                accountPage();
            } else {
                System.out.println("Wrong card number or PIN!");
                homePage();
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Please enter a 16 digit card number and a 4 digit pin");
        } catch (Exception e) {
            System.out.println("Oops, something went wrong");
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