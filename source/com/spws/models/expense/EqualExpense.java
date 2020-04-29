package com.spws.models.expense;

import com.spws.models.User;
import com.spws.models.split.EqualSplit;
import com.spws.models.split.Split;

import java.util.List;

public class EqualExpense extends Expense {
    public EqualExpense(double amount, User paidBy, List<Split> splits, ExpenseMetadata metadata) {
        super(amount, paidBy, splits, metadata);
    }

    @Override
    public boolean validate() {
        for(Split split : getSplits()) {
            if(!(split instanceof EqualSplit)) {
                return false;
            }
        }
        return true;
    }
}