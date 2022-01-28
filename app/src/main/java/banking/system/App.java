package banking.system;

import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        String filename = "card.s3db";
        String url = "jdbc:sqlite:"+ filename;
        createDataBase(filename);

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        createTable(dataSource);

        Account account = new Account();
        homePage(account, dataSource);


    }

    public static void createDataBase(String filename) {

        try {
            File fileName = new File(filename);
            if (fileName.createNewFile()) {
                System.out.println("File created: " + fileName.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createTable(SQLiteDataSource dataSource){

        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void homePage(Account account, SQLiteDataSource dataSource) {


        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        Scanner keyboard = new Scanner(System.in);

        String nextIntString = keyboard.nextLine(); //get the number as a single line
        int menuItem = Integer.parseInt(nextIntString);

        if (menuItem == 1) {
            createAccount(account, dataSource);
        }

        if (menuItem == 2) {
            loginToAccount(account, dataSource);
        }

        if (menuItem == 0) {
            exitProgram();
        }

    }

    public static void createAccount(Account account, SQLiteDataSource dataSource) {

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

        //save to DB
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("INSERT INTO card (number, pin, balance )" +
                        "VALUES(" +
                        "'" + account.getAccountNumber() + "'," +
                        "'" + account.getAccountPin() + "'," +
                        "'" + account.getBalance() + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getAccountNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getAccountPin());
        System.out.println();


        homePage(account, dataSource);
    }

    public static void loginToAccount(Account account, SQLiteDataSource dataSource) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter your card number:");
            String cardNumberEntered  = scanner.next();
            System.out.println("Enter your PIN:");
            String PinEntered  = scanner.next();


            if ((PinEntered.equals(account.getAccountPin()) && cardNumberEntered.equals(account.getAccountNumber()))) {
                System.out.println("You have successfully logged in!");
                accountPage(account, dataSource);
            } else {
                System.out.println("Wrong card number or PIN!" +
                        PinEntered + " " + account.getAccountPin() + " " +
                        cardNumberEntered + " " + account.getAccountNumber() );
                homePage(account, dataSource);
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Please enter a 16 digit card number and a 4 digit pin");
        } catch (Exception e) {
            System.out.println("Oops, something went wrong");
        }


    }

    public static void accountPage(Account account, SQLiteDataSource dataSource) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
        int menuItem = scanner.nextInt();

        if (menuItem == 1) {
            accountBalance(account, dataSource);
        }

        if (menuItem == 2) {
            logOut(account, dataSource);
        }

        if (menuItem == 0) {
            exitProgram();
        }
    }

    public static void logOut(Account account, SQLiteDataSource dataSource) {
        System.out.println("You have successfully logged out!");
        homePage(account, dataSource);

    }

    public static void accountBalance(Account account, SQLiteDataSource dataSource) {
        System.out.println(account.getBalance());
        accountPage(account, dataSource);
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