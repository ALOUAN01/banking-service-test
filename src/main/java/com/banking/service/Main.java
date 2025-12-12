package com.banking.service;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Running Banking Service Tests ===\n");

        testAcceptanceCriteria();
        testDepositValidation();
        testWithdrawalValidation();
        testInsufficientFunds();
        testMultipleOperations();

        System.out.println("\n=== All Tests Completed ===");
    }

    // Acceptance test as specified
    private static void testAcceptanceCriteria() {
        System.out.println("Test 1: Acceptance Criteria");
        System.out.println("----------------------------");
        Account account = new Account();

        // Using package-private methods for testing with specific dates
        account.depositOnDate(1000, LocalDate.of(2012, 1, 10));
        account.depositOnDate(2000, LocalDate.of(2012, 1, 13));
        account.withdrawOnDate(500, LocalDate.of(2012, 1, 14));

        System.out.println("Expected output:");
        System.out.println("Date       || Amount || Balance");
        System.out.println("14/01/2012 || -500 || 2500");
        System.out.println("13/01/2012 || 2000 || 3000");
        System.out.println("10/01/2012 || 1000 || 1000");
        System.out.println("\nActual output:");
        account.printStatement();
        System.out.println();
    }

    private static void testDepositValidation() {
        System.out.println("Test 2: Deposit Validation");
        System.out.println("----------------------------");
        Account account = new Account();

        try {
            account.deposit(-100);
            System.out.println("FAILED: Should throw exception for negative deposit");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Correctly rejected negative deposit - " + e.getMessage());
        }

        try {
            account.deposit(0);
            System.out.println("FAILED: Should throw exception for zero deposit");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Correctly rejected zero deposit - " + e.getMessage());
        }
        System.out.println();
    }

    private static void testWithdrawalValidation() {
        System.out.println("Test 3: Withdrawal Validation");
        System.out.println("------------------------------");
        Account account = new Account();

        try {
            account.withdraw(-100);
            System.out.println("FAILED: Should throw exception for negative withdrawal");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Correctly rejected negative withdrawal - " + e.getMessage());
        }

        try {
            account.withdraw(0);
            System.out.println("FAILED: Should throw exception for zero withdrawal");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Correctly rejected zero withdrawal - " + e.getMessage());
        }
        System.out.println();
    }

    private static void testInsufficientFunds() {
        System.out.println("Test 4: Insufficient Funds");
        System.out.println("---------------------------");
        Account account = new Account();
        account.deposit(100);

        try {
            account.withdraw(200);
            System.out.println("FAILED: Should throw exception for insufficient funds");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Correctly rejected withdrawal - " + e.getMessage());
        }

        System.out.println("Balance after failed withdrawal: " + account.getBalance());
        System.out.println();
    }

    private static void testMultipleOperations() {
        System.out.println("Test 5: Multiple Operations");
        System.out.println("----------------------------");
        Account account = new Account();

        account.deposit(1000);
        account.deposit(500);
        account.withdraw(200);
        account.deposit(300);
        account.withdraw(100);

        System.out.println("Final balance: " + account.getBalance());
        System.out.println("Expected: 1500");
        System.out.println("Transaction count: " + account.getTransactionCount());
        System.out.println("\nStatement:");
        account.printStatement();
        System.out.println();
    }
}