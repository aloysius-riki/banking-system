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
        int randomAccountId = random.nextInt(1000000000);
        int cardPin = random.nextInt(10000);

        String cardNumber = "40000" + randomAccountId;



        //Generate checksum using Luhn algorithm
        long[] controlNumberArray = new long[cardNumber.length()-1];
        long controlNumber = 0;
        long checksum;


        for (int i = 0; i < controlNumberArray.length; i++) {
            controlNumberArray[i] = cardNumber.charAt(i);
        }

        for (int i = 0; i < controlNumberArray.length; i++) {
            if ((i+1) % 2 != 0) {
                controlNumberArray[i] = controlNumberArray[i] * 2;
            }
            if (controlNumberArray[i] > 9){
                controlNumberArray[i] = controlNumberArray[i] -9;
            }
            controlNumber = controlNumber + controlNumberArray[i];
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
        System.out.println();
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
            int PinEntered  = scanner.nextInt();


            if ((PinEntered == account.getAccountPin()) && (cardNumberEntered.equals(account.getAccountNumber()))) {
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
    private int accountPin;
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

    public int getAccountPin() {
        return accountPin;
    }

    public void setAccountPin(int accountPin) {
        this.accountPin = accountPin;
    }
}