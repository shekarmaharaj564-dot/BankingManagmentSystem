package com.bankingsystem.main;

import java.util.Scanner;

import com.bankingsystem.dao.CustomerDAO;
import com.bankingsystem.exception.InsufficientBalanceException;
import com.bankingsystem.model.Customer;

public class BankingApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CustomerDAO dao = new CustomerDAO();

        while (true) {

            System.out.println("\n===== BANKING SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. Check Balance");
            System.out.println("7. Delete Account");
            System.out.println("8. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

            case 1:

                sc.nextLine();

                System.out.print("Enter Customer Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Phone Number: ");
                String phone = sc.nextLine();

                System.out.print("Enter Email: ");
                String email = sc.nextLine();

                Customer customer =
                        new Customer(name, phone, email);

                dao.createAccount(customer);

                break;

            case 2:

                System.out.print("Enter Account ID: ");
                int viewId = sc.nextInt();

                dao.viewAccount(viewId);

                break;

            case 3:

                System.out.print("Enter Account ID: ");
                int depositId = sc.nextInt();

                System.out.print("Enter Amount: ");
                double depositAmount = sc.nextDouble();

                dao.deposit(depositId, depositAmount);

                break;

            case 4:

                try {

                    System.out.print("Enter Account ID: ");
                    int withdrawId = sc.nextInt();

                    System.out.print("Enter Amount: ");
                    double withdrawAmount = sc.nextDouble();

                    dao.withdraw(withdrawId, withdrawAmount);

                } catch (InsufficientBalanceException e) {

                    System.out.println(e.getMessage());
                }

                break;

            case 5:

                System.out.print("Enter Sender Account ID: ");
                int senderId = sc.nextInt();

                System.out.print("Enter Receiver Account ID: ");
                int receiverId = sc.nextInt();

                System.out.print("Enter Amount: ");
                double transferAmount = sc.nextDouble();

                dao.transferMoney(senderId, receiverId, transferAmount);

                break;

            case 6:

                System.out.print("Enter Account ID: ");
                int balanceId = sc.nextInt();

                dao.checkBalance(balanceId);

                break;

            case 7:

                System.out.print("Enter Account ID: ");
                int deleteId = sc.nextInt();

                dao.deleteAccount(deleteId);

                break;

            case 8:

                System.out.println("Thank You");
                sc.close();
                System.exit(0);

                break;

            default:

                System.out.println("Invalid Choice");
            }
        }
    }
}