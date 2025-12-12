package com.banking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    // Acceptance Test (Given / When / Then)
    @Test
    void should_print_statement_with_correct_balance_and_order() {
        // Given
        account.depositOnDate(1000, LocalDate.of(2012, 1, 10));
        account.depositOnDate(2000, LocalDate.of(2012, 1, 13));
        account.withdrawOnDate(500, LocalDate.of(2012, 1, 14));

        // Then
        assertEquals(2500, account.getBalance());
        assertEquals(3, account.getTransactionCount());
    }

    // Deposit tests
    @Test
    void deposit_should_increase_balance() {
        account.deposit(1000);

        assertEquals(1000, account.getBalance());
    }

    @Test
    void deposit_should_throw_exception_when_amount_is_negative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));

        assertTrue(exception.getMessage().contains("positive"));
    }

    @Test
    void deposit_should_throw_exception_when_amount_is_zero() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
    }

    // Withdrawal tests
    @Test
    void withdraw_should_decrease_balance() {
        account.deposit(1000);
        account.withdraw(400);

        assertEquals(600, account.getBalance());
    }

    @Test
    void withdraw_should_throw_exception_when_amount_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50));
    }

    @Test
    void withdraw_should_throw_exception_when_amount_is_zero() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
    }

    @Test
    void withdraw_should_throw_exception_when_insufficient_funds() {
        account.deposit(200);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> account.withdraw(300));

        assertTrue(exception.getMessage().contains("Insufficient"));
    }

    // Multiple operations
    @Test
    void multiple_operations_should_keep_consistent_balance() {
        account.deposit(1000);
        account.deposit(500);
        account.withdraw(200);
        account.deposit(300);
        account.withdraw(100);

        assertEquals(1500, account.getBalance());
        assertEquals(5, account.getTransactionCount());
    }
    @Test
    void printStatement_should_display_transactions_in_reverse_chronological_order() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        account.depositOnDate(1000, LocalDate.of(2012, 1, 10));
        account.depositOnDate(2000, LocalDate.of(2012, 1, 13));
        account.withdrawOnDate(500, LocalDate.of(2012, 1, 14));

        account.printStatement();

        System.setOut(originalOut);
        String output = outputStream.toString();

        // Check that 14/01 appears before 10/01
        int index14 = output.indexOf("14/01/2012");
        int index10 = output.indexOf("10/01/2012");
        assertTrue(index14 < index10);
    }
    @Test
    void printStatement_should_work_on_empty_account() {
        assertDoesNotThrow(() -> account.printStatement());
    }

    @Test
    void new_account_should_have_zero_balance() {
        assertEquals(0, account.getBalance());
        assertEquals(0, account.getTransactionCount());
    }
    @Test
    void withdraw_entire_balance_should_leave_zero() {
        account.deposit(500);
        account.withdraw(500);

        assertEquals(0, account.getBalance());
    }

    @Test
    void large_amounts_should_be_handled() {
        account.deposit(Integer.MAX_VALUE / 2);
        assertTrue(account.getBalance() > 0);
    }
    @Test
    void acceptance_test_with_full_verification() {
        // Given
        account.depositOnDate(1000, LocalDate.of(2012, 1, 10));
        account.depositOnDate(2000, LocalDate.of(2012, 1, 13));
        account.withdrawOnDate(500, LocalDate.of(2012, 1, 14));

        // When (captured output)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        account.printStatement();
        System.setOut(System.out);

        String output = outputStream.toString();

        // Then
        assertTrue(output.contains("14/01/2012 || -500 || 2500"));
        assertTrue(output.contains("13/01/2012 || 2000 || 3000"));
        assertTrue(output.contains("10/01/2012 || 1000 || 1000"));
    }
}
