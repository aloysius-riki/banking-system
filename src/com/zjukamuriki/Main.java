package com.zjukamuriki;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Account account = new Account();
        homePage(account);

    }

    public static void homePage(Account account) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        int menuItem = scanner.nextInt();

        if (menuItem == 1) {
            createAccount(account);
        }

        if (menuItem == 2) {
            loginToAccount(account);
        }

        if (menuItem == 0) {
            exitProgram();
        }

    }

    public static void createAccount(Account account) {

        Random random = new Random();
        int randomAccountIdInt = random.nextInt(1000000000);
        String randomAccountId = String.format("%09d", randomAccountIdInt);

        int cardPinInt = random.nextInt(10000);
        String cardPin = String.format("%04d", cardPinInt);

        String cardNumber = "400000" + randomAccountId;



        //Generate checksum using Luhn algorithm
        int [] controlNumberArray = new int [cardNumber.length()];
        long controlNumber = 0;
        long checksum;

        for (int i = 0; i < controlNumberArray.length; i++) {
            controlNumberArray[i] = Integer.parseInt(String.valueOf(cardNumber.charAt(i)));

        }

        for (int i = 0; i < controlNumberArray.length; i++) {
            if ((i+1) % 2 != 0) {
                controlNumberArray[i] = controlNumberArray[i] * 2;
            }
        }

        for (int i = 0; i < controlNumberArray.length; i++) {
            if (controlNumberArray[i] > 9){
                controlNumberArray[i] = controlNumberArray[i] -9;
            }
        }

        for (int j : controlNumberArray) {
            controlNumber = controlNumber + j;
        }

        if (controlNumber % 10 == 0) {
            checksum = 0;
        } else {
            checksum = 10 - (controlNumber % 10);
        }

        cardNumber = cardNumber + checksum;


        account.setAccountNumber(cardNumber);
        account.setAccountPin(cardPin);
        account.setBalance(0);


        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getAccountNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getAccountPin());
        System.out.println();


        homePage(account);
    }

    public static void loginToAccount(Account account) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter your card number:");
            String cardNumberEntered  = scanner.next();
            System.out.println("Enter your PIN:");
            String PinEntered  = scanner.next();


            if ((PinEntered.equals(account.getAccountPin()) && cardNumberEntered.equals(account.getAccountNumber()))) {
                System.out.println("You have successfully logged in!");
                accountPage(account);
            } else {
                System.out.println("Wrong card number or PIN!" +
                        PinEntered + " " + account.getAccountPin() + " " +
                        cardNumberEntered + " " + account.getAccountNumber() );
                homePage(account);
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Please enter a 16 digit card number and a 4 digit pin");
        } catch (Exception e) {
            System.out.println("Oops, something went wrong");
        }


    }

    public static void accountPage(Account account) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
        int menuItem = scanner.nextInt();

        if (menuItem == 1) {
            accountBalance(account);
        }

        if (menuItem == 2) {
            logOut(account);
        }

        if (menuItem == 0) {
            exitProgram();
        }
    }

    public static void logOut(Account account) {
        System.out.println("You have successfully logged out!");
        homePage(account);

    }

    public static void accountBalance(Account account) {
        System.out.println(account.getBalance());
        accountPage(account);
    }

    public static void exitProgram() {
        System.out.println("Bye!");
    }

}

class Account {

    private String accountNumber;
    private String accountPin;
    private long balance;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountPin() {
        return accountPin;
    }

    public void setAccountPin(String accountPin) {
        this.accountPin = accountPin;
    }
}