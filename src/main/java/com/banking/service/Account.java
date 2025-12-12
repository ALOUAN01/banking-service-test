package com.banking.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements AccountService {
    private int balance;
    private final List<Transaction> transactions;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    @Override
    public void deposit(int amount) {
        validateAmount(amount, "Deposit");
        balance += amount;
        transactions.add(new Transaction(LocalDate.now(), amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        validateAmount(amount, "Withdrawal");

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds. Current balance: " + balance);
        }

        balance -= amount;
        transactions.add(new Transaction(LocalDate.now(), -amount, balance));
    }

    @Override
    public void printStatement() {
        System.out.println("Date       || Amount || Balance");

        // Print transactions in reverse chronological order (most recent first)
        List<Transaction> reversedTransactions = new ArrayList<>(transactions);
        Collections.reverse(reversedTransactions);

        for (Transaction transaction : reversedTransactions) {
            String date = transaction.getDate().format(DATE_FORMATTER);
            String amount = String.format("%d", transaction.getAmount());
            String balance = String.format("%d", transaction.getBalance());

            System.out.println(date + " || " + amount + " || " + balance);
        }
    }

    // Helper method for validation
    private void validateAmount(int amount, String operation) {
        if (amount <= 0) {
            throw new IllegalArgumentException(operation + " amount must be positive. Received: " + amount);
        }
    }

    // Additional utility methods for testing
    public int getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    // Method to allow date injection for testing (without changing public interface)
    void depositOnDate(int amount, LocalDate date) {
        validateAmount(amount, "Deposit");
        balance += amount;
        transactions.add(new Transaction(date, amount, balance));
    }

    void withdrawOnDate(int amount, LocalDate date) {
        validateAmount(amount, "Withdrawal");

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds. Current balance: " + balance);
        }

        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance));
    }
}