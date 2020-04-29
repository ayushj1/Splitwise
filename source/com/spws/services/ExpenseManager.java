package com.spws.services;

import com.spws.models.User;
import com.spws.models.expense.Expense;
import com.spws.models.expense.ExpenseMetadata;
import com.spws.models.expense.ExpenseType;
import com.spws.models.split.Split;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    List<Expense> expenses;
    public Map<String, User> userMap;
    Map<String, Map<String, Double>> balanceSheet;

    public ExpenseManager() {
        expenses = new ArrayList<Expense>();
        userMap = new HashMap<String, User>();
        balanceSheet = new HashMap<String, Map<String, Double>>();
    }

    public void addUser(User user) {
        userMap.put(user.getId(), user);
        balanceSheet.put(user.getId(), new HashMap<String, Double>());
    }

    public void addExpense(ExpenseType expenseType, double amount, String paidBy, List<Split> splits, ExpenseMetadata expenseMetadata) {
        Expense expense = ExpenseService.createExpense(expenseType, amount, userMap.get(paidBy), splits, expenseMetadata);
        expenses.add(expense);
        for(Split split : expense.getSplits()) {
            String paidFor = split.getUser().getId();
            Map<String, Double> balances = balanceSheet.get(paidBy);
            if(!balances.containsKey(paidFor)) {
                balances.put(paidFor, 0.0);
            }
            balances.put(paidFor, balances.get(paidFor) + split.getAmount());

            balances = balanceSheet.get(paidFor);
            if(!balances.containsKey(paidBy)) {
                balances.put(paidBy, 0.0);
            }
            balances.put(paidBy, balances.get(paidBy) - split.getAmount());
        }
    }

    public void showBalance(String userId) {
        boolean isEmpty = true;
        for(Map.Entry<String, Double> userBalance : balanceSheet.get(userId).entrySet()) {
            if(userBalance.getValue() != 0) {
                isEmpty = false;
                printBalance(userId, userBalance.getKey(), userBalance.getValue());
            }
        }

        if(isEmpty) {
            System.out.println("No Balances");
        }
    }

    public void showBalances() {
        boolean isEmpty = true;
        for(Map.Entry<String, Map<String, Double>> allBalances : balanceSheet.entrySet()) {
            for(Map.Entry<String, Double> userBalance : allBalances.getValue().entrySet()) {
                if(userBalance.getValue() > 0) {
                    isEmpty = false;
                    printBalance(allBalances.getKey(), userBalance.getKey(), userBalance.getValue());
                }
            }
        }
        
        if(isEmpty) {
            System.out.println("No Balances");
        }
    }

    public void printBalance(String user1, String user2, double amount) {
        String user1Name = userMap.get(user1).getName();
        String user2Name = userMap.get(user2).getName();
        if(amount<0) {
            System.out.println(user1Name + " owes " + user2Name + ": " + Math.abs(amount));
        } else {
            System.out.println(user2Name + " owes " + user1Name + ": " + Math.abs(amount));
        }
    }
}