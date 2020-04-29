package com.spws.view;

import com.spws.models.User;
import com.spws.services.ExpenseManager;
import com.spws.models.expense.ExpenseType;
import com.spws.models.split.EqualSplit;
import com.spws.models.split.ExactSplit;
import com.spws.models.split.PercentSplit;
import com.spws.models.split.Split;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainDriver {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();

        expenseManager.addUser(new User("u1", "Ayush Jain", "u1@spws.com", "1234567890"));
        expenseManager.addUser(new User("u2", "Aarju Kaushal", "u2@spws.com", "1234567890"));
        expenseManager.addUser(new User("u3", "Amresh Jain", "u3@spws.com", "1234567890"));
        expenseManager.addUser(new User("u4", "Sushama Jain", "u4@spws.com", "1234567890"));

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String command = scanner.nextLine();
            String[] commands = command.split(" ");
            String commandType = commands[0];

            switch(commandType) {
                case "SHOW":
                    if(commands.length == 1) {
                        expenseManager.showBalances();
                    } else {
                        expenseManager.showBalance(commands[1]);
                    }
                    break;
                case "EXPENSE":
                    String paidBy = commands[1];
                    Double amount = Double.parseDouble(commands[2]);
                    int noOfUsers = Integer.parseInt(commands[3]);
                    String expenseType = commands[4 + noOfUsers];
                    List<Split> splits = new ArrayList<Split>();
                    switch(expenseType) {
                        case "EQUAL": 
                            for(int i=0; i<noOfUsers; i++) {
                                splits.add(new EqualSplit(expenseManager.userMap.get(commands[4+i])));
                            }
                            expenseManager.addExpense(ExpenseType.EQUAL, amount, paidBy, splits, null);
                            break;
                        case "EXACT": 
                            for(int i=0; i<noOfUsers; i++) {
                                splits.add(new ExactSplit(expenseManager.userMap.get(commands[4+i]), Double.parseDouble(commands[5 + noOfUsers + i])));
                            }
                            expenseManager.addExpense(ExpenseType.EXACT, amount, paidBy, splits, null);
                            break;
                        case "PERCENT": 
                            for(int i=0; i<noOfUsers; i++) {
                                splits.add(new PercentSplit(expenseManager.userMap.get(commands[4+i]), Double.parseDouble(commands[5 + noOfUsers + i])));
                            }
                            expenseManager.addExpense(ExpenseType.PERCENT, amount, paidBy, splits, null);
                            break;
                    }
                    break;
            }
        }
    }
}